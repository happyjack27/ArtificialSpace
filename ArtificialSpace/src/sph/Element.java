package sph;

import Global.Settings;

public class Element {
	public static Element[] allElements = new Element[Settings.num_chemicals];
	
	public static void init() {
		for(int i = 0; i < Settings.num_chemicals; i++) {
			allElements[i] = new Element();
		}
	}

	
	int number;
	double mass;
	
	double binding_energy;
	double specific_heat;
	double diffusion_rate;
	double[] color = new double[3];
	
	Element() {
		super();
		randomize();
	}

	public void randomize() {
		mass = Math.random();
		binding_energy = Math.random();
		diffusion_rate = Math.random();
		for( int i = 0; i < color.length; i++) {
			color[i] = Math.random();
		}
	}

}
