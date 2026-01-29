// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package tribefire.extension.validation.processing;

import java.util.List;
import java.util.Set;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.processing.meta.cmd.CmdResolver;
import com.braintribe.model.processing.traversing.api.GmTraversingContext;
import com.braintribe.model.processing.traversing.api.GmTraversingException;
import com.braintribe.model.processing.traversing.api.GmTraversingVisitor;
import com.braintribe.model.processing.traversing.api.path.TraversingModelPathElement;
import com.braintribe.model.processing.traversing.engine.GMT;

import tribefire.extension.validation.api.ValidationExperts;
import tribefire.extension.validation.api.Validator;
import tribefire.extension.validation.model.reason.EntityConstraintViolation;

/**
 * Validates an entity and its properties transitively by traversing it with the {@link GMT} api. What exactly is
 * validated depends on the implementations of the experts: {@link ValidationExpert}s and
 * {@link PropertyValidationExpert}s which can be passed via an {@link ValidationExpertRegistry}.
 */
public class Validation {

	private ValidationExperts validationExperts;
	private CmdResolver cmdResolver;
	
	public Validation(CmdResolver cmdResolver, ValidationExperts validationExperts) {
		this.cmdResolver = cmdResolver;
		this.validationExperts = validationExperts;
	}

	public Reason validate(Object rootValue) {
		return validate(rootValue, GMF.getTypeReflection().getType(rootValue));
	}
	
	public Reason validate(Object rootValue, GenericModelType rootType) {
		ValidationContextImpl validationContext = new ValidationContextImpl(cmdResolver, validationExperts, rootValue, rootType);
		StatefulValidation statefulValidation = new StatefulValidation(validationContext);
		GMT.traverse().depthFirstWalk().visitor(statefulValidation).doFor(rootValue);
		return statefulValidation.getError();
	}
	
	private class StatefulValidation implements GmTraversingVisitor {
		private ValidationContextImpl validationContext;
		private InvalidArgument error;
		private Set<GenericEntity> visited;
		
		public StatefulValidation(ValidationContextImpl validationContext) {
			super();
			this.validationContext = validationContext;
		}
		
		private void addError(Reason reason) {
			if (error == null) {
				error = Reasons.build(InvalidArgument.T) //
						.text("Invalid data input for type " + validationContext.getRootType().getTypeSignature()) //
						.toReason();
			}
			
			error.getReasons().add(reason);
		}
		
		public InvalidArgument getError() {
			return error;
		}

		@Override
		public void onElementEnter(GmTraversingContext context, TraversingModelPathElement pathElement) throws GmTraversingException {
			if (pathElement.getType().isEntity()) {
				EntityType<?> pathElementType = pathElement.getType();
				GenericEntity value = pathElement.getValue();
	
				if (value != null && visited.add(value)) {
					EntityConstraintViolation entityViolation = null;
					List<Validator<?>> experts = validationContext.getExperts(pathElementType);
					
					for (Validator<?> expert: experts) {
						Validator<GenericEntity> castedExpert = (Validator<GenericEntity>)expert;
						Reason violation = castedExpert.validate(validationContext, value);
						
						if (violation == null)
							continue;
						
						if (entityViolation == null) {
							String typeSignature = pathElementType.getTypeSignature();
							entityViolation = Reasons.build(EntityConstraintViolation.T) //
									.text("Entity constraint violation of " + typeSignature) //
									.toReason();
							
							entityViolation.setPath(ModelPaths.stringify(pathElement));
							entityViolation.setTypeSignature(typeSignature);
							
							addError(entityViolation);
						}
						
						entityViolation.getReasons().add(violation);
					}
				}
			}
		}
	
		@Override
		public void onElementLeave(GmTraversingContext context, TraversingModelPathElement pathElement) throws GmTraversingException {
			// Unused functionality of implemented interface
			// We only use onElementEnter()
		}
	}

}
