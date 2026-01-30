// ============================================================================
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
package tribefire.extension.validation.test;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.gm.model.reason.essential.InvalidArgument;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.service.api.ServiceRequest;

import hiconic.rx.test.common.AbstractRxTest;
import tribefire.extension.validation.model.reason.EntityConstraintViolation;
import tribefire.extension.validation.model.reason.MandatoryViolation;
import tribefire.extension.validation.model.reason.MaxLengthViolation;
import tribefire.extension.validation.model.reason.MaxViolation;
import tribefire.extension.validation.model.reason.MinLengthViolation;
import tribefire.extension.validation.model.reason.MinViolation;
import tribefire.extension.validation.model.reason.PatternViolation;
import tribefire.extension.validation.model.reason.PropertyConstraintViolation;
import tribefire.extension.validation.test.model.ValidateMe;
import tribefire.extension.validation.test.model.Validated;
import tribefire.extension.validation.test.model.Validated1;
import tribefire.extension.validation.test.model.Validated2;

public class ValidationTest extends AbstractRxTest {

	@BeforeClass
	public static void onBeforeClass() {
	}

	@Test
	public void testRootMandatory() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();

		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T), ValidateMe.T, ValidateMe.payload, MandatoryViolation.T)
		);
	}
	
	private <E extends Validated> E buildValidValidated(EntityType<E> entityType) {
		E v = entityType.create();
		v.setName("Tina");
		v.setDependency("foo:bar#1.0");
		v.setNumber1(4);
		v.setNumber2(4);
		
		return v;
	}
	
	@Test
	public void testValidated1Success() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe);
	}
	
	@Test
	public void testValidated1NameTooShort() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setName("Ti");
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.name, MinLengthViolation.T)
		);
	}
	
	@Test
	public void testValidated1NameTooLong() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setName("Christina Willpanik");
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.name, MaxLengthViolation.T)
				);
	}
	
	@Test
	public void testValidated1DependencyPatternWrong() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setDependency("foo:bar");
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.dependency, PatternViolation.T)
				);
	}
	
	@Test
	public void testValidated1Number1TooLow() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber1(2);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.number1, MinViolation.T)
				);
	}
	
	@Test
	public void testValidated1Number1TooHigh() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber1(11);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.number1, MaxViolation.T)
				);
	}
	
	@Test
	public void testValidated1Number1MaxSucceed() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber1(10);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe);
	}
	
	@Test
	public void testValidated1Number1MinSucceed() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber1(3);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe);
	}
	
	@Test
	public void testValidated1Number2TooLow() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber2(3);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.number2, MinViolation.T)
				);
	}
	
	@Test
	public void testValidated1Number2TooHigh() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		
		payload.setNumber2(10);
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload), 
						Validated1.T, Validated.number2, MaxViolation.T)
				);
	}
	
	@Test
	public void testValidated2NameTooShort() throws Exception {
		ValidateMe validateMe = ValidateMe.T.create();
		
		Validated1 payload = buildValidValidated(Validated1.T);
		Validated2 info = buildValidValidated(Validated2.T); 
		
		payload.setInfo(info);
		
		info.setName("Zi");
		
		validateMe.setPayload(payload);
		
		checkViolations(validateMe,
				new ViolationExpectation(new ViolationPath(ValidateMe.T, ValidateMe.payload, Validated1.info), 
						Validated2.T, Validated2.name, MinLengthViolation.T)
				);
	}
	
	private record ViolationPath(EntityType<?> rootType, String... path) {
		public String asString() {
			StringBuilder builder = new StringBuilder();
			builder.append(rootType.getShortName());
			
			for (String pathElement: path) {
				builder.append('.');
				builder.append(pathElement);
			}
			
			return builder.toString();
		}
	}
	
	private record ViolationExpectation(ViolationPath path, EntityType<?> failedEntityType, String property, EntityType<? extends InvalidArgument> violation) {}

	private void checkViolations(ServiceRequest request, ViolationExpectation... expectations) {
		Maybe<Object> maybe = evaluator.eval(request).getReasoned();
		checkViolations(maybe, expectations);
	}
	
	private void checkViolations(Maybe<?> maybe, ViolationExpectation... expectations) {
		if (expectations.length == 0) {
			if (maybe.isUnsatisfied())
				Assertions.fail("Request unexpectedly failed with " + maybe.whyUnsatisfied().stringify());
			return;
		}
		
		if (!maybe.isUnsatisfiedBy(InvalidArgument.T))
			Assertions.fail("Request unexpectedly succeeded");
		
		InvalidArgument invalidArgument = maybe.whyUnsatisfied();
		
		for (ViolationExpectation expectation: expectations) {
			EntityConstraintViolation ecv = findEntityConstraintViolation(invalidArgument, expectation.failedEntityType(), expectation.path());
			
			if (ecv == null)
				Assertions.fail("Missing expected entity constraint violation for: " + expectation.toString());
			
			PropertyConstraintViolation pcv = findPropertyConstrainViolation(ecv, expectation.property());
			
			if (ecv == null)
				Assertions.fail("Missing expected property constraint violation for: " + expectation.toString());
			
			InvalidArgument v = findViolation(pcv, expectation.violation());
			
			if (v == null)
				Assertions.fail("Missing expected leaf constraint violation for: " + expectation.toString());
		}
	}
	
	private EntityConstraintViolation findEntityConstraintViolation(Reason reason, EntityType<?> failedEntityType, ViolationPath path) {
		for (Reason cause: reason.getReasons()) {
			if (!(cause instanceof EntityConstraintViolation violation))
				continue;
			
			if (!failedEntityType.getTypeSignature().equals(violation.getTypeSignature()))
				continue;
			
			if (!path.asString().equals(violation.getPath()))
				continue;

			return violation;
		}
		
		return null;
	}
	
	private PropertyConstraintViolation findPropertyConstrainViolation(Reason reason, String property) {
		for (Reason cause: reason.getReasons()) {
			if (!(cause instanceof PropertyConstraintViolation violation))
				continue;
			
			if (!property.equals(violation.getProperty()))
				continue;

			return violation;
		}
		
		return null;
	}
	
	private InvalidArgument findViolation(Reason reason, EntityType<? extends InvalidArgument> violationType) {
		for (Reason cause: reason.getReasons()) {
			if (violationType.isInstance(cause))
				return (InvalidArgument)cause;
		}
		
		return null;
	}
}
