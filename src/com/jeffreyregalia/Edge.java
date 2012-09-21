package com.jeffreyregalia;

public class Edge {
	public final Node src, dst;
	public final float weight;
	
	public Edge(Node src, Node dst, float weight){
		this.src = src;
		this.dst = dst;
		this.weight = weight;
		
		src.edges.add(this);
		dst.backtrack.add(this);
	}
}
