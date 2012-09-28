package com.jeffreyregalia;

public class FinishLine implements Filter<Node>{
	private Node targetNode;
	public FinishLine(Node targetNode){
		this.targetNode = targetNode;
	}
	
	public boolean accept(Node node){
		return node == targetNode;
	}
}
