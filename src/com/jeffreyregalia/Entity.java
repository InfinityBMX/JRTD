package com.jeffreyregalia;

import java.awt.Graphics;

public interface Entity {
	void update(int time);
	void render(Graphics g);
	boolean isAlive();
}
