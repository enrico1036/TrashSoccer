package com.trashgames.trashsoccer.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class Entity {
	protected World world;
	protected Body[] bodies;
	protected Sprite[] sprites;

	public Body[] getBodies() {
		return bodies;
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	public abstract void update(float delta);

	public abstract void render();

	public void destroy() {
		for (Body body : bodies) {
			if (body != null) {
				body.setUserData(null);
				world.destroyBody(body);
			}
		}
	}
}
