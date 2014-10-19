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
import com.trashgames.trashsoccer.ui.UILabel;

public class MenuScreen extends GameScreen {

<<<<<<< HEAD
	//Background Image
	private Texture bg;
	private Sprite spriteBg;
	private SpriteBatch sb;
=======
	private float x=0;
	UIButton button;
	UILabel label;
>>>>>>> refs/remotes/origin/matcap_branch
	
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
<<<<<<< HEAD
		
		//Draws all the images
		sb.begin();
		spriteBg.draw(sb);
=======
		sb.begin();
		button.render(sb);
		label.render(sb);
>>>>>>> refs/remotes/origin/matcap_branch
		sb.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();
		
<<<<<<< HEAD
=======
		if(button.checkBound(gm.im.mousePos()))
			button.setPressed(true);
		else
			button.setPressed(false);
		
		
	}
	@Override
	public void show() {
		super.show();
		sb = new SpriteBatch();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
>>>>>>> refs/remotes/origin/matcap_branch
		
<<<<<<< HEAD
=======
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		Texture t1 = new Texture(Gdx.files.internal("image.jpg"));
		Texture t2 = new Texture(Gdx.files.internal("paolo-brosio.jpg"));
		
		button = new UIButton("CIAO", font, new Rectangle(200, 200, 100, 100), new Sprite(t1), new Sprite(t2));
		label = new UILabel("TEST LABEL", new Rectangle(0, 0, 100, 100), font);
		
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
>>>>>>> refs/remotes/origin/matcap_branch
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	

}
