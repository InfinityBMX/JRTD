package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile implements Entity {
	float x;
	float y;
	int power;
	float bearing;
	final double moveSpeed = .5;
	Enemy target;
	final int size = 8;
	boolean alive;
	double distanceFromTarget;
	
	Projectile(Node start, Enemy target, int power){
		this.x = start.x;
		this.y = start.y;
		this.power = power;
		this.bearing = getBearing(start.x,start.y,target.x,target.y);
		this.alive = true;
		this.target = target;
		this.distanceFromTarget = Math.sqrt((start.x-target.x)*(start.x-target.x)+(start.y-target.y)*(start.y-target.y));
	}

	@Override
	public void render(Graphics g) {
		g.setColor( new Color(230,230,230));
		g.fillOval((int) (x-size/2), (int) (y-size/2), size, size);
	}

	@Override
	public void update(int time) {
		this.bearing = getBearing(x,y,target.x,target.y);
		this.x += Math.cos(this.bearing) * time * this.moveSpeed;
		this.y += Math.sin(this.bearing) * time * this.moveSpeed;
		if(getCurrentDistance() <= 10)
			explode();
	}
	
	public void explode(){
		if(this.target != null){
			this.target.attack(power);
		}
		this.alive = false;
	}

	public boolean isAlive(){
		return this.alive;
	}
	
	public double getCurrentDistance(){
		return Math.sqrt((this.x-this.target.x)*(this.x-this.target.x)+(this.y-this.target.y)*(this.y-this.target.y));
	}
	
	public float getBearing(float x, float y, float x1, float y1){
		float dx=x1-x;
		float dy=y1-y;
		double d = Math.atan2(dy, dx);
		if(d<0)
			d=Math.PI*2+d;
		return (float)d;
	}
}
