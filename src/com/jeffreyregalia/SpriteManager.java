package com.jeffreyregalia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteManager {
	public static BufferedImage towerImage;
	public static final int rows = 2;
	public static final int columns = 4;
	public static final int spriteHeight = 32;
	public static final int spriteWidth = 32;
	public static final int[] WALL_BLOCK = {1,3};
	public static final int[] FAKE_TOWER = {1,1};
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
	
	public Sprite getSprite(int[] spriteType){
		ArrayList<BufferedImage> theImages = new ArrayList<BufferedImage>();
		if(spriteType == BASE_TOWER){
			theImages.add(sprites[spriteType[0]][spriteType[1]]);
			theImages.add(sprites[0][1]);
			theImages.add(sprites[0][2]);
			theImages.add(sprites[0][3]);
		}else
			theImages.add(sprites[spriteType[0]][spriteType[1]]);
		Sprite theSprite = new Sprite(theImages);
		return theSprite;
	}
}
