package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.trashgames.trashsoccer.GameManager;

public abstract class GameScreen extends ScreenAdapter{

    protected GameManager gm;
    protected SpriteBatch sb;
    protected OrthographicCamera camera;

    public GameScreen(GameManager gm){
        this.gm = gm;
    }

    public GameManager getGm() {
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

    }

    @Override
    public void dispose() {

    }

}
