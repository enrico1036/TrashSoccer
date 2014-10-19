package com.trashgames.trashsoccer.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class UIObject {

	protected String text;
	protected Rectangle bound;
	protected BitmapFont font;
	
	public UIObject(){}
	public abstract void render(SpriteBatch batch);
	public abstract String getText();
	public abstract void setText(String string);
	public abstract void setFont(BitmapFont font);
	public abstract BitmapFont getFont();
	public abstract void setBound(Rectangle bound);
}
