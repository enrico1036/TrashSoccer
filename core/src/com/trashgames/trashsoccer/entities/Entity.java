package com.trashgames.trashsoccer.entities;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.trashgames.trashsoccer.Dimension;

public abstract class Entity {
	
	protected World world;
	protected Rectangle bounds;
	protected Filter filter;
	protected Body[] bodies;
	protected Sprite[] sprites;
	protected Dimension[] dims;
	
	protected abstract void createBodies();
	
	protected abstract void setSprites();
	
	public void regenerateBodies(){
		// destroy first
		destroy();
		setSprites();
		createBodies();
	}

	public Body[] getBodies() {
		return bodies;
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public  void update(float delta){
		// Update sprites position
		for (int i = 0; i < sprites.length && i < bodies.length; i++) {
			if (bodies[i] != null && sprites[i] != null) {
				sprites[i].setPosition(bodies[i].getPosition().x - dims[i].width, bodies[i].getPosition().y - dims[i].height);
				sprites[i].setSize(dims[i].width * 2, dims[i].height * 2);
				sprites[i].setOriginCenter();
				sprites[i].setRotation(bodies[i].getAngle() * MathUtils.radiansToDegrees);
			}
		}
	}

	public void render(SpriteBatch batch){
		for (int i = 0; i < sprites.length  && i < bodies.length; i++) {
			if (sprites[i] != null && bodies[i] != null) {
				sprites[i].draw(batch);
			}
		}
	}
	
	public void setFilter(Filter filter){
		this.filter = new Filter();
		this.filter.categoryBits = filter.categoryBits;
		this.filter.groupIndex = filter.groupIndex;
		this.filter.maskBits = filter.maskBits;
	}

	public void destroy() {
		// Iterate for all bodies
		for (Body body : bodies) {
			if (body != null) {
				body.setUserData(null);
				// Iterate for all fixtures
				for(Fixture fixture : body.getFixtureList()){
					fixture.setUserData(null);
					body.destroyFixture(fixture);
				}
				for(JointEdge joint : body.getJointList()){
					joint.joint.setUserData(null);
					world.destroyJoint(joint.joint);
				}
				world.destroyBody(body);
			}
		}
	}
}
