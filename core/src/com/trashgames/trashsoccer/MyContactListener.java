package com.trashgames.trashsoccer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {

	boolean playerOnGround = true;
	@Override
	public void beginContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
		Fixture fb = arg0.getFixtureB();
		
		if((fa.getUserData() != null && fa.getUserData().equals("foot")) || (fb.getUserData() != null && fb.getUserData().equals("foot")))
		{
			playerOnGround = true;
		}

	}

	@Override
	public void endContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
		Fixture fb = arg0.getFixtureB();
		if((fa.getUserData() != null && fa.getUserData().equals("foot")) || (fb.getUserData() != null && fb.getUserData().equals("foot")))
		{
			playerOnGround = false;
		}
	}
	
	public boolean isPlayerOnGround(){ 
		return playerOnGround; 
		}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
	}

}
