package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Enemy implements Entity{
	int x;
	int y;
	int size;
	int hp;
	
	Enemy(int x, int y, int hp){
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.size = hp*5;
	}
	
	public void attack(int damage){
		this.hp -= damage;
	}
	
	public int getHP(){
		return this.hp;
	}
	
	public void update(int time){
		x += time * .1;
		size = hp * 5;
		if(x > 800){
			leaked();
		}
	}
	
	public void render(Graphics g){
		g.setColor( new Color(0,0,0));
		g.fillRect(x, y, size, size);
		//Draw hit box
		g.setColor( new Color(255,255,255));
		for(Point point: getHitBox()){
			g.fillRect(point.x, point.y, 1, 1);
		}
	}

	public Point[] getHitBox(){
		Point[] hitBox = new Point[4];
		hitBox[0] = new Point(x,y);
		hitBox[1] = new Point(x+size-1,y);
		hitBox[2] = new Point(x,y+size-1);
		hitBox[3] = new Point(x+size-1,y+size-1);
		return hitBox;
	}
	
	public boolean isAlive(){
		return this.hp > 0;
	}
	
	public void leaked(){
		this.hp = 0;
	}
}
