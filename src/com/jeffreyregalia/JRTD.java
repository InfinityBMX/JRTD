package com.jeffreyregalia;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class JRTD implements Runnable{
   
   final int WIDTH = 800;
   final int HEIGHT = 600;

   JFrame frame;
   Canvas canvas;
   BufferStrategy bufferStrategy;
   Random random = new Random();
   
   public JRTD(){
      frame = new JFrame("Basic Game");
      
      JPanel panel = (JPanel) frame.getContentPane();
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
      panel.setLayout(null);
      
      canvas = new Canvas();
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
      
      panel.add(canvas);
      
      canvas.addMouseListener(new MouseControl());
      canvas.addMouseMotionListener(new MouseControl());
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      
      canvas.requestFocus();
   }
   
        
   private class MouseControl extends MouseAdapter{
      public void mouseClicked(MouseEvent e){
    	  if(canPlaceTowers){
    		  addTower(45,200,e.getX(),e.getY());
    		  //recalculatePaths = true;
    	  } else {
    		  gameStarted = true;
    	  }
      }
      
      public void mouseMoved(MouseEvent e){
    	  if(canPlaceTowers)
    		  if(cursorTower != null){
    			  cursorTower.setLocation(e.getX(), e.getY());
    		  }
      }
   }
   
   long desiredFPS = 60;
   long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
   int maxEnemies = 10;
   int maxTowers = 10;
   int spawnDelay = 150;
   int timeWaited = 0;
   boolean canPlaceTowers = true;
   boolean recalculatePaths = false;
   boolean gameStarted = false;
    
   boolean running = true;
   
   public void run(){
      
      long beginLoopTime;
      long endLoopTime;
      long currentUpdateTime = System.nanoTime();
      long lastUpdateTime;
      long deltaLoop;
      
      while(running){
         beginLoopTime = System.nanoTime();
         
         render();
         
         lastUpdateTime = currentUpdateTime;
         currentUpdateTime = System.nanoTime();
         update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));
         
         endLoopTime = System.nanoTime();
         deltaLoop = endLoopTime - beginLoopTime;
           
         if(deltaLoop > desiredDeltaLoop){
           //Do nothing. We are already late.
         }else{
        	 try{
        		 Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
        	 }catch(InterruptedException e){
        		 //Do nothing
        	 }
         }
      }
   }
   
   private void render() {
      Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
      g.setBackground( new Color(255,255,255));
      g.clearRect(0, 0, WIDTH, HEIGHT);
      render(g);
      g.dispose();
      bufferStrategy.show();
   }
   
   ArrayList<Tower> towers = new ArrayList<Tower>();
   ArrayList<Enemy> enemies = new ArrayList<Enemy>();
   GameBoard gameBoard = new GameBoard(HEIGHT/30,WIDTH/30,30,30);
   FakeTower cursorTower = new FakeTower();

   protected void update(int deltaTime){
	   timeWaited += deltaTime;
	   if(gameStarted){
		   for(int i = 0; i < enemies.size(); i++){
			   if(recalculatePaths){
				   enemies.get(i).recalculatePath();
			   }
			   if(!enemies.get(i).isAlive()) enemies.remove(i);
		   }
		   recalculatePaths = false;
		   if(!enemies.isEmpty()){
			   for(Tower tower : towers){
				   tower.findTarget(enemies);
			   }
			   for(Enemy enemy : enemies)
				   enemy.update(deltaTime);
		   }
		   if(enemies.size() < maxEnemies)
			   if(timeWaited >= spawnDelay){
				   	enemies.add(new Enemy(gameBoard.getNode(4,0), 10));
		   			timeWaited = 0;
			   }
		   for(Tower tower : towers){
			   tower.update(deltaTime);
		   }
	   }
	   if(towers.size() >= maxTowers){
		   cursorTower = null;
		   canPlaceTowers = false;
		   gameStarted = true;
	   }

   }
   
   protected void render(Graphics2D g){
	   for(Tower tower : towers){
		   tower.render(g);
	   }
	   for(Enemy enemy : enemies)
		   enemy.render(g);
	   
	   if(cursorTower != null)
		   cursorTower.render(g);
//	   for(Node node : gameBoard.getNodeList())
//		   node.render(g);
	   
//	   gameBoard.renderEdges(g);
   }
   
   public static void main(String [] args){
	   JRTD ex = new JRTD();
	   new Thread(ex).start();
   }

   // Check gameboard for obstacles and add tower if none exist
   public boolean addTower(int size, int radius, int x, int y){
	   ArrayList<Node> testNodes = gameBoard.getNodesInRect((int) (x-.5*size), (int) (y - .5*size), (int) (x+.5*size), (int) (y+.5*size));
	   for(Node node : testNodes)
		   if(node.isUsed())
			   return false;
	   
	   towers.add(new Tower(size,radius,x,y));
	   for(Node node : testNodes)
		   node.use();

	   return true;
   }
}