package sph;
import java.util.LinkedList;


public class Particle {
	LinkedList<Particle> neighborList = new LinkedList<Particle>(); 
	LinkedList<Particle> neighborListSwap = new LinkedList<Particle>(); 
	LinkedList<Particle> solidNeighborList = new LinkedList<Particle>(); 
	int element;
	int phase;
	double mass;
	double temperature;
	double heat;
	double[] x = new double[3];
	double[] dx = new double[3];
	double[] ddx = new double[3];
	double no_force_distance_squared = 1;
	//do discrete transitions to particle states - full or nothing.  based on probability.
	
	public double getLiquidForce(double distance_squared) {
		if( distance_squared >= no_force_distance_squared)
			return 0;
		return (no_force_distance_squared-distance_squared)/distance_squared;
	}
	
	public void updateNeighborList() {
		neighborListSwap.clear();
		double id_squared = Global.Settings.interaction_distance*Global.Settings.interaction_distance;
		for( Particle p : neighborList) {
			double d = distance_squared(this.x,p.x);
			if( d <= id_squared) {
				neighborListSwap.add(p);
			}
			for( Particle p2 : p.neighborList) {
				if( p2 == this)
					continue;
				d = distance_squared(this.x,p2.x);
				if( d <= id_squared) {
					if( !neighborListSwap.contains(p2)) {
						neighborListSwap.add(p2);
					}
				}
			}
		}
	}
	public void swapNeighborList() {
		LinkedList<Particle> t = neighborList;
		neighborList = neighborListSwap;
		neighborListSwap = t;
	}
	public static double distance_squared(double[] x1, double[] x2) {
		double sum = 0;
		for( int i = 0; i < x1.length; i++) {
			double d0 = x1[i]-x2[i];
			sum += d0*d0;
		}
		return sum;
	}

}
