package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.Game;
import com.trashgames.trashsoccer.ui.UIButton;
import com.trashgames.trashsoccer.ui.UILabel;

public class MultiPlayerScreen extends SinglePlayerScreen{

	protected UIButton kickButtonR;
	protected UIButton jumpButtonR;
	
	public MultiPlayerScreen(Game gm) {
		super(gm);
	}
	
	@Override
	protected void createBottomUI() {
		leftPlayerUI();
		rightPlayerUI();
	}
	
	protected void leftPlayerUI(){
		Rectangle bound = new Rectangle(10, 10, Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
		// Kick button
		kickButtonL = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_KICK_BLUE_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_KICK_BLUE_DOWN, Texture.class));
		kickButtonL.setAction(new Runnable() {
			@Override
			public void run() {
				playersL[0].toggleKick();
				playersL[1].toggleKick();
			}
		});
		
		// Jump button
		bound.setPosition(20 + bound.width, bound.y);
		jumpButtonL = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_JUMP_BLUE_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_JUMP_BLUE_DOWN, Texture.class));
		jumpButtonL.setAction(new Runnable() {
			@Override
			public void run() {
				playersL[0].jump();
				playersL[1].jump();
			}
		});
	}
	
	protected void rightPlayerUI(){
		Rectangle bound = new Rectangle(0, 10, Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
		
		
		// Jump button
		bound.setPosition(Gdx.graphics.getWidth() - 10 - bound.width, bound.y);
		jumpButtonR = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_JUMP_RED_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_JUMP_RED_DOWN, Texture.class));
		jumpButtonR.setAction(new Runnable() {
			@Override
			public void run() {
				playersR[0].jump();
				playersR[1].jump();
			}
		});
		
		// Kick button
		bound.setPosition(bound.x - 10 - bound.width, bound.y);
		kickButtonR = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_KICK_RED_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_KICK_RED_DOWN, Texture.class));
		kickButtonR.setAction(new Runnable() {
			@Override
			public void run() {
				playersR[0].toggleKick();
				playersR[1].toggleKick();
			}
		});
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		sb.begin();
		jumpButtonR.render(sb);
		kickButtonR.render(sb);
		sb.end();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(kickButtonR.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			kickButtonR.setPressed(true);
			kickButtonR.execAction();
		}
		
		if(jumpButtonR.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			jumpButtonR.setPressed(true);
			jumpButtonR.execAction();
		}
		return super.touchDown(screenX, screenY, pointer, button);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(kickButtonR.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			kickButtonR.execAction();
			kickButtonR.setPressed(false);
		}
		jumpButtonR.setPressed(false);		
		return super.touchUp(screenX, screenY, pointer, button);
	}

}
