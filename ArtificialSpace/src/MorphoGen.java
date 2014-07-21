
public class MorphoGen {
	//a morphogen controls the rates of a number of chemical reactions
	public double[] x = new double[]{0,0,0};
	public double[] dx = new double[]{0,0,0};
	public double mass;
	public double energy;
	public double split_energy;

	
	//genetic
	public double [] internal_state = new double[Settings.num_internals];
	public double [] internal_state_deltas = new double[Settings.num_internals];

	public double [] sigmoid_bias = new double[Settings.num_sigmoids];
	public double [] sigmoid_sensitivity = new double[Settings.num_chemicals+Settings.num_internals];
	public double [][] sigmoid_mix = new double[Settings.num_sigmoids][Settings.num_chemicals+Settings.num_internals];
	public double [] sigmoid_mult = new double[Settings.num_sigmoids];
	public double [] sigmoid_post_mult = new double[Settings.num_sigmoids];

	public double [] reaction_bias = new double[Settings.num_controlled_reactions];
	public double [] reaction_sensitivity = new double[Settings.num_chemicals+Settings.num_internals+Settings.num_sigmoids];
	public double [][] reaction_mix = new double[Settings.num_controlled_reactions][Settings.num_chemicals+Settings.num_internals+Settings.num_sigmoids];
	public double [] reaction_mult = new double[Settings.num_controlled_reactions];

	public double [] internal_state_bias = new double[Settings.num_internals];
	public double [] internal_state_sensitivity = new double[Settings.num_chemicals+Settings.num_internals+Settings.num_sigmoids];
	public double [][] internal_state_mix = new double[Settings.num_internals][Settings.num_chemicals+Settings.num_internals+Settings.num_sigmoids];
	public double [] internal_state_mult = new double[Settings.num_internals];

	//scratch
	public double [] chemicals = new double[Settings.num_chemicals];
	public double [] inputs = new double[Settings.num_chemicals+Settings.num_internals+Settings.num_sigmoids];
	public double [] reaction_rates = new double[Settings.num_controlled_reactions];
	
	public MorphoGen() {
		init();
	}
	
	public void init() {
		initFromParents(new MorphoGen[]{},new double[]{});
	}

	public void initFromParents(MorphoGen[] parents, double[] weights) {
		double mult_final = 0;
		for( int i = 0; i < weights.length; i++) {
			mult_final += weights[i];
		}
		if( mult_final == 0)
			mult_final = 1;
		mult_final = 2.0/mult_final;
		
		//internal
		for(int i = 0; i < internal_state.length; i++) {
			internal_state[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				internal_state[i] += parents[j].internal_state[i] * weights[j] * Math.random();
			}
			internal_state[i] *= mult_final;
		}
		
		//sigmoids
		for(int i = 0; i < sigmoid_bias.length; i++) {
			sigmoid_bias[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				sigmoid_bias[i] += parents[j].sigmoid_bias[i] * weights[j] * Math.random();
			}
			sigmoid_bias[i] *= mult_final;
		}
		for(int i = 0; i < sigmoid_sensitivity.length; i++) {
			sigmoid_sensitivity[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				sigmoid_sensitivity[i] += parents[j].sigmoid_sensitivity[i] * weights[j] * Math.random();
			}
			sigmoid_sensitivity[i] *= mult_final;
		}
		for(int i = 0; i < sigmoid_mult.length; i++) {
			sigmoid_mult[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				sigmoid_mult[i] += parents[j].sigmoid_mult[i] * weights[j] * Math.random();
			}
			sigmoid_mult[i] *= mult_final;
		}
		for(int i = 0; i < sigmoid_post_mult.length; i++) {
			sigmoid_post_mult[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				sigmoid_post_mult[i] += parents[j].sigmoid_post_mult[i] * weights[j] * Math.random();
			}
			sigmoid_post_mult[i] *= mult_final;
		}
		for(int i = 0; i < sigmoid_mix.length; i++) {
			for(int k = 0; k < sigmoid_mix[i].length; k++) {
				sigmoid_mix[i][k] = 0;
				for( int j = 0; j < parents.length; j++) {
					sigmoid_mix[i][k] += parents[j].sigmoid_mix[i][k] * weights[j] * Math.random();
				}
				sigmoid_mix[i][k] *= mult_final;
			}
		}

		//reaction
		for(int i = 0; i < reaction_bias.length; i++) {
			reaction_bias[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				reaction_bias[i] += parents[j].reaction_bias[i] * weights[j] * Math.random();
			}
			reaction_bias[i] *= mult_final;
		}
		for(int i = 0; i < reaction_sensitivity.length; i++) {
			reaction_sensitivity[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				reaction_sensitivity[i] += parents[j].reaction_sensitivity[i] * weights[j] * Math.random();
			}
			reaction_sensitivity[i] *= mult_final;
		}
		for(int i = 0; i < reaction_mult.length; i++) {
			reaction_mult[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				reaction_mult[i] += parents[j].reaction_mult[i] * weights[j] * Math.random();
			}
			reaction_mult[i] *= mult_final;
		}
		for(int i = 0; i < reaction_mix.length; i++) {
			for(int k = 0; k < reaction_mix[i].length; k++) {
				reaction_mix[i][k] = 0;
				for( int j = 0; j < parents.length; j++) {
					reaction_mix[i][k] += parents[j].reaction_mix[i][k] * weights[j] * Math.random();
				}
				reaction_mix[i][k] *= mult_final;
			}
		}

		//internal_state
		for(int i = 0; i < internal_state_bias.length; i++) {
			internal_state_bias[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				internal_state_bias[i] += parents[j].internal_state_bias[i] * weights[j] * Math.random();
			}
			reaction_bias[i] *= mult_final;
		}
		for(int i = 0; i < internal_state_sensitivity.length; i++) {
			internal_state_sensitivity[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				internal_state_sensitivity[i] += parents[j].internal_state_sensitivity[i] * weights[j] * Math.random();
			}
			internal_state_sensitivity[i] *= mult_final;
		}
		for(int i = 0; i < internal_state_mult.length; i++) {
			internal_state_mult[i] = 0;
			for( int j = 0; j < parents.length; j++) {
				internal_state_mult[i] += parents[j].internal_state_mult[i] * weights[j] * Math.random();
			}
			internal_state_mult[i] *= mult_final;
		}
		for(int i = 0; i < internal_state_mix.length; i++) {
			for(int k = 0; k < internal_state_mix[i].length; k++) {
				internal_state_mix[i][k] = 0;
				for( int j = 0; j < parents.length; j++) {
					internal_state_mix[i][k] += parents[j].internal_state_mix[i][k] * weights[j] * Math.random();
				}
				internal_state_mix[i][k] *= mult_final;
			}
		}
	}	
	
	public void iterate() {
		//copy chemical state to input
		for( int i = 0; i < Settings.num_chemicals; i++) {
			inputs[i] = chemicals[i];
		}
		//copy internal state to input
		for( int i = 0; i < Settings.num_internals; i++) {
			inputs[i+Settings.num_chemicals] = internal_state[i];
		}

		//generate sigmoid signals
		for( int i = 0; i < Settings.num_sigmoids; i++) {
			int n = i+Settings.num_chemicals+Settings.num_internals;
			inputs[n] = sigmoid_bias[i];
			for( int j = 0; j < Settings.num_chemicals+Settings.num_internals; j++) {
				inputs[n] += sigmoid_mix[i][j]*inputs[j]*sigmoid_sensitivity[j];
			}
			inputs[n] *= sigmoid_mult[i];
			inputs[n] = sigmoid_function(inputs[n]);
			inputs[n] *= sigmoid_post_mult[n];
		}
		
		//generate reaction rates.
		for( int i = 0; i < reaction_rates.length; i++) {
			reaction_rates[i] = reaction_bias[i];
			for( int j = 0; j < inputs.length; j++) {
				reaction_rates[i] += reaction_mix[i][j]*inputs[j]*reaction_sensitivity[j];
			}
			reaction_rates[i] *= reaction_mult[i];
		}
		
		//generate internal state changes.
		for( int i = 0; i < internal_state.length; i++) {
			internal_state_deltas[i] = internal_state_bias[i];
			for( int j = 0; j < inputs.length; j++) {
				internal_state_deltas[i] += internal_state_mix[i][j]*inputs[j]*internal_state_sensitivity[j];
			}
			internal_state_deltas[i] *= internal_state_mult[i];
		}
	}
	
	public void integrate(double dt) {
		for( int i = 0; i < internal_state.length; i++) {
			internal_state[i] += internal_state_deltas[i] * dt;
		}
		for( int i = 0; i < internal_state.length; i++) {
			internal_state_deltas[i] = 0;
		}
		
	}


	
	//used for nueral net
	public static double sigmoid_function(double d) {
		return 2.0/(1+Math.exp(-d))-1.0;
	}
	
	//used for true reaction rates. (can only inhibit or excite between 0 and 1)
	public static double logistic_function(double d) {
		return 1.0/(1+Math.exp(-d));
	}

}
