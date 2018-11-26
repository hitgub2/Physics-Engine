package com.engine.simulation;

public interface Config {
	public static int DIMENSION = 2;					//2D
	public static int CONSIDERATION = 3;				//3:���ӵ����� ����
	public static float GRAVITY = 30.0f;				//�߷°��ӵ�
	
	public static float RESTITUTION_COEFF_THING = 0.9f;				//��ü�ݹ߰��
	public static float RESTITUTION_COEFF_THING_ANGULAR = 0.5f;	//��ü�ݹ߰��(��)
	public static float RESTITUTION_COEFF_WALL = 0.6f;				//���ݹ߰��
	public static float RESTITUTION_COEFF_WALL_ANGULAR = 0.5f;		//���ݹ߰��(��)
	
	public static float FRICTION_COEFF = 0.999f;		//�ٴڸ鸶�����
//	public static float delT = 0.1f;					//�ð�����(delta t)
//	public static float STOP_POSITION = 0.1f;			//�ּ� ���� ����(position)
//	public static float STOP_VELOCITY = 0.1f;			//�ּ� ���� ����(velocity)
	
	public static final int DP_WIDTH  = 800; // �������� ũ��(���� �ʿ�)
	public static final int DP_HEIGHT = 550;
	
	public static final int DP_RIGHT = DP_WIDTH-16;	//ȯ�濡 ���� �ٸ� �� ����
	public static final int DP_BOTTOM = DP_HEIGHT-40;
	
	public static final String TITLE = "Engine  v.0.2";
}