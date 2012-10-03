package com.jeffreyregalia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpriteManager {
	private BufferedImage spriteSheet;
	private final int rows = 2;
	private final int columns = 4;
	private final int spriteHeight = 32;
	private final int spriteWidth = 32;
	public static final int[] WALL_BLOCK = {1,3};
	public static final int[] FAKE_TOWER = {1,1};
	public static final int[] BASE_TOWER = {0,0};
	public static final String TOWER_FILE = "resources/towers.png";
	public static final String ENEMY_FILE = "resources/enemies.png";
	private BufferedImage[][] sprites;
	
	SpriteManager(String filePath) throws IOException{
		if(spriteSheet == null){
			spriteSheet = ImageIO.read(new File(filePath));
			sprites = new BufferedImage[rows][columns];
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++){
					sprites[i][j] = spriteSheet.getSubimage(j*spriteWidth, i*spriteHeight, spriteWidth, spriteHeight);
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
