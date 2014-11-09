package com.trashgames.trashsoccer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.trashgames.trashsoccer.entities.Entity;
import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.entities.Score;

public class MyContactListener implements ContactListener {

	private Score scoreL;
	private Score scoreR;
	
	public void setScores(Score scoreL, Score scoreR){
		this.scoreL = scoreL;
		this.scoreR = scoreR;
	}
	
	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		// Sum both fa and fb to avoid double check (fb -> fa, fa -> fb)
		int filterSum = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;
		// Switch between possible contacts
		switch(filterSum){
		// Increase score when the Ball touches Goal sensor
		case B2DFilter.BALL | B2DFilter.SENSORL:
			scoreL.addGoal();
			break;
		case B2DFilter.BALL | B2DFilter.SENSORR:
			scoreR.addGoal();
			break;
		// 	The Player can jump only when it is on the ground
		case B2DFilter.FOOT_SENSOR | B2DFilter.TERRAIN:
			Entity ea = (Entity) fa.getUserData();
			Entity eb = (Entity) fb.getUserData();
			Player player = null;
			if(ea != null){
				player = (Player) ea;
				player.setJump(true);
			}else if(eb != null){
				player = (Player) eb;
				player.setJump(true);
			}
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void endContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		
		// Sum both Fixture.categoryBits to avoid double check (fb -> fa, fa -> fb)
		int filterSum = fa.getFilterData().categoryBits | fb.getFilterData().categoryBits;
		// Switch between possible contacts
		switch(filterSum){
		// The Player can jump only when in contact with the ground
		case B2DFilter.FOOT_SENSOR | B2DFilter.TERRAIN:
			Entity ea = (Entity) fa.getUserData();
			Entity eb = (Entity) fb.getUserData();
			Player player = null;
			if(ea != null){
				player = (Player) ea;
				player.setJump(false);
			}else if(eb != null){
				player = (Player) eb;
				player.setJump(false);
			}
			break;
			
		default:
			break;
		}
	}
	

	@Override
	public void postSolve(Contact c, ContactImpulse imp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold man) {
		// TODO Auto-generated method stub
	}

}
