package com.engine.simulation;

import com.engine.rigidbody.Circle;
import com.engine.rigidbody.Polygon;
import com.engine.rigidbody.RigidBody;

public class Gjk {
    public static final int MAX_ITERATION = 20;

    public final int support(final int[] x, final int[] y, final Vec2d direction) {
        double maxValue = -Double.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < x.length; i++) {
            final Vec2d vertex = new Vec2d(x[i], y[i]);
            final double value = vertex.dot(direction);

            if (value > maxValue) {
                maxValue = value;
                index = i;
            }
        }

        return index;
    }

    // get direction vector of Minkowski
    public final Vec2d searchDirection(final Simplex simplex) {
        switch (simplex.count) {
            case 1:
                return simplex.vertexA.point.negate();
            case 2:
                Vec2d edgeAB = simplex.vertexB.point.sub(simplex.vertexA.point);
                double sgn = edgeAB.cross(simplex.vertexA.point.negate());
                if (sgn > 0.0f) {
//                    return Vec2d.cross(1.0f, edgeAB);
                    return edgeAB.cross_s(1.0);
                } else {
//                    return Vec2d.cross(edgeAB, 1.0f);
                    return edgeAB.cross_f(1.0);
                }
            default:
                return new Vec2d(0.0, 0.0);
        }
    }

    public class SimplexVertex {
        public Vec2d point1 = new Vec2d();
        public Vec2d point2 = new Vec2d();
        public Vec2d point = new Vec2d();
        public double u;
        public int index1;
        public int index2;

        public SimplexVertex clone() {
            SimplexVertex simplexVertex = new SimplexVertex();
            simplexVertex.point1 = point1;
            simplexVertex.point2 = point2;
            simplexVertex.point = point;
            simplexVertex.u = u;
            simplexVertex.index1 = index1;
            simplexVertex.index2 = index2;
            return simplexVertex;
        }
    }

    public class Simplex {
        public SimplexVertex vertexA = new SimplexVertex();
        public SimplexVertex vertexB = new SimplexVertex();
        public SimplexVertex vertexC = new SimplexVertex();
        public double divisor;
        public int count;
    }

    public final void witnessPoints(final Simplex simplex, Vec2d point1, Vec2d point2) {
        double s = 1.0 / simplex.divisor;

        switch (simplex.count) {
            case 1:
                point1.set(simplex.vertexA.point1);
                point2.set(simplex.vertexA.point2);
                break;

            case 2:
                Vec2d uA1 = simplex.vertexA.point1.mul(s * simplex.vertexA.u);
                Vec2d uB1 = simplex.vertexB.point1.mul(s * simplex.vertexB.u);
                point1.set(uA1.sum(uB1));

                Vec2d uA2 = simplex.vertexA.point2.mul(s * simplex.vertexA.u);
                Vec2d uB2 = simplex.vertexB.point2.mul(s * simplex.vertexB.u);
                point2.set(uA2.sum(uB2));
                break;

            case 3:
                Vec2d uA = simplex.vertexA.point1.mul(s * simplex.vertexA.u);
                Vec2d uB = simplex.vertexB.point1.mul(s * simplex.vertexB.u);
                Vec2d uC = simplex.vertexC.point1.mul(s * simplex.vertexC.u);
                point1.set(uA.sum(uB).sum(uC));
                point2.set(point1);
                break;

            default:
                break;
        }
    }

    public final void solve2(final Simplex simplex, final Vec2d Q) {
        final Vec2d A = simplex.vertexA.point;
        final Vec2d B = simplex.vertexB.point;

        final double u = Q.sub(B).dot(A.sub(B));
        final double v = Q.sub(A).dot(B.sub(A));

        if (v <= 0.0f) {
            simplex.vertexA.u = 1.0;
            simplex.divisor = 1.0;
            simplex.count = 1;
            return;
        }

        if (u <= 0.0f) {
            simplex.vertexA = simplex.vertexB.clone();
            simplex.vertexA.u = 1.0;
            simplex.divisor = 1.0;
            simplex.count = 1;
            return;
        }

        simplex.vertexA.u = u;
        simplex.vertexB.u = v;
        Vec2d e = B.sub(A);
        simplex.divisor = e.dot(e);
        simplex.count = 2;
    }

    public final void solve3(final Simplex simplex, final Vec2d Q) {

        final Vec2d A = simplex.vertexA.point;
        final Vec2d B = simplex.vertexB.point;
        final Vec2d C = simplex.vertexC.point;

        final double uAB = Q.sub(B).dot(A.sub(B));
        final double vAB = Q.sub(A).dot(B.sub(A));

        final double uBC = Q.sub(C).dot(B.sub(C));
        final double vBC = Q.sub(B).dot(C.sub(B));

        final double uCA = Q.sub(A).dot(C.sub(A));
        final double vCA = Q.sub(C).dot(A.sub(C));

        if (vAB <= 0.0 && uCA <= 0.0) {
            simplex.vertexA.u = 1.0;
            simplex.divisor = 1.0;
            simplex.count = 1;
            return;
        }

        if (uAB <= 0.0 && vBC <= 0.0) {
            simplex.vertexA = simplex.vertexB.clone();
            simplex.vertexA.u = 1.0;
            simplex.divisor = 1.0;
            simplex.count = 1;
            return;
        }

        if (uBC <= 0.0 && vCA <= 0.0) {
            simplex.vertexA = simplex.vertexC.clone();
            simplex.vertexA.u = 1.0;
            simplex.divisor = 1.0;
            simplex.count = 1;
            return;
        }

        final double area = B.sub(A).cross(C.sub(A));

        final double uABC = B.sub(Q).cross(C.sub(Q));
        final double vABC = C.sub(Q).cross(A.sub(Q));
        final double wABC = A.sub(Q).cross(B.sub(Q));

        if (uAB > 0.0 && vAB > 0.0 && wABC * area <= 0.0) {
            simplex.vertexA.u = uAB;
            simplex.vertexB.u = vAB;
            Vec2d e = B.sub(A);
            simplex.divisor = e.dot(e);
            simplex.count = 2;
            return;
        }

        if (uBC > 0.0 && vBC > 0.0 && uABC * area <= 0.0) {
            simplex.vertexA = simplex.vertexB.clone();
            simplex.vertexB = simplex.vertexC.clone();

            simplex.vertexA.u = uBC;
            simplex.vertexB.u = vBC;
            Vec2d e = C.sub(B);
            simplex.divisor = e.dot(e);
            simplex.count = 2;
            return;
        }

        if (uCA > 0.0 && vCA > 0.0 && vABC * area <= 0.0) {
            simplex.vertexB = simplex.vertexA.clone();
            simplex.vertexA = simplex.vertexC.clone();

            simplex.vertexA.u = uCA;
            simplex.vertexB.u = vCA;
            Vec2d e = A.sub(C);
            simplex.divisor = e.dot(e);
            simplex.count = 2;
            return;
        }

        simplex.vertexA.u = uABC;
        simplex.vertexB.u = vABC;
        simplex.vertexC.u = wABC;
        simplex.divisor = area;
        simplex.count = 3;
    }

    public final Output distance(final int[] xA, final int[] yA, final int[] xB, final int[] yB) {
        Output output = new Output();
        Simplex simplex = new Simplex();

        int[] save1 = new int[3];
        int[] save2 = new int[3];
        int saveCount = 0;
        int iteration = 0;

        simplex.vertexA.point1 = new Vec2d(xA[0], yA[0]);
        simplex.vertexA.point2 = new Vec2d(xB[0], yB[0]);
        simplex.vertexA.point = simplex.vertexA.point2.sub(simplex.vertexA.point1);
        simplex.vertexA.u = 1.0;
        simplex.vertexA.index1 = 0;
        simplex.vertexA.index2 = 0;
        simplex.count = 1;

        while (iteration < MAX_ITERATION) {
            saveCount = simplex.count;

            save1[0] = simplex.vertexA.index1;
            save2[0] = simplex.vertexA.index2;

            save1[1] = simplex.vertexB.index1;
            save2[1] = simplex.vertexB.index2;

            save1[2] = simplex.vertexC.index1;
            save2[2] = simplex.vertexC.index2;

            switch (simplex.count) {
                case 1:
                    break;
                case 2:
                    solve2(simplex, new Vec2d(0.0, 0.0));
                    break;
                case 3:
                    solve3(simplex, new Vec2d(0.0f, 0.0f));
                    break;
            }

            if (simplex.count == 3) {
                break;
            }

            Vec2d d = searchDirection(simplex);
            if(d.dot(d) == 0.0f) {
                break;
            }

            SimplexVertex vertex = null;
            switch(simplex.count) {
                case 0:
                    vertex = simplex.vertexA;
                    break;
                case 1:
                    vertex = simplex.vertexB;
                    break;
                case 2:
                    vertex = simplex.vertexC;
                    break;
            }
            vertex.index1 = support(xA, yA, d.negate());
            vertex.point1 = new Vec2d(xA[vertex.index1], yA[vertex.index1]);
            vertex.index2 = support(xB, yB, d);
            vertex.point2 = new Vec2d(xB[vertex.index2], yB[vertex.index2]);
            vertex.point = vertex.point2.sub(vertex.point1);


            iteration++;

            boolean duplicate = false;
            for (int i = 0; i < saveCount; i++) {
                if (vertex.index1 == save1[i] && vertex.index2 == save2[i]) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                break;
            }

            simplex.count++;
        }

        witnessPoints(simplex, output.point1, output.point2);
        output.distance = output.point1.distance(output.point2);

        return output;
    }

    public class Output {
        public Vec2d point1 = new Vec2d();
        public Vec2d point2 = new Vec2d();
        public double distance;
    }


    // Collisiion detecting methods
    public boolean collisionPP(Polygon a, Polygon b) {
        Gjk.Output output = new Gjk().distance(a.getX(), a.getY(), b.getX(), b.getY());

        if(output.distance == 0.0) {
            a.printVertices();
            b.printVertices();
            output.point1.print();
            output.point2.print();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean collisionPC(Polygon a, Circle b) {
        int[] circleX = {(int)b.posX()};
        int[] circleY = {(int)b.posY()};

        Gjk.Output output = new Gjk().distance(a.getX(), a.getY(), circleX, circleY);


        if(output.distance <= b.rad()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean collisionCC(Circle a, Circle b) {
        double distance = a.pos().distance(b.pos());
        double cond = a.rad() + b.rad();
        if(distance <= cond) {
            return true;
        }
        else return false;
    }

    public boolean collision(RigidBody a, RigidBody b) {
        Boolean ret = false;
        if(a.type() == Config.TYPE_POLYGON) {
            if(b.type() == Config.TYPE_POLYGON) {
                ret = collisionPP((Polygon)a, (Polygon)b);
            }
            else if(b.type() == Config.TYPE_CIRCLE) {
                ret = collisionPC((Polygon)a, (Circle)b);
            }
        }
        else if(a.type() == Config.TYPE_CIRCLE) {
            if(b.type() == Config.TYPE_POLYGON) {
                ret = collisionPC((Polygon)b, (Circle)a);
            }
            else if(b.type() == Config.TYPE_CIRCLE) {
                ret = collisionCC((Circle) a, (Circle)b);
            }
        }
        return ret;
    }

}
