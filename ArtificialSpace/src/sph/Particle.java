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
	//do discrete transitions to particle states - full or nothing.  based on probability.
	
	public void updateNeighborList() {
		
	}

}
