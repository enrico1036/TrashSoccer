package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trashgames.trashsoccer.GameManager;

public class MenuScreen extends GameScreen {

	//Background Image
	private Texture bg;
	private Sprite spriteBg;
	private SpriteBatch sb;
	
	public MenuScreen(GameManager gm) {
		super(gm);
		//Sets the camera view
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		//Load bacground image
		bg = new Texture("MenuBackground.jpg");
		spriteBg = new Sprite(bg);
		spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sb = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		//Clear buffer bit
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		//Draws all the images
		sb.begin();
		spriteBg.draw(sb);
		sb.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();
		
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	

}
