package com.engine.simulation;

import com.engine.Display;

public class EngineThread extends Thread {
	private long tLapse = 0;
	private long curT = 0;
	private long lastT = 0;
	private float unitT = 0.0f;
	private Manager manager;
	
	public EngineThread() {
		this.manager = Manager.getInstance();
	}

	public void run() {
		curT = System.currentTimeMillis();
		while(Display.running) {
			updateTime();
			accelerate();
			nextMove();
		}
	}

	private void updateTime() {
		lastT = curT;
		curT = System.currentTimeMillis();
		tLapse = (curT - lastT);
		unitT = (tLapse / 300.0f);
	}

	private synchronized void accelerate() {
		/*
		int size = manager.getNumberOfThings();
		for(int i = 0; i < size; i++) {
			Thing t = manager.getThing(i);
			Vec2d oldVel = t.vel();			
			Vec2d newVel = new Vec2d(	oldVel.getX() + t.accX() * unitT,
										oldVel.getY() + t.accY() * unitT);
			t.setVel(newVel);
		}*/
	}	
	
	// �ӵ��� ���� ��ü �̵�
	private synchronized void nextMove() {
		/*
		int numOfThings = manager.getNumberOfThings();
		int numOfWalls = manager.getNumberOfWalls();
		Thing thing;
		for(int i = 0; i < numOfThings; i++) {
			thing = manager.getThing(i);
			Vec2d oldPos = thing.pos();
			Vec2d newPos = new Vec2d(	oldPos.getX() + thing.velX() * unitT,
										oldPos.getY() + thing.velY() * unitT);
			thing.setPos(newPos);
			thing.setTheta((float)((thing.theta() + thing.angular() * unitT) % 6.2831855f)); //	6.2831855 = 2��
			
			for(int j=0; j<numOfWalls; j++)
				thing.bounce(manager.getWall(j));
			for(int j=i+1; j<numOfThings; j++)
				thing.collide(manager.getThing(j));
		}*/
	}
	
//	// ���� �浹�˻�
//	private synchronized void isWallCollision(Thing t) {
//		float maxX = Config.DP_WIDTH;
//		float maxY = Config.DP_HEIGHT-30; // 30 size of bar
//		if(t.posX() > maxX) {
//			t.setPosX(maxX);
//			t.setVelX(t.velX() * (- Config.RESTITUTION_COEFF));
//		}
//		if(t.posY() > maxY) {
//			t.setPosY(maxY);
//			t.setVelY(t.velY() * (- Config.RESTITUTION_COEFF));
//		}
//		if(t.posX() < 1) {
//			t.setPosX(1);
//			t.setVelX(t.velX() * Config.RESTITUTION_COEFF);
//		}
//	}
}