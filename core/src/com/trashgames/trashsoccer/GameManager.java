package com.trashgames.trashsoccer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.trashgames.trashsoccer.input.InputManager;
import com.trashgames.trashsoccer.screen.MenuScreen;
import com.trashgames.trashsoccer.screen.PlayScreen;
import com.trashgames.trashsoccer.screen.ScreenManager;



public class GameManager extends ApplicationAdapter {
    // Static variables
    public static String WND_TITLE = "Platformer game";
    public static int WND_WIDTH = 640;
    public static int WND_HEIGHT = 480;



    // Class memebers
    protected ScreenManager sm;
    public InputManager im;

    @Override
    public void create () {
        sm = new ScreenManager(this);
        sm.push(new PlayScreen(this));
        sm.push(new MenuScreen(this));

        im = new InputManager();
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void render () {
        sm.renderCurrent(Gdx.graphics.getDeltaTime());
        im.update();
    }

    @Override
    public void dispose() {
        super.dispose();
        sm.dispose();
    }
}
