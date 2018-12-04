package com.engine.simulation;

import com.engine.Display;
import com.engine.rigidbody.Polygon;
import com.engine.rigidbody.RigidBody;

public class EngineThread extends Thread {
	private long tLapse = 0;
	private long curT = 0;
	private long lastT = 0;
	private double unitT = 0.0;
	private Manager manager;

	public EngineThread() {
		this.manager = Manager.getInstance();
	}

	public void run() {
		curT = System.currentTimeMillis();
		while(Display.running) {
			updateTime();
			//accelerate();
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
            rigidBody.move(rigidBody.vel().mul(unitT));
            rigidBody.setTheta((rigidBody.theta() + rigidBody.angular() * unitT) % (2 * Math.PI));

			for(int j=i+1; j<nRigidBodies; j++) {
				RigidBody other = manager.getRigidBody(j);
                Boolean isCollide = new Gjk().collision(rigidBody, other);
                if(isCollide) {
                    inelastic_collision(rigidBody, other);
                    rigidBody.move(rigidBody.vel().mul(unitT));
                    other.move(other.vel().mul(unitT));
                }
			}
		}
	}


    final float COEF = Config.RESTITUTION_COEFF + 1f;	//(1 + e)


    private void inelastic_collision(RigidBody a, RigidBody b) {
        double m1 = a.mass();
        double m2 = b.mass();
        Vec2d v1 = a.vel();
        Vec2d v2 = b.vel();

        double totalMass = m1 + m2;
        double vi1, vi2, vf1, vf2;

        for(int i = 0; i < Config.DIMENSION; i++) {
            vi1 = v1.getComponent(i);
            vi2 = v2.getComponent(i);
            vf1 = 	((m1 - m2 * Config.RESTITUTION_COEFF) *  vi1 + (m2 * COEF) 	* vi2) / totalMass;
            vf2 = 	((m1 * COEF) * vi1 + (m2 - m1 * Config.RESTITUTION_COEFF) * vi2) / totalMass;
            a.vel().setComponent(i, vf1);
            b.vel().setComponent(i, vf2);
        }

        if(a.velX() > 0.1 || a.velX() < -0.1) {

            a.pos().print();
            b.pos().print();
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