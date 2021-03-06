package com.jeffreyregalia;

import java.awt.Graphics;

public class FakeTower implements Entity {
	private int x = 0;
	private int y = 0;
	private final int size = 32;
	private Sprite towerSprite;
	
	FakeTower(SpriteManager spriteManager){
		this.towerSprite = spriteManager.getSprite(SpriteManager.FAKE_TOWER);
	}
	
	public void render(Graphics g){
		g.drawImage(towerSprite.getImage(), x-size/2, y-size/2, null);
	}
	public void update(int time){
		// We don't do anything based on time
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
