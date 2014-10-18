package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trashgames.trashsoccer.GameManager;

public class MenuScreen extends GameScreen {

	private Texture img;
	private Texture img2;
	private Sprite sprite;
	private Sprite sprite2;
	private Sprite sprite3;
	private float x=0;
	public MenuScreen(GameManager gm) {
		super(gm);
		show();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();
		
	}
	@Override
	public void show() {
		super.show();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	

}
