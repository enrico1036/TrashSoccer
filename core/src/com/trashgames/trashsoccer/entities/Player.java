package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import static com.trashgames.trashsoccer.Game.PPM;

public class Player {

	private Body body;
	private Body arm1;
	private Body arm2;
	
	private float bodyWidth = Gdx.graphics.getWidth()/30/PPM;
	private float bodyHeight = Gdx.graphics.getHeight()/10/PPM;
	private float headRad = Gdx.graphics.getWidth()/30/PPM;
	private float armWidth = Gdx.graphics.getWidth()/80/PPM;
	private float armHeight = Gdx.graphics.getHeight()/30/PPM;
	
	public Player(World world, Vector2 pos){
		// Body creation
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		body = world.createBody(bdef);
		
		// Human body
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		body.createFixture(fdef);
		
		// Human head
		CircleShape cshape = new CircleShape();
		cshape.setRadius(headRad);
		cshape.setPosition(new Vector2(0, headRad + bodyHeight));
		fdef.shape = cshape;
		body.createFixture(fdef);
		
		// Human Right arm
		arm1 = world.createBody(bdef);
		MassData md = new MassData();
		md.I = 0.1f;
		md.mass = 2f;
		arm1.setMassData(md);
		shape.setAsBox(armWidth, armHeight);
		fdef.shape = shape;
		fdef.restitution = 0.1f;
		arm1.createFixture(fdef);
		
		// Right arm joint
		RevoluteJointDef jdef = new RevoluteJointDef();
		jdef.bodyA = body;
		jdef.bodyB = arm1;
		jdef.localAnchorA.set(Gdx.graphics.getWidth()/30/PPM, Gdx.graphics.getHeight()/20/PPM);
		jdef.localAnchorB.set(-armWidth, armHeight);
		jdef.collideConnected = true;
		jdef.enableLimit = false;
		world.createJoint(jdef);
		
		// Human Left arm
		arm2 = world.createBody(bdef);
		arm2.setMassData(md);
		arm2.createFixture(fdef);
		
		// Left arm joint
		jdef.bodyA = body;
		jdef.bodyB = arm2;
		jdef.localAnchorA.set(-bodyWidth, Gdx.graphics.getHeight()/20/PPM);
		jdef.localAnchorB.set(armWidth, armHeight);
		world.createJoint(jdef);
		
//		body.applyForce(100, 10, pos.x, pos.y, true);
//		arm1.applyAngularImpulse(10, true);
		
	}
	
	public void jump(){
		
	}
	
	public void kick(){
		
	}
}
