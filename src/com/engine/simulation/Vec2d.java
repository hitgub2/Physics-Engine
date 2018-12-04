package com.engine.simulation;

public class Vec2d implements Cloneable {
	private double x;
	private double y;

	public Vec2d() {
		x = 0.0;
		y = 0.0;
	}

	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch(CloneNotSupportedException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	//getter and setter
	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void set(Vec2d v) { this.x = v.x; this.y = v.y; }

	//create new instance
	public Vec2d sum(Vec2d v1) {
		return new Vec2d(this.x + v1.x, this.y + v1.y);
	}
	public Vec2d sub(Vec2d v1) {
		return new Vec2d(this.x - v1.x, this.y - v1.y);
	}
	public double sqr() {
		return x*x + y*y;
	}
	public double norm() {
		return Math.sqrt(sqr());
	}
	public Vec2d unit() {
		double norm = norm();
		return unit(norm);
	}
	public Vec2d unit(double norm) {
		return new Vec2d(x / norm, y / norm);
	}
	public static Vec2d center(int[] x, int[] y) {
		return center(x, y, x.length);
	}
	public static Vec2d center(int[] x, int[] y, int len) {
		Vec2d center = new Vec2d(0, 0);
		for(int i=0; i<len; i++) {
			center.x += x[i];
			center.y += y[i];
		}
		center.x /= len;
		center.y /= len;
		return center;
	}
	public Vec2d negate() { return new Vec2d(-this.x, -this.y); }
	public double dot(Vec2d other) { return this.x*other.x+this.y*other.y; }
	public Vec2d mul(double k) { return new Vec2d(this.x*k, this.y*k); }
	public double distance(Vec2d other) { return Math.sqrt( (this.x-other.x)*(this.x-other.x) + (this.y-other.y)*(this.y-other.y) ); }

	public double cross(Vec2d other) { return this.x*other.y - y*other.x; }
	public Vec2d cross_f(double s) { return new Vec2d(s+this.y, -s*this.x); }
	public Vec2d cross_s(double s) { return new Vec2d(-s+this.y, s*this.x); }



	//modify this instance
	//public void mul(double k) {
	//	this.x *= k;
	//	this.y *= k;
	//}
	public void add(Vec2d v1) {
		this.x += v1.x;
		this.y += v1.y;
	}
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}

	//get & set each component
	public double getComponent(int itr) {
		switch(itr) {
			case 0 : return this.x;
			case 1 : return this.y;
			default : return -1;
		}
	}

	public void setComponent(int itr, double val) {
		switch(itr) {
			case 0 : this.x = val; break;
			case 1 : this.y = val; break;
			default : return;
		}
	}

	public void print() {
		System.out.printf("%f %f\n",x, y);
	}

	public static double abs(double x) {
		return x>=0 ? x : -x;
	}

	public static double vFunction(double theta) {
		if(theta<0 || theta>Math.PI)
			theta %= Math.PI;

		if(theta==0 || theta==Math.PI)
			return 0;
		else if(theta < Math.PI / 2)
			return Math.cos(theta);
		else
			return -Math.cos(theta);
	}

}