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
		// TODO Auto-generated method stub
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		//sb.setTransformMatrix(camera.combined);
		sb.begin();
		//sb.draw(img, gm.im.mousePos().x, GameManager.WND_HEIGHT - gm.im.mousePos().y);
		sprite.draw(sb);
		sprite2.draw(sb);
//		sprite3.draw(sb);
		sb.end();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		camera.update();
		x+=20*delta;
//		sprite.setRotation(x);
		sprite.setScale(0.5f+1.5f*(float) (Math.abs(Math.cos(x/10))));
//		sprite2.setRotation(-x);
		sprite2.setScale(0.5f+1.5f*(float) (Math.abs(Math.sin(x/10))));
		sprite2.setPosition(250+50*(float)Math.sin(x/5), 250+50*(float)Math.cos(x/5));
//		sprite3.setScale((float) (Math.abs(Math.sin(x/10+(Math.PI/4)))));
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		sb = new SpriteBatch();
		img = new Texture(Gdx.files.internal("paolo-brosio.jpg"));
		sprite = new Sprite(img);
		sprite.setPosition(50, 50);
		sprite.setSize(200, 200);
		sprite.setOriginCenter();
		img2 = new Texture(Gdx.files.internal("image.jpg"));
		sprite2 = new Sprite(img2);
		sprite2.setPosition(250, 250);
		sprite2.setSize(200, 200);
		sprite2.setOriginCenter();
//		sprite3 = new Sprite(img);
//		sprite3.setPosition(50+(img.getWidth()/2), 50);
//		sprite3.setSize(200, 200);
		
		Gdx.gl.glClearColor(0.8f, 0f, 0f, 1f);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		img.dispose();
		img2.dispose();
	}
	

}
