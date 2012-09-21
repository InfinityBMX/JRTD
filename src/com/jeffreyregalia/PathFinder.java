package com.jeffreyregalia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PathFinder {
	public final TreeSet<Node> frontline;
	
	public PathFinder(){
		this.frontline = new TreeSet<Node>(new NodeComparator(this));
	}
	
	public List<Node> search(Node start, Filter<Node> target){
		this.frontline.add(start);
		
		while(!this.frontline.isEmpty()){
			Node found = this.step(target);
			
			if(found != null){
				return this.getPath(found);
			}
		}
		
		return null;
	}
	
	public Node step(Filter<Node> target){
		Node head = this.frontline.pollFirst();
		if(head == null)
			return null;
		
		this.visit(head);
		
		if(target.accept(head))
			return head;
		
		for(Edge edge : head.edges){
			if(this.isVisited(edge.dst))
				continue;
			
			float last = this.getAccumulatedWeight(edge.dst);
			float curr = this.getAccumulatedWeight(head) + edge.weight;
			if(last != 0.0f && last <= curr)
				continue;
			
			this.frontline.remove(edge.dst);
			this.setAccumulatedWeight(edge.dst, curr);
			this.frontline.add(edge.dst);
		}
		
		return null;
	}
	
	public List<Node> getPath(Node node){
		LinkedList<Node> path = new LinkedList<Node>();
		path.addFirst(node);
		
		do{
			path.addFirst(node = this.getPreviousNode(node));
		} while( this.getAccumulatedWeight(node) != 0.0f);
		
		return path;
	}
	
	private Node getPreviousNode(Node node){
		Edge best = null;
		for(Edge edge : node.backtrack)
		{
			if(this.isVisited(edge.src))
				if(best == null || this.getAccumulatedWeight(edge.src) < this.getAccumulatedWeight(best.src))
					best = edge;
		}
		if(best == null)
			throw new IllegalStateException();
		return best.src;
	}
	
	private final Set<Node> visited = new HashSet<Node>();
	
	public void visit(Node node){
		this.visited.add(node);
	}
	
	public boolean isVisited(Node node){
		return this.visited.contains(node);
	}
	
	private final Map<Node, Float> accumulatedWeight = new HashMap<Node, Float>();
	
	float getAccumulatedWeight(Node node){
		Float weight = this.accumulatedWeight.get(node);
		return weight == null ? 0.0f : weight.floatValue();
	}
	
	void setAccumulatedWeight(Node node, float weight){
		this.accumulatedWeight.put(node, Float.valueOf(weight));
	}
}
