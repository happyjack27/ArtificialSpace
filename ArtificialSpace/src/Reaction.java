import Global.Settings;


public class Reaction {
	public static Reaction[] allUncontrolledReactions = new Reaction[Settings.num_uncontrolled_reactions];
	public static Reaction[] allControlledReactions = new Reaction[Settings.num_controlled_reactions];

	//mass must match!
	
	double[] sources = new double[Settings.num_chemicals];
	double[] products = new double[Settings.num_chemicals];
	double rate;
	double activation_energy;
	double energy_diff;
	
	public static void init() {
		for(int i = 0; i < Settings.num_uncontrolled_reactions; i++) {
			allUncontrolledReactions[i] = new Reaction();
		}
		for(int i = 0; i < Settings.num_controlled_reactions; i++) {
			allControlledReactions[i] = new Reaction();
		}
	}

	
	Reaction() {
		super();
		randomize();
	}
	
	public double getReactionRateAtTemperature(double T) {
		return rate * Math.exp(-activation_energy/T);
	}

	public void randomize() {
		rate = Math.random();
		for( int i = 0; i < sources.length; i++) {
			sources[i] = Math.random()*2.0-1.0;
			if( sources[i] < 0) sources[i] = 0;
		}
		for( int i = 0; i < sources.length; i++) {
			products[i] = Math.random()*2.0-1.0;
			if( products[i] < 0) products[i] = 0;
		}
		balance();
	}
	
	public void balance() {
		
		double source_mass = 0;
		for( int i = 0; i < sources.length; i++) {
			source_mass += sources[i]*Chemical.allChemicals[i].mass;
		}
		
		double product_mass = 0;
		for( int i = 0; i < products.length; i++) {
			product_mass += products[i]*Chemical.allChemicals[i].mass;
		}
		
		source_mass /= product_mass;
		for( int i = 0; i < products.length; i++) {
			products[i] *= source_mass;
		}
		
		energy_diff = 0;

		for( int i = 0; i < sources.length; i++) {
			energy_diff += sources[i]*Chemical.allChemicals[i].binding_energy;
		}
		for( int i = 0; i < products.length; i++) {
			energy_diff -= products[i]*Chemical.allChemicals[i].binding_energy;
		}

	}
	
	public double getReactionRateForCell(double T, Cell cell) {
		return getReactionRateAtTemperature(T) * getCollisionRateForCell(cell);
	}
	public double getCollisionRateForCell( Cell cell) {
		double max_ratio = 0;
		for( int i = 0; i < Settings.num_chemicals; i++) {
			if( sources[i] <= 0)
				continue;
			if( products[i] <= 0)
				return 0;
			double test = sources[i] / products[i];
			if( test > max_ratio)
				max_ratio = test;
		}
		return 1.0/max_ratio;
	}

}
