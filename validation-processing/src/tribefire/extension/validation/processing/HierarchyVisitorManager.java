package tribefire.extension.validation.processing;

import java.util.HashSet;
import java.util.Set;

public class HierarchyVisitorManager {
	private int branchCount = 0;
	private Set<Object> visited;
	
	public void incBranchCount() {
		branchCount++;
	}
	
	public void decBranchCount() {
		branchCount--;
	}
	
	public boolean visited(Object o) {
		if (branchCount > 0) {
			if (visited == null)
				visited = new HashSet<Object>();
			return !visited.add(o);
		}
		
		return false;
	}
}
