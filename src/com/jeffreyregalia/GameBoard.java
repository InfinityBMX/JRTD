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
}
