package com.jeffreyregalia;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameBoard {
	private final Node[][] gameboard;
	private final ArrayList<Edge> edges;
	private final int rows;
	private final int columns;
	
	public GameBoard(int rows, int columns, int width, int height){
		this.rows = rows;
		this.columns = columns;
		this.gameboard = new Node[rows][columns];
		this.edges = new ArrayList<Edge>();
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++){
				gameboard[y][x] = new Node((int) (x*width+.5*width),(int) (y*height+.5*height));
			}
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++){
				if(y != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y-1][x], 1.0f));
				if(y < (rows -1))
					this.edges.add(new Edge(gameboard[y][x], gameboard[y+1][x], 1.0f));
				if(x != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y][x-1], 1.0f));
				if(x < (columns-1))
					this.edges.add(new Edge(gameboard[y][x], gameboard[y][x+1], 1.0f));
/*				if(x != 0 && y != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y-1][x-1], (float) Math.sqrt(2)));
				if(x != columns-1 && y != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y-1][x+1], (float) Math.sqrt(2)));
				if(x != columns-1 && y != rows -1)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y+1][x+1], (float) Math.sqrt(2)));
				if(x != 0 && y != rows -1)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y+1][x-1], (float) Math.sqrt(2)));
*/			}
	
	}
	
	public ArrayList<Node> getNodeList(){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++)
				nodes.add(gameboard[y][x]);		
		
		return nodes;
	}
	
	public ArrayList<Node> getNodesInRect(int x1, int y1, int x2, int y2){
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++){
				int x0 = gameboard[y][x].x;
				int y0 = gameboard[y][x].y;
				if((x1 > x0 && x0 > x2) || (x1 < x0 && x0 < x2))
					if((y1 > y0 && y0 > y2) || (y1 < y0 && y0 < y2))
						nodes.add(gameboard[y][x]);		
			}
		return nodes;
	}

	public void renderEdges(Graphics g){
		for(Edge edge : edges){
			edge.render(g);
		}
	}
	
	public Node[][] getGameboard(){
		return this.gameboard;
	}
	
	public Node getNode(int y, int x){
		return this.gameboard[y][x];
	}
	
	public Node getNodeAtLocation(int x, int y){
		return this.gameboard[((y+32)/32)-1][((x+32)/32)-1];
	}
}
