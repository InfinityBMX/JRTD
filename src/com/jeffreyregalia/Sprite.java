package com.jeffreyregalia;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {
	private ArrayList<BufferedImage> images;
	private int totalFrames;
	private int currentFrame;
	private int updatesPerImage;
	private int updatesForImage;
	
	Sprite(ArrayList<BufferedImage> images){
		this.images = images;
		this.totalFrames = images.size();
		this.currentFrame = 0;
		this.updatesPerImage = 40;
		this.updatesForImage = 0;
	}
	
	public BufferedImage getImage(){
		if(totalFrames > 1){
			++updatesForImage;
			if(updatesForImage == updatesPerImage){
				++currentFrame;
				updatesForImage = 0;
			}
			if(currentFrame == totalFrames)
				currentFrame = 0;
		}
		return this.images.get(currentFrame);
	}
}
