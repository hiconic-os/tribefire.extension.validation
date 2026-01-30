package tribefire.extension.validation.processing;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.api.IListItemModelPathElement;
import com.braintribe.model.generic.path.api.IMapKeyModelPathElement;
import com.braintribe.model.generic.path.api.IMapValueModelPathElement;
import com.braintribe.model.generic.path.api.IModelPathElement;
import com.braintribe.model.generic.path.api.IPropertyModelPathElement;
import com.braintribe.model.generic.path.api.IPropertyRelatedModelPathElement;
import com.braintribe.model.generic.path.api.ISetItemModelPathElement;
import com.braintribe.model.generic.reflection.CollectionType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EssentialCollectionTypes;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.MapType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.SetType;

public interface ModelPaths {
	static String stringify(IModelPathElement element) {
		StringBuilder builder = new StringBuilder();
		stringify(element, builder);
		return builder.toString();
	}

	/* Company@13.address.city.name Company@13.contracts[0].name Company@13.contracts[someString].name Company@13.contracts[someString].name
	 * Company@13.personToAddress[Person@1].street; Company@13.personToAddress(Person@1).address; */

	private static void stringify(IModelPathElement element, StringBuilder builder) {

		IModelPathElement previous = element.getPrevious();

		if (previous != null)
			stringify(previous, builder);

		switch (element.getElementType()) {
			case Root:
			case EntryPoint:
				GenericModelType type = element.getType();
				if (type.isEntity()) {
					EntityType<?> entityType = (EntityType<?>) type;
					builder.append(entityType.getShortName());
				}
				else 
					builder.append(type.getTypeName());
				break;
			case ListItem:
				builder.append('[');
				builder.append(((IListItemModelPathElement) element).getIndex());
				builder.append(']');
				break;
			case SetItem:
				builder.append('(');
				ISetItemModelPathElement setElement = (ISetItemModelPathElement) element;
				SetType setType = (SetType) getCollectionType(setElement);
				GenericModelType setElementType = setType.getCollectionElementType();
				builder.append(stringify(setElementType, setElement.getValue()));
				builder.append(')');
				break;
			case MapKey: {
				IMapKeyModelPathElement mapKeyElement = (IMapKeyModelPathElement) element;
				MapType mapType = (MapType) getCollectionType(mapKeyElement);
				GenericModelType keyType = mapType.getKeyType();
				builder.append('[');
				builder.append(stringify(keyType, mapKeyElement.getValue()));
				builder.append("]^");
				break;
			}
			case MapValue: {
				IMapValueModelPathElement mapValueElement = (IMapValueModelPathElement) element;
				IMapKeyModelPathElement mapKeyElement = mapValueElement.getKeyElement();
				MapType mapType = (MapType) getCollectionType(mapKeyElement);
				GenericModelType keyType = mapType.getKeyType();
				builder.append('[');
				builder.append(stringify(keyType, mapKeyElement.getValue()));
				builder.append(']');
				break;
			}
			case Property:
				builder.append(".");
				builder.append(((IPropertyModelPathElement) element).getProperty().getName());
				break;
		}
	}

	private static CollectionType getCollectionType(IPropertyRelatedModelPathElement element) {
		Property property = element.getProperty();

		if (property != null)
			return (CollectionType) property.getType();

		if (element instanceof ISetItemModelPathElement) {
			return EssentialCollectionTypes.TYPE_SET;
		} else if (element instanceof IMapKeyModelPathElement) {
			return EssentialCollectionTypes.TYPE_MAP;
		} else if (element instanceof IMapValueModelPathElement) {
			return EssentialCollectionTypes.TYPE_MAP;
		} else if (element instanceof IListItemModelPathElement) {
			return EssentialCollectionTypes.TYPE_LIST;
		}

		throw new IllegalStateException("unexpected element type " + element.getType());
	}

	private static String stringify(GenericModelType type, Object value) {
		if (type.isBase())
			type = type.getActualType(value);

		if (type.isEntity()) {
			GenericEntity entity = (GenericEntity) value;
			return entity.entityType().getShortName() + "@" + entity.getId();
		} else {
			return String.valueOf(value);
		}

	}
}
