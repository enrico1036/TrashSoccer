package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.trashgames.trashsoccer.GameManager;


public class PlayScreen extends GameScreen{
    

    public PlayScreen(GameManager gm){
        super(gm);

        // OpenGL initialization
        Gdx.gl.glClearColor(.3f, .0f, .25f,1f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
