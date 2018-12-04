package com.engine.simulation;

import com.engine.Display;
import com.engine.rigidbody.Polygon;
import com.engine.rigidbody.RigidBody;

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
		int nRigidBodies = manager.getNumOfRigidBodies();
		RigidBody rigidBody;
		for(int i = 0; i < nRigidBodies; i++) {
			rigidBody = manager.getRigidBody(i);
			rigidBody.vel().add(rigidBody.accX() * unitT, rigidBody.accY() * unitT);
		}
	}

	private synchronized void nextMove() {
		int nRigidBodies = manager.getNumOfRigidBodies();
		RigidBody rigidBody;
		for(int i = 0; i < nRigidBodies; i++) {
			rigidBody = manager.getRigidBody(i);
			rigidBody.pos().add(rigidBody.velX() * unitT, rigidBody.velY() * unitT);
			rigidBody.setTheta((rigidBody.theta() + rigidBody.angular() * unitT) % (2 * Math.PI));

			for(int j=i+1; j<nRigidBodies; j++) {
				RigidBody other = manager.getRigidBody(j);

			}
		}
	}

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