package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public void render(Graphics g){
		g.setColor( new Color(155,155,155));
		g.drawLine(src.x,src.y,dst.x,dst.y);
	}
}
