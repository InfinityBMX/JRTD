package com.jeffreyregalia;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {
	private int waveNumber;
	private CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
	private int numberOfEnemies;
	private Enemy enemyModel;
	private int enemyValue;
	
	Wave(int waveNumber, int numberOfEnemies, int enemyValue, Enemy enemyModel){
		this.waveNumber = waveNumber;
		this.numberOfEnemies = numberOfEnemies;
		this.enemyModel = enemyModel;
		
		for(int i = 0;i < numberOfEnemies; i++)
			enemies.add(new Enemy(enemyModel.getNode(),enemyModel.getFinishNode(),enemyModel.getHP()));
		
		for(Enemy enemy : enemies)
			enemy.setValue(enemyValue);
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemies(){
		return this.enemies;
	}
}
