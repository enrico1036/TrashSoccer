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
import com.trashgames.trashsoccer.ui.UIObject;

public class MenuScreen extends GameScreen {

	//Background Image
	private Texture bg;
	private Sprite spriteBg;
	private SpriteBatch sb;

	//Buttons
	UIButton button;
	UILabel label;
	UIObject objects[];
	
	public MenuScreen(GameManager gm) {
		super(gm);
		//Sets the camera view
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		//Load background image
		bg = new Texture("MenuBackground.jpg");
		spriteBg = new Sprite(bg);
		spriteBg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sb = new SpriteBatch();
		
		//Sets Buttons and labels
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		Texture t1 = new Texture(Gdx.files.internal("image.jpg"));
		Texture t2 = new Texture(Gdx.files.internal("paolo-brosio.jpg"));
		
		objects = new UIObject[2];
		
		objects[0] = new UIButton("CIAO", font, new Rectangle(200, 200, 100, 100), new Sprite(t1), new Sprite(t2));
		objects[1] = new UILabel("TEST LABEL", new Rectangle(0, 0, 100, 100), font);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		//Clear buffer bit
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		//Draws all the images
		sb.begin();
		spriteBg.draw(sb);
		
		objects[0].render(sb);
		objects[1].render(sb);
		sb.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();


		if(button.checkBound(gm.im.mousePos()))
			button.setPressed(true);
		else
			button.setPressed(false);
		
		
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	

}
