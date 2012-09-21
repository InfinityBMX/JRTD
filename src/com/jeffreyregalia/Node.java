package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Node {
	public final int x, y;
	public final List<Edge>	edges;
	public final List<Edge>	backtrack;
	public boolean used;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		this.edges = new ArrayList<Edge>();
		this.backtrack = new ArrayList<Edge>();
		this.used = false;
	}
	
	public void render(Graphics g){
		if(this.used)
			g.setColor( new Color(255,0,0));
		else
			g.setColor( new Color(0,0,255));
		g.fillRect(x, y, 1, 1);
	}
	
	public void use(){
		this.used = true;
	}
	
	public void unuse(){
		this.used = false;
	}
	
	public boolean isUsed(){
		return this.used;
	}
}
