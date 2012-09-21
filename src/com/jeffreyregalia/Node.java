package com.jeffreyregalia;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public final int x, y;
	public final List<Edge>	edges;
	public final List<Edge>	backtrack;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		this.edges = new ArrayList<Edge>();
		this.backtrack = new ArrayList<Edge>();
	}
}
