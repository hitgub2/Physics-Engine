package com.engine.wall;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import com.engine.simulation.Calculator;
import com.engine.simulation.Config;
import com.engine.simulation.Vec2d;
import com.engine.thing.Thing;

public class HorizontalWall extends Wall implements Cloneable {
	protected float y1;
	protected HorizontalWall(String name) { super(name); }
	public HorizontalWall(String name, float y1) {
		super(name);
		this.type = TYPE_HORIZONTAL;
		this.y1 = y1;
		shape = new Line2D.Float(0f, y1, (float)Config.DP_RIGHT, y1);
	}
	public void set(HorizontalWall other) {
		super.set(other);
		this.y1 = other.y1;
	}
	@Override
	public Object clone() {
		return super.clone();
	}
	
	@Override
	public boolean isToward(Vec2d pos, Vec2d vel) {
		if(pos.getY()>=y1 && vel.getY()<0d)
			return true;
		if(pos.getY()<=y1 && vel.getY()>0d)
			return true;
		return false;
	}
	
	@Override
	public float distance(Vec2d pos) {
		return Calculator.abs(pos.getY() - y1);
	}

	@Override
	public void bounce(Thing thing, float charLength) {
		Vec2d pos = thing.pos();
		Vec2d vel = thing.vel();
		if(pos.getY()>=y1)
			pos.setY(y1 + charLength);
		else
			pos.setY(y1 - charLength);
		vel.setY(vel.getY() * (-Config.RESTITUTION_COEFF_WALL));
		vel.setX(vel.getX() * Config.FRICTION_COEFF);
		thing.setAngular(thing.angular() * -Calculator.vFunction(thing.theta()));
	}

	protected Line2D.Float shape;
	@Override
	public Shape drawShape() {
		return shape;
	}
}

class DependentOfY extends HorizontalWall {
	protected String function;
	protected DependentOfY(String name) { super(name); }
	public DependentOfY(String name, String function) {
		super(name);
		this.type = TYPE_DEPENDENT_OF_Y;
		this.function = function;
	}
}