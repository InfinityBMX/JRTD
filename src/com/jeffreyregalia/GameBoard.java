package com.jeffreyregalia;

import java.util.ArrayList;

public class GameBoard {
	private final Node[][] gameboard;
	private final int rows;
	private final int columns;
	
	public GameBoard(int rows, int columns, int width, int height){
		this.rows = rows;
		this.columns = columns;
		gameboard = new Node[rows][columns];
		for(int x=0; x<rows; x++)
			for(int y=0; y<columns; y++){
				gameboard[x][y] = new Node((int) (x*width+.5*width),(int) (y*height+.5*height));
			}
	}
	
	public ArrayList<Node> getNodeList(){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int x=0; x<rows; x++)
			for(int y=0; y<columns; y++)
				nodes.add(gameboard[x][y]);		
		
		return nodes;
	}
	
	public ArrayList<Node> getNodesInRect(int x1, int y1, int x2, int y2){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int x=0; x<rows; x++)
			for(int y=0; y<columns; y++){
				int x0 = gameboard[x][y].x;
				int y0 = gameboard[x][y].y;
				if((x1 > x0 && x0 > x2) || (x1 < x0 && x0 < x2))
					if((y1 > y0 && y0 > y2) || (y1 < y0 && y0 < y2))
						nodes.add(gameboard[x][y]);		
			}
		return nodes;
	}
}
