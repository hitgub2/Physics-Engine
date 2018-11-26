package com.engine.wall;

import java.awt.Shape;

import com.engine.simulation.Vec2d;
import com.engine.thing.Thing;

public abstract class Wall {
	public final static int TYPE_NONE = 0;
	public final static int TYPE_VERTICAL = 1;
	public final static int TYPE_HORIZONTAL = 2;
	public final static int TYPE_DEPENDENT_OF_X = 3;
	public final static int TYPE_DEPENDENT_OF_Y = 4;
	
	private String name;
	protected int type;
	protected Wall(String name) {
		this.name = name;
		this.type = TYPE_NONE;
	}
	public void set(Wall other) {
		this.name = other.name;
		this.type = other.type;
	}
	public String getName() {
		return this.name;
	}
	public int getType() {
		return this.type;
	}
	//Shape
	abstract public Shape drawShape();
	
	
	public abstract boolean isToward(Vec2d pos, Vec2d vel);	//������ ���ϰ� �ִ��� �ݺ�
	public abstract float distance(Vec2d pos);
	public abstract void bounce(Thing thing, float charLength);
}