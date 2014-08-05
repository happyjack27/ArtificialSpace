import java.util.*;

import Global.Settings;

public class Chemical {
	public static Chemical[] allChemicals = new Chemical[Settings.num_chemicals];
	
	public static void init() {
		for(int i = 0; i < Settings.num_chemicals; i++) {
			allChemicals[i] = new Chemical();
		}
	}

	
	int number;
	double mass;
	double binding_energy;
	double specific_heat;
	double diffusion_rate;
	double[] color = new double[3];
	
	Chemical() {
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
