package com.jeffreyregalia;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class JRTD implements Runnable{
   
   final int WIDTH = 1024;
   final int HEIGHT = 768;

   JFrame frame;
   Canvas canvas;
   Canvas canvas2;
   BufferStrategy bufferStrategy;
   Random random = new Random();
   
   public JRTD(){
      frame = new JFrame("Basic Game");
      
      JPanel panel = (JPanel) frame.getContentPane();
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT+155));
      panel.setLayout(null);
      panel.setBackground(Color.BLACK);
      
      canvas = new Canvas();
      canvas.setBounds(5, 160, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
      canvas2 = new Canvas();
      canvas2.setIgnoreRepaint(true);
      canvas2.setBounds(5, 5, WIDTH, 150);
      panel.add(canvas2);
      panel.add(canvas);
      
      MouseControl mouse = new MouseControl();
      KeyControl keyboard = new KeyControl();
      canvas.addMouseListener(mouse);
      canvas.addMouseMotionListener(mouse);
      canvas.addKeyListener(keyboard);
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      
      canvas.requestFocus();
   }
   
   private class KeyControl extends KeyAdapter{
	   public void keyPressed(KeyEvent e){
		   switch (e.getKeyCode()) {
		   		case KeyEvent.VK_ESCAPE: running = false;
		   				break;
		   		case KeyEvent.VK_ENTER: canPlaceTowers = false;
		   				gameStarted = true;
		   				cursorTower = null;
		   				break;
		   }
	   }
   }
   
   private class MouseControl extends MouseAdapter{
      public void mouseClicked(MouseEvent e){
    	  if(canPlaceTowers){
			  Node placementNode = gameBoard.getNodeAtLocation(e.getX(), e.getY());
    		  addTower(32,150,placementNode);
    		  //recalculatePaths = true;
    	  } else {
    		  gameStarted = true;
    	  }
      }
      
      public void mouseMoved(MouseEvent e){
    	  if(canPlaceTowers)
    		  if(cursorTower != null){
    			  Node placementNode = gameBoard.getNodeAtLocation(e.getX(), e.getY());
    			  cursorTower.setLocation(placementNode.x, placementNode.y);
    		  }
      }
   }
   
   long desiredFPS = 60;
   long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
   int maxEnemies = 5;
   int maxTowers = 10;
   int spawnDelay = 500;
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
      frame.dispose();
   }
   
   private void render() {
	  Graphics2D g2 = (Graphics2D) canvas2.getGraphics();
	  g2.setBackground( Color.WHITE );
	  g2.clearRect(0, 0, WIDTH, 150);
      Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
      g.setBackground( new Color(255,255,255));
      g.clearRect(0, 0, WIDTH, HEIGHT);
      render(g);
      g.dispose();
      bufferStrategy.show();
   }
   
   List<Tower> towers = new CopyOnWriteArrayList<Tower>();
   List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
   GameBoard gameBoard = new GameBoard(WIDTH,HEIGHT);
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
				   	enemies.add(new Enemy(gameBoard.getNode(0,0), gameBoard.getMaxNode(),100));
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
	   gameBoard.render(g);
	   for(Tower tower : towers){
		   tower.render(g);
	   }
	   for(Enemy enemy : enemies)
		   enemy.render(g);
	   
	   if(cursorTower != null)
		   cursorTower.render(g);
	   for(Node node : gameBoard.getNodeList())
		   node.render(g);
	   
//	   gameBoard.renderEdges(g);
   }
   
   public static void main(String [] args){
	   JRTD ex = new JRTD();
	   new Thread(ex).start();
   }

   // Check gameboard for obstacles and add tower if none exist
   public boolean addTower(int size, int radius, Node placementNode){

	   if(placementNode.isUsed())
		   return false;
	   
	   towers.add(new Tower(size,radius,placementNode));
	   placementNode.use();

	   return true;
   }
}