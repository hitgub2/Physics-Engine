public abstract class Calculator {

	/*
	 * @return distance between two things positions
	 */
	public static double distance(Thing one, Thing other) {
		return Math.sqrt(distance_sqr(one, other));
	}
	public static double distance_sqr(Thing one, Thing other) {
		double[] oneXY = one.getXY();
		double[] otherXY = other.getXY();
		return (Math.pow(oneXY[0]-otherXY[0], 2) + Math.pow(oneXY[1]-otherXY[1], 2));
	}
	
	public static double abs(double x) {
		return x>=0 ? x : -x;
	}
}
