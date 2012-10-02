package com.jeffreyregalia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteManager {
	public static BufferedImage towerImage;
	public static final int rows = 2;
	public static final int columns = 4;
	public static final int spriteHeight = 32;
	public static final int spriteWidth = 32;
	public static final int[] WALL_BLOCK = {1,1};
	public static final int[] BASE_TOWER = {0,0};
	public static BufferedImage[][] sprites;
	
	SpriteManager() throws IOException{
		if(towerImage == null){
			towerImage = ImageIO.read(new File("resources/towers.png"));
			sprites = new BufferedImage[rows][columns];
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++){
					sprites[i][j] = towerImage.getSubimage(j*spriteWidth, i*spriteHeight, spriteWidth, spriteHeight);
				}
		}
	}
	
	public BufferedImage getSprite(int[] spriteType){
		return sprites[spriteType[0]][spriteType[1]];
	}
}
