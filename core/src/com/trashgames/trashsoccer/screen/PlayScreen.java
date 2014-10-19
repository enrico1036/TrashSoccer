package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.trashgames.trashsoccer.GameManager;
import com.trashgames.trashsoccer.graphics.TextureManager;


public class PlayScreen extends GameScreen{
	private TextureManager tm;
	private Sprite sprite;
    public PlayScreen(GameManager gm){
        super(gm);
        tm = new TextureManager();
        // Texture loading
        tm.loadTexture("MenuBackground.jpg", "BACKGROUND");
        sprite = new Sprite(tm.get("BACKGROUND"));
        sprite.setPosition(0, 0);
        sb = new SpriteBatch();
        
        // OpenGL initialization
        Gdx.gl.glClearColor(.3f, .0f, .25f,1f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
    	
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void dispose() {
        super.dispose();
        tm.dispose();
    }
}
