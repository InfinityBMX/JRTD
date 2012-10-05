package com.jeffreyregalia;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameBoard {
	private final Node[][] gameboard;
	private final ArrayList<Edge> edges;
	private final int rows;
	private final int columns;
	private final int nodeSize = 32;
	private Sprite blockSprite;
	
	public GameBoard(int width, int height, SpriteManager spriteManager){
		this.rows = height/nodeSize;
		this.columns = width/nodeSize;
		this.gameboard = new Node[rows][columns];
		this.edges = new ArrayList<Edge>();
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++){
				gameboard[y][x] = new Node((int) (x*nodeSize+.5*nodeSize),(int) (y*nodeSize+.5*nodeSize));
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
				if(y == 0 || y == rows-1 || x == 0 || x == columns - 1)
					gameboard[y][x].use();
				//diagonal edges
/*				if(x != 0 && y != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y-1][x-1], (float) Math.sqrt(2)));
				if(x != columns-1 && y != 0)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y-1][x+1], (float) Math.sqrt(2)));
				if(x != columns-1 && y != rows -1)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y+1][x+1], (float) Math.sqrt(2)));
				if(x != 0 && y != rows -1)
					this.edges.add(new Edge(gameboard[y][x], gameboard[y+1][x-1], (float) Math.sqrt(2)));
*/			}
		getStartNode().unuse();
		getFinishNode().unuse();
		
		blockSprite = spriteManager.getSprite(SpriteManager.WALL_BLOCK);
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
	
	public int getColumns(){
		return this.columns;
	}
	
	public int getRows(){
		return this.rows;
	}
	
	public Node getMaxNode(){
		return getNode(this.rows-1,this.columns-1);
	}
	
	public Node getFinishNode(){
		return getNode(rows/2, this.columns-1);
	}
	
	public Node getStartNode(){
		return getNode(rows/2, 0);
	}
	
	public boolean blocksPath(Node newNode){
		newNode.use();
		LinkedList<Node> tempPath = (LinkedList<Node>) new PathFinder().search(getStartNode(), new FinishLine(getFinishNode()));
		newNode.unuse();
		
		if(tempPath == null)
			return true;
		
		return false;
	}
	
	public void render(Graphics g){
		Node temp = getStartNode();
		g.setColor( new Color(0,255,0));
		g.fillRect((int) (temp.x-nodeSize*.5),(int) (temp.y-nodeSize*.5), nodeSize, nodeSize);
		temp = getFinishNode();
		g.setColor( new Color(255,0,0));
		g.fillRect((int) (temp.x-nodeSize*.5),(int) (temp.y-nodeSize*.5), nodeSize, nodeSize);
		g.setColor( new Color(160,82,45));
		for(int y=0; y<rows; y++)
			for(int x=0; x<columns; x++){
				if(gameboard[y][x].isUsed()){
					temp = gameboard[y][x];
					//g.fillRect((int) (temp.x-nodeSize*.5),(int) (temp.y-nodeSize*.5), nodeSize, nodeSize);
					g.drawImage(blockSprite.getImage(),(int) (temp.x-nodeSize*.5),(int) (temp.y-nodeSize*.5), null);
				}
			}
	}
}
