package com.engine.thing;

import java.awt.Polygon;

import com.engine.simulation.Vec2d;
import com.engine.wall.Wall;

public class Triangle extends Thing {
	
	public Triangle(String name, Vec2d p1, Vec2d p2, Vec2d p3, Vec2d vel, Vec2d acc, float mass, float angular) {
		super(name, Vec2d.center(p1, p2, p3), vel, acc, mass, angular);
	}

	@Override
	protected float charLength(Thing other) {
		return 0;
	}

	@Override
	protected float charLength(Wall wall) {
		return 0;
	}

	private Polygon shape;
	@Override
	public Polygon fillShape() {
		// TODO Auto-generated method stub
		return shape;
	}

}
