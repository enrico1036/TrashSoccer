package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trashgames.trashsoccer.Game;

public abstract class GameScreen extends ScreenAdapter implements InputProcessor{

    protected Game gm;
    protected SpriteBatch sb;
    protected OrthographicCamera camera;

    public GameScreen(Game gm){
        this.gm = gm;
		Gdx.input.setInputProcessor(this);
    }

    public Game getGm() {
        return gm;
    }


    @Override
    public void render(float delta) {
        update(delta);
    }

    public void update(float delta){
    	
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    	
    }

    @Override
    public void pause() {
    	
    }

    @Override
	public void resume() {
    	Gdx.input.setInputProcessor(this);
	}

    @Override
    public void dispose() {

    }
    
	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

}
