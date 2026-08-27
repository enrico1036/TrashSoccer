package com.trashgames.trashsoccer.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UILabel{
	private String text;
	private Rectangle bound;
	private BitmapFont font;
	
	public UILabel(String text, Rectangle bound, BitmapFont font){
		this.text = text;
		this.bound = bound;
		this.font = font;
	}
	
	public void render(SpriteBatch batch){
		if(text != null && !text.isEmpty()){
			GlyphLayout glyphLayout = new GlyphLayout(font, text);
			font.getData().scaleX = 1.5f;
			font.getData().scaleY = 1.5f;
			font.draw(batch, text, bound.x + (bound.width - glyphLayout.width) / 2, bound.y + (bound.height - glyphLayout.height) / 2);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
}
