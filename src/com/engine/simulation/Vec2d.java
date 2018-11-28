package com.engine.simulation;

public class Vec2d implements Cloneable {
	private float x;
	private float y;
	
	public Vec2d() {
		x = 0.0f;
		y = 0.0f;
	}
	
	public Vec2d(float x, float y) {
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
	public float getX() { return x; }
	public float getY() { return y; }
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	
	
	//������ ���� ���� �ǵ帮�� �ʴ� ����
	public Vec2d sum(Vec2d v1) {
		return new Vec2d(this.x + v1.x, this.y + v1.y);
	}
	public Vec2d sub(Vec2d v1) {
		return new Vec2d(this.x - v1.x, this.y - v1.y);
	}
	public float sqr() {
		return x*x + y*y;
	}
	public float norm() {
		return (float)Math.sqrt((double)sqr());
	}
	public Vec2d unit() {
		float norm = norm();
		return unit(norm);
	}
	public Vec2d unit(float norm) {
		return new Vec2d(x / norm, y / norm);
	}
	public static Vec2d center(Vec2d... v) {
		int size = v.length;
		Vec2d center = new Vec2d(0f, 0f);
		for(int i=0; i<size; i++) {
			center.x += v[i].x;
			center.y += v[i].y;
		}
		center.x /= size;
		center.y /= size;
		return center;
	}
	
	//������ ���� ���� �ǵ帮�� ���� (void�� ��Ģ���� ��)
	public void multiply(float k) {
		this.x *= k;
		this.y *= k;
	}
	public void add(Vec2d v1) {
		this.x += v1.x;
		this.y += v1.y;
	}
	
	//get & set each component
	public float getComponent(int itr) {
		switch(itr) {
		case 0 : return this.x;
		case 1 : return this.y;
//		case 2 : return this.z;
		default : return -1;
		}
	}
	public void setComponent(int itr, float val) {
		switch(itr) {
		case 0 : this.x = val; break;
		case 1 : this.y = val; break;
//		case 2 : return this.z;
		default : return;
		}
	}
	
	public void print() {
		System.out.printf("%f %f\n",x, y);
	}


	public static float abs(float x) {
		return x>=0 ? x : -x;
	}

	public static float vFunction(float theta) {
		if(theta<0 || theta>3.1415927f)
			theta %= 3.1415927f;

		if(theta==0 || theta==3.1415927f)
			return 0f;
		else if(theta < 1.5707964f)
			return (float)(Math.cos(theta));
		else
			return (float)(Math.cos(3.1415927f-theta));
	}
	
	
}