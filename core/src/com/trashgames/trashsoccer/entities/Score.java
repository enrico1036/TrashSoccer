package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.math.Rectangle;

public class Score {
	int score;
	Rectangle bounds;
	public Score(Rectangle rect){
		bounds = rect;
		reset();
	}
	
	public void reset(){
		score = 0;
	}
	public void addGoal(){
		score++;
		System.out.println(this.toString() + "  " + score);
	}
	
	public boolean hasWon(int maximum){
		if(score >= maximum)
			return true;
		return false;
	}
	
	public void render(){
		
	}
}
