package com.engine.simulation;

public interface Config {
	public static int DIMENSION = 2;
	public static float GRAVITY = 10.0f;

	public static float RESTITUTION_COEFF = 0.9f;
	public static float RESTITUTION_COEFF_ANGULAR = 0.5f;

	public static float FRICTION_COEFF = 0.999f;

	public static final int DP_WIDTH  = 800;
	public static final int DP_HEIGHT = 550;

	public static final int DP_RIGHT = DP_WIDTH-16;
	public static final int DP_BOTTOM = DP_HEIGHT-40;

	public static final String TITLE = "Engine  v.0.2";

	public static int TYPE_NONE = 0;
	public static int TYPE_CIRCLE = 1;
	public static int TYPE_POLYGON = 2;
}