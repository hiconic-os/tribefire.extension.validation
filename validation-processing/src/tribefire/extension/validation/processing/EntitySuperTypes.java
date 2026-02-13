package tribefire.extension.validation.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.model.generic.reflection.EntityType;

public final class EntitySuperTypes {

    private static final Map<EntityType<?>, List<EntityType<?>>> CACHE =
            new ConcurrentHashMap<>();

    public static List<EntityType<?>> getLinearizedSuperTypes(EntityType<?> type) {
    	List<EntityType<?>> superTypes = CACHE.get(type);
    	
    	if (superTypes == null) {
    		superTypes = linearize(type);
    		CACHE.put(type, superTypes);
    	}
    		
    	return superTypes;
    }

    private static List<EntityType<?>> linearize(EntityType<?> type) {

        List<List<EntityType<?>>> sequences = new ArrayList<>();

        // recursively obtain parent linearizations
        for (EntityType<?> parent : type.getSuperTypes()) {
            sequences.add(new ArrayList<>(getLinearizedSuperTypes(parent)));
        }

        // plus the direct parent list itself
        sequences.add(new ArrayList<>(type.getSuperTypes()));

        List<EntityType<?>> result = new ArrayList<>();
        result.add(type);

        result.addAll(merge(sequences));

        return List.copyOf(result); // immutable
    }

    private static List<EntityType<?>> merge(List<List<EntityType<?>>> sequences) {

        List<EntityType<?>> result = new ArrayList<>();

        while (true) {

            // remove empty lists
            sequences.removeIf(List::isEmpty);

            if (sequences.isEmpty())
                return result;

            EntityType<?> candidate = null;

            outer:
            for (List<EntityType<?>> seq : sequences) {

                EntityType<?> head = seq.get(0);

                // check if head appears in any tail
                for (List<EntityType<?>> other : sequences) {
                    if (other == seq)
                        continue;

                    if (other.subList(1, other.size()).contains(head)) {
                        continue outer;
                    }
                }

                candidate = head;
                break;
            }

            if (candidate == null) {
                throw new IllegalStateException(
                        "Illegal multiple inheritance hierarchy detected (C3 conflict).");
            }

            result.add(candidate);

            // remove candidate from all heads
            for (List<EntityType<?>> seq : sequences) {
                if (!seq.isEmpty() && seq.get(0).equals(candidate)) {
                    seq.remove(0);
                }
            }
        }
    }
}

