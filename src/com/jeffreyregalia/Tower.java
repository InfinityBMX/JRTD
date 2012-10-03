package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class Tower implements Entity{
	private int size;
	int radius;
	int x;
	int y;
	Node myNode;
	Enemy target;
	double targetDistance;
	Point targetPoint;
	int timeSinceAttack = 0;
	int attackTime = 250;
	int attackFrames = 0;
	boolean attacked = false;
	int power = 1;
	Projectile shot = null;
	boolean selected;
	private int powerUpgrades = 0;
	private int radiusUpgrades = 0;
	private Sprite towerSprite;
	private BufferedImage currentImage;
	
	Tower(int size, int radius, Node myNode, SpriteManager spriteManager){
		this.size = size;
		this.radius = radius;
		this.myNode = myNode;
		this.x = myNode.x;
		this.y = myNode.y;
		this.selected = false;
		this.towerSprite = spriteManager.getSprite(SpriteManager.BASE_TOWER);
		this.currentImage = this.towerSprite.getImage();
	}
	
	public void update(int time){
		this.timeSinceAttack += time;
		if(this.timeSinceAttack > this.attackTime)
			this.timeSinceAttack = this.attackTime;
		if(this.target == null){
			this.targetDistance = 0.0;
			this.targetPoint = null;
		}else{
			if(this.timeSinceAttack == this.attackTime){
				if(this.shot == null){
					if(this.target.getHP() < 1){
						this.target = null;
					}else{
						attack();
						this.timeSinceAttack = 0;
						this.attacked = true;
					}

				}
			}
		}
		if(shot != null){
			if(!shot.isAlive()){
				shot = null;
			}else{
				shot.update(time);
			}
		}
		// fire?
		// rotate gun?
		this.currentImage = towerSprite.getImage();
	}
	
	public void render(Graphics g){
		//draw tower
		g.drawImage(this.currentImage, this.x-this.size/2, this.y-this.size/2, null);
		//draw radius
		if(this.selected){
			g.setColor( new Color(100,200,100));
			g.drawOval(this.x-this.radius, this.y-this.radius, this.radius*2, this.radius*2);
		}

		if(this.shot != null)
			this.shot.render(g);
	}
	
	public void findTarget(List<Enemy> enemyList){
		double tempDistance = 0.0;
		if(this.target==null){
			for(Enemy enemy: enemyList){
				for(Point point: enemy.getHitBox()){
					double distance = Math.sqrt((x-point.getX())*(x-point.getX())+(y-point.getY())*(y-point.getY()));
					if(distance < radius)
						if(tempDistance == 0.0 || distance < tempDistance){
							tempDistance = distance;
							this.targetDistance = distance;
							this.targetPoint = point;
							this.target = enemy;
						}
				}
			}
			if(tempDistance == 0.0){
				this.targetPoint = null;
				this.targetDistance = 0.0;
				this.target = null;
			}
		}else{
			boolean stillValid = false;
			for(Point point: this.target.getHitBox()){
				double distance = Math.sqrt((x-point.getX())*(x-point.getX())+(y-point.getY())*(y-point.getY()));
				if(distance < radius &&(tempDistance == 0.0 || distance < tempDistance)){
					tempDistance = distance;
					this.targetDistance = distance;
					this.targetPoint = point;
					stillValid = true;
				}
			}
			if(!stillValid){
				this.target = null;
				this.targetDistance = 0.0;
				this.targetPoint = null;
			}
		}
	}

	
	public void setTarget(){
		
	}

	public Entity getTarget(){
		return this.target;
	}
	
	public boolean isAlive(){
		return true;
	}
	
	public void attack(){
		shot = new Projectile(this.myNode, this.target, this.power);
	}
	
	public void selectTower(){
		this.selected = true;
	}
	
	public void unselectTower(){
		this.selected = false;
	}
	
	public boolean isAt(Node myNode){
		return this.myNode.equals(myNode);
	}
	
	public BufferedImage getImage(){
		return this.currentImage;
	}
	
	public int getPowerUpgradeCost(){
		return (int) Math.pow(2, this.powerUpgrades);
	}
	
	public int getRadiusUpgradeCost(){
		return (int) Math.pow(2, this.radiusUpgrades);
	}
	
	public void increasePower(int bonusPower){
		this.power += bonusPower;
		this.powerUpgrades++;
	}
	
	public void increaseRadius(int bonusRadius){
		this.radius += bonusRadius;
		this.radiusUpgrades++;
	}

	public int getRadius() {
		return this.radius;
	}

	public int getPower() {
		return this.power;
	}
}
