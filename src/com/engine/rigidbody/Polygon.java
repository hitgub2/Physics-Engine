package com.engine.rigidbody;

import java.awt.Graphics2D;

import com.engine.simulation.Vec2d;

public class Polygon extends RigidBody implements Cloneable {
	private int[] xi;      //vertices x location when the center of mass are at (0, 0)
	private int[] yi;      //vertices y location when the center of mass are at (0, 0)
	private int nVertices;

	public Polygon(String name, int[] x, int[] y, Vec2d vel, Vec2d acc, double theta, double angular, double mass) {
		super(name, Vec2d.center(x, y), vel, acc, theta, angular, mass);
		nVertices = x.length;
		inertia = 1d;
		this.x = x;
		this.y = y;
		super.type = TYPE_POLYGON;

		int xp = (int)pos.getX();
		int yp = (int)pos.getY();
		this.xi = new int[nVertices];
		this.yi = new int[nVertices];
		for(int i = 0; i < nVertices; i++) {
			this.xi[i] = x[i] - xp;
			this.yi[i] = y[i] - yp;
		}
	}
	@Override
	public void set(RigidBody other) {
		try {
			Polygon p = (Polygon)other;
			super.set(other);
			this.xi = p.xi;
			this.yi = p.yi;
			this.nVertices = p.nVertices;
		} catch(ClassCastException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object clone() {
		Polygon polygon = (Polygon)super.clone();
		polygon.xi = this.xi.clone();
		polygon.yi = this.yi.clone();
		return polygon;
	}


	private int[] x;	//current verctices x location
	private int[] y;	//current verctices y location
	@Override
	public void draw(Graphics2D g2d) {
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		for(int i=0; i<nVertices; i++) {
			x[i] = (int)pos.getX() + (int)(cos * xi[i] + sin * yi[i]);
			y[i] = (int)pos.getY() + (int)(-sin * xi[i] + cos * yi[i]);
		}
		g2d.fillPolygon(x, y, nVertices);
	}

	public int[] getX() { return this.x; }
	public int[] getY() { return this.y; }
}