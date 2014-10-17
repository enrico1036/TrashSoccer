package com.trashgames.trashsoccer.screen;

import java.util.EmptyStackException;
import java.util.Stack;

import com.trashgames.trashsoccer.GameManager;


public class ScreenManager {

    protected  GameManager gm;
    protected Stack<GameScreen> screenStack;

    public ScreenManager(GameManager gm){
        this.gm = gm;
        screenStack = new Stack<GameScreen>();
    }

    public GameManager getGameManager(){
        return gm;
    }

    public void push(GameScreen screen){
        screenStack.push(screen);
    }

    public GameScreen pop(){
        GameScreen ret;
        try{
            ret = screenStack.pop();
        } catch(EmptyStackException e){
            return null;
        }
        ret.dispose();
        return ret;
    }

    public GameScreen current(){
        try{
            return screenStack.peek();
        } catch(EmptyStackException e){ }
        return null;
    }

    public boolean renderCurrent(float delta){
        try{
            screenStack.peek().render(delta);
        } catch(Exception e){
            e.printStackTrace();
            //Gdx.app.exit();
            return false;
        }
        return true;
    }

    public void dispose() {
        for(GameScreen scr : screenStack)
            scr.dispose();
        screenStack.clear();
    }






}
