package com.trashgames.trashsoccer.screen;

import java.util.EmptyStackException;
import java.util.Stack;

import com.trashgames.trashsoccer.Game;

public class ScreenManager {

	protected Game gm;
	protected Stack<GameScreen> screenStack;

	public ScreenManager(Game gm) {
		this.gm = gm;
		screenStack = new Stack<GameScreen>();
	}

	public Game getGameManager() {
		return gm;
	}

	public void push(GameScreen screen) {
		try {
			screenStack.peek().pause();
		} catch (EmptyStackException e) {
			// Do nothing
		}
		screenStack.push(screen);
	}

	public GameScreen pop() {
		try {
			GameScreen ret = screenStack.pop();
			ret.dispose();
			screenStack.peek().resume();
			return ret;
		} catch (EmptyStackException e) {
			return null;
		}
	}

	public GameScreen current() {
		try {
			return screenStack.peek();
		} catch (EmptyStackException e) {
			// Do nothing
		}
		return null;
	}

	public boolean renderCurrent(float delta) {
		try {
			screenStack.peek().render(delta);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void dispose() {
		for (GameScreen scr : screenStack)
			scr.dispose();
		screenStack.clear();
	}

}
