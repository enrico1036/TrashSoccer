package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.math.Rectangle;

public class Score {
	int score;
	boolean isIncremented;
	
	public Score(){
		reset();
	}
	
	public void reset(){
		score = 0;
		isIncremented = false;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean isIncremented(){
		boolean isIncremented = this.isIncremented;
		this.isIncremented = false;
		return isIncremented;
	}
	public void addGoal(){
		score++;
		isIncremented = true;
		System.out.println(this.toString() + "  " + score);
	}
	
	public boolean hasWon(int maximum){
		if(score >= maximum)
			return true;
		return false;
	}
}
