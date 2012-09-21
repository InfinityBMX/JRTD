package com.jeffreyregalia;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
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
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setResizable(false);
      frame.setVisible(true);
      
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      
      canvas.requestFocus();
   }
   
        
   private class MouseControl extends MouseAdapter{
      
   }
   
   long desiredFPS = 60;
   long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;
    
   boolean running = true;
   
   public void run(){
      
      long beginLoopTime;
      long endLoopTime;
      long currentUpdateTime = System.nanoTime();
      long lastUpdateTime;
      long deltaLoop;
      
      for(int i=0;i<3;i++)enemies.add(new Enemy(0,random.nextInt(HEIGHT), 10));
      towers.add(new Tower(100,200,WIDTH/2,HEIGHT/2));
      towers.add(new Tower(100,200, WIDTH/4,HEIGHT/4));
      towers.add(new Tower(100,200, WIDTH*3/4,HEIGHT*3/4));
      towers.add(new Tower(100,200, WIDTH/4,HEIGHT*3/4));
      towers.add(new Tower(100,200, WIDTH*3/4,HEIGHT/4));
      
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
      g.clearRect(0, 0, WIDTH, HEIGHT);
      render(g);
      g.dispose();
      bufferStrategy.show();
   }
   
   ArrayList<Tower> towers = new ArrayList<Tower>();
   ArrayList<Enemy> enemies = new ArrayList<Enemy>();

   protected void update(int deltaTime){
	   for(int i = 0; i < enemies.size(); i++){
		   if(!enemies.get(i).isAlive()) enemies.remove(i);
	   }
	   if(!enemies.isEmpty()){
		   for(Tower tower : towers){
			   tower.findTarget(enemies);
		   }
		   for(Enemy enemy : enemies)
			   enemy.update(deltaTime);
	   }
	   for(int i = enemies.size(); i < 20; i++)
		   enemies.add(new Enemy(0,random.nextInt(HEIGHT-200)+100, 10));
	   for(Tower tower : towers){
		   tower.update(deltaTime);
	   }

   }
   
   /**
    * Rewrite this method for your game
    */
   protected void render(Graphics2D g){
	   for(Tower tower : towers){
		   tower.render(g);
	   }
	   for(Enemy enemy: enemies)
		   enemy.render(g);
   }
   
   public static void main(String [] args){
      JRTD ex = new JRTD();
      new Thread(ex).start();
   }

}