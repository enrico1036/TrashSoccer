package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.trashgames.trashsoccer.Game;
import com.trashgames.trashsoccer.ui.UIButton;

public class PauseScreen extends GameScreen{
	private UIButton toMenuButton;
	private UIButton resumeButton;
	private UIButton pauseButton;
	public PauseScreen(Game gm) {
		super(gm);
		
		gm.assetManager.load("/data/ui/to_menu_up.png", Texture.class);
		gm.assetManager.load("/data/ui/to_menu_down.png", Texture.class);
		gm.assetManager.load("/data/ui/resume_up.png", Texture.class);
		gm.assetManager.load("/data/ui/resume_down.png", Texture.class);
		gm.assetManager.finishLoading();
		
		Rectangle bounds = new Rectangle(50,50, 200, 50);
		toMenuButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get("/data/ui/to_menu_up.png", Texture.class), 
				gm.assetManager.get("/data/ui/to_menu_down.png", Texture.class));
		
		bounds.setPosition(50, 150);
		resumeButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get("/data/ui/resume_up.png", Texture.class), 
				gm.assetManager.get("/data/ui/resume_down.png", Texture.class));
		
		bounds.set(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60, 50, 50);
		pauseButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get("/data/ui/pause_down.png", Texture.class), 
				gm.assetManager.get("/data/ui/pause_up.png", Texture.class));
		
		sb = new SpriteBatch();
		
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 0.5f);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		sb.setTransformMatrix(uiCamera.combined);
		sb.begin();		
			toMenuButton.render(sb);
			resumeButton.render(sb);
			pauseButton.render(sb);
		sb.end();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		uiCamera.update();
	}
	
	
	
	
	
}
