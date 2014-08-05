package sph;
import java.util.LinkedList;



public class Solid {
	LinkedList<Particle> particleList = new LinkedList<Particle>();
	LinkedList<Particle> edgeParticleList = new LinkedList<Particle>(); //any particle not full or next to not full or not all neighbor slots filled - only these need get their forces and melting and what not considered 
	double[] center_of_mass = new double[3];
	double[] x = new double[3];
	double[] dx = new double[3];
	double[] ddx = new double[3];
	double[] rx = new double[3];
	double[] rdx = new double[3];
	double[] rddx = new double[3];
	


}
