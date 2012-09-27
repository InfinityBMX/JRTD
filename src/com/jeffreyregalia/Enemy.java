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
	final int maxHp;
	Node targetNode;
	Node currentNode;
	Node finishNode;
	LinkedList<Node> path;
	PathFinder finder = new PathFinder();
	int pathIndex = 0;
	double moveSpeed;
	double moved;
	
	Enemy(Node currentNode, Node finishNode, int hp){
		this.x = currentNode.x;
		this.y = currentNode.y;
		this.currentNode = currentNode;
		this.finishNode = finishNode;
		this.hp = hp;
		this.maxHp = hp;
		//this.size = (int) (hp*2);
		this.size = 16;
		path = (LinkedList<Node>) finder.search(this.currentNode, new FinishLine(finishNode));
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
		this.moveSpeed = xDirection != 0 && yDirection != 0 ? Math.sqrt((.1*.1)/2) : .1;
		this.moved = xDirection * time * this.moveSpeed;
		this.x += xDirection * time * this.moveSpeed;
		this.y += yDirection * time * this.moveSpeed;
		//size = hp * 2;
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
		//Draw hp indicator
		int barPixels = (int)(hp/(float)maxHp*size-2);
		barPixels = barPixels < 1 ? 1 : barPixels;
		g.fillRect(x-size/2, y-size, size, 6);
		g.drawString(""+hp, x-size/2, y-size);
		g.setColor( new Color(0,255,0));
		g.fillRect(x-size/2+1, y-size+1, barPixels, 4);
		
		//Draw path
//		g.setColor( new Color(0, 255, 0));
//		for(Node node : path)
//			node.render(g);
		
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
		path = (LinkedList<Node>) finder.search(this.currentNode, new FinishLine(finishNode));
		pathIndex = 0;
		targetNode = path.get(pathIndex);
	}
}
