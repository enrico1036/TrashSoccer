package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.trashgames.trashsoccer.GameManager;
import com.trashgames.trashsoccer.ui.UIButton;

public class MenuScreen extends GameScreen {

	private float x=0;
	UIButton button;
	public MenuScreen(GameManager gm) {
		super(gm);
		show();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		sb.begin();
		button.render(sb);
		sb.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();
		
		if(button.checkBound(gm.im.mousePos()))
			button.font.setColor(Color.GREEN);
		else
			button.font.setColor(Color.RED);
		
		
	}
	@Override
	public void show() {
		super.show();
		sb = new SpriteBatch();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		button = new UIButton("CIAO", font, new Rectangle(200, 200, 100, 100), null, null);
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	

}
