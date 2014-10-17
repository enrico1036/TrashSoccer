package com.trashgames.trashsoccer.input;


import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputManager implements InputProcessor {

    // Key arrays
    protected boolean[][] keyboard;
    protected boolean[][] mouse;
    protected Vector2 mouseCoord;

    public InputManager(){
        keyboard = new boolean[256][2];
        mouse = new boolean[3][2];
        mouseCoord = new Vector2(0,0);
    }

    public void update(){
        for(int i=0; i<keyboard.length; i++)
            keyboard[i][0] = keyboard[i][1];

    }

    public boolean isKeyDown(int keycode){
        return keyboard[keycode][1];
    }

    public boolean isKeyPressed(int keycode) {
        return (!keyboard[keycode][0] && keyboard[keycode][1]);
    }

    public boolean isKeyReleased(int keycode) {
        return (keyboard[keycode][0] && !keyboard[keycode][1]);
    }

    public Vector2 mousePos(){
        return mouseCoord;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyboard[keycode][1] = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyboard[keycode][1] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseCoord.set(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
