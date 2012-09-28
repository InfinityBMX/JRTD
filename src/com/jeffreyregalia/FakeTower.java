package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;

public class FakeTower implements Entity {
	private int x = 0;
	private int y = 0;
	private final int size = 32;
	
	public void render(Graphics g){
//		if(x != 0 && y != 0){
			g.setColor( new Color(200,200,200));
			g.fillRect(x-size/2, y-size/2, size, size);
//		}
	}
	public void update(int time){
		// We don't do anything based on time
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
