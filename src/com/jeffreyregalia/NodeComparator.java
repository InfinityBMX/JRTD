package com.jeffreyregalia;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	private final PathFinder finder;
	
	public NodeComparator(PathFinder finder){
		this.finder = finder;
	}
	
	@Override
	public int compare(Node o1, Node o2) {
		if(o1 == o2)
			return 0;
		float w1 = finder.getAccumulatedWeight(o1);
		float w2 = finder.getAccumulatedWeight(o2);
		return w1 < w2 ? -1 : +1;
	}
}
