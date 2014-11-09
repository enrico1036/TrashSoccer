package com.trashgames.trashsoccer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.entities.Score;

public class MyContactListener implements ContactListener {

	Player player;
	Score scoreL;
	Score scoreR;
	public void setScores(Score scoreL, Score scoreR){
		this.scoreL = scoreL;
		this.scoreR = scoreR;
	}
	@Override
	public void beginContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
		Fixture fb = arg0.getFixtureB();
		
		// Trying to detect goal
		if((fa.getFilterData().categoryBits == B2DFilter.SENSORL && fb.getFilterData().categoryBits == B2DFilter.BALL) || (fa.getFilterData().categoryBits == B2DFilter.BALL && fb.getFilterData().categoryBits == B2DFilter.SENSORL))
			scoreL.addGoal();
		else if((fa.getFilterData().categoryBits == B2DFilter.SENSORR && fb.getFilterData().categoryBits == B2DFilter.BALL) || (fa.getFilterData().categoryBits == B2DFilter.BALL && fb.getFilterData().categoryBits == B2DFilter.SENSORR))
			scoreR.addGoal();
		else if(fa.getUserData() != null)
		{
			player = (Player)(fa.getUserData());
			player.setJump(true);
		}else if(fb.getUserData() != null)
		{
			player = (Player)(fb.getUserData());
			player.setJump(true);
		}

	}

	@Override
	public void endContact(Contact arg0) {
		Fixture fa = arg0.getFixtureA();
		Fixture fb = arg0.getFixtureB();
		if(fa.getUserData() != null && !fa.getUserData().equals("ball"))
		{
			player = (Player)(fa.getUserData());
			player.setJump(false);
		}else if(fb.getUserData() != null && !fb.getUserData().equals("ball"))
		{
			player = (Player)(fb.getUserData());
			player.setJump(false);
		}
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
