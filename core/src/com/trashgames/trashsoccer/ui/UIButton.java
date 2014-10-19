package com.trashgames.trashsoccer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class UIButton {
	private String text;
	public BitmapFont font;
	private Sprite[] backImg;	// 0 = normal background, 1 = pressed background
	private boolean state; 		// false = released, 	  true = pressed
	public Rectangle bound;
	
	public UIButton(String text, BitmapFont font, Rectangle bound, Sprite normal, Sprite pressed){
		this.text = text;
		this.font = font;
		this.backImg = new Sprite[2];
		backImg[0] = normal;
		backImg[1] = pressed;
		this.bound = bound;
	}
	
	public void render(SpriteBatch batch){
		// Convert boolean to int
		int s = (state) ? 1 : 0;
		// Checks if sprite exists
		if(backImg[s] != null){
		// Draws background sprite
		backImg[s].setBounds(bound.x, bound.y, bound.width, bound.height);
		backImg[s].draw(batch);
		}
		// Draws text
		font.draw(batch, text, bound.x, bound.y);
	}
	
	public boolean checkBound(Vector2 point){
		return bound.contains(point);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBackground(Sprite backImg) {
		this.backImg[0] = backImg;
	}
	
	public void setPressedBackground(Sprite backImg) {
		this.backImg[1] = backImg;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public void setPos(float x, float y){
		bound.x = x;
		bound.y = y;
	}
	
	public void setSize(float w, float h){
		bound.width = w;
		bound.height = h;
	}
	
	public void setBound(Rectangle rect){
		bound = rect;
	}
	
	public Rectangle getBound(){
		return bound;
	}
	
}
