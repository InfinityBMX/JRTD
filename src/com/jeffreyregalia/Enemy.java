package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

public class Enemy implements Entity{
	int x;
	int y;
	int size;
	int hp;
	Node targetNode;
	Node currentNode;
	LinkedList<Node> path;
	PathFinder finder = new PathFinder();
	int pathIndex = 0;
	double moveSpeed;
	double moved;
	
	Enemy(Node currentNode, int hp){
		this.x = currentNode.x;
		this.y = currentNode.y;
		this.currentNode = currentNode;
		this.hp = hp;
		this.size = (int) (hp*.1);
		path = (LinkedList<Node>) finder.search(this.currentNode, new FinishLine());
		targetNode = path.get(pathIndex);
	}
	
	public void attack(int damage){
		this.hp -= damage;
	}
	
	public int getHP(){
		return this.hp;
	}
	
	public void update(int time){
		int xDirection = this.x < this.targetNode.x ? 1 : (this.x == this.targetNode.x ? 0 : -1);
		int yDirection = this.y < this.targetNode.y ? 1 : (this.y == this.targetNode.y ? 0 : -1);
		moveSpeed = xDirection != 0 && yDirection != 0 ? Math.sqrt((.1*.1)/2) : .1;
		moved = xDirection * time * moveSpeed;
		x += xDirection * time * moveSpeed;
		y += yDirection * time * moveSpeed;
		size = hp < 50 ? 5 : (int) (hp * .1);
		if(this.x == this.targetNode.x && this.y == targetNode.y){
			if(this.targetNode != path.getLast()){
				this.currentNode = this.targetNode;
				this.targetNode = path.get(++pathIndex);
			}else{
				leaked();
			}
		}
	}
	
	public void render(Graphics g){
		g.setColor( new Color(0,0,0));
		g.fillOval(x-size/2, y-size/2, size, size);
		
		//Draw path
		g.setColor( new Color(0, 255, 0));
		for(Node node : path)
			node.render(g);
		
//		g.drawString("Move Speed: "+moved, 100, 100);
		//Draw hit box
//		g.setColor( new Color(255,255,255));
//		for(Point point: getHitBox()){
//			g.fillRect(point.x, point.y, 1, 1);
//		}
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
	
	public Node getNode(){
		return this.currentNode;
	}
	
	public void recalculatePath(){
		path = (LinkedList<Node>) finder.search(this.currentNode, new FinishLine());
		pathIndex = 0;
		targetNode = path.get(pathIndex);
	}
}
