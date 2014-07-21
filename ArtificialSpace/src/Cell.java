import java.util.*;

public class Cell {
	
	double[] chemical_quantities = new double[Settings.num_chemicals];
	double heat;
	
	double[] chemical_deltas = new double[Settings.num_chemicals];
	double heat_delta;
	
	double[] rate_controls = new double[Settings.num_controlled_reactions];
	
	public Cell[] neighbors = new Cell[]{};
	Vector<MorphoGen> morphoGens = new Vector<MorphoGen>();

	public void iterateReaction() {
		for( MorphoGen m : morphoGens) {
			m.chemicals = this.chemical_quantities;
			m.iterate();
		}
		getRateControls();
		doReactions();
	}
	public void integrateReaction(double dt) {
		for( int i = 0; i < Settings.num_chemicals; i++) {
			chemical_quantities[i] += chemical_deltas[i]*dt;
		}
		heat += heat_delta*dt;
		
		for( int i = 0; i < Settings.num_chemicals; i++) {
			chemical_deltas[i] = 0;
		}
		heat_delta = 0;
		for( MorphoGen m : morphoGens) {
			m.integrate(dt);
		}
	}
	public void iterateDiffusion() {
		double T = getTemperature();
		for( int i = 0; i < Settings.num_chemicals; i++) {
			for( Cell cell : neighbors) {
				double diffuse_amount = Chemical.allChemicals[i].diffusion_rate*chemical_quantities[i]*Settings.rr_multiplier*T;
				
				double heat_per_amount = T*Chemical.allChemicals[i].mass*Chemical.allChemicals[i].specific_heat;//Chemical.allChemicals[i].
				
				double diffuse_heat = heat_per_amount*diffuse_amount;
				
				cell.chemical_deltas[i] += diffuse_amount;
				cell.heat_delta += diffuse_heat;
				
				chemical_deltas[i] -= diffuse_amount;
				heat_delta -= diffuse_heat;
			}
		}
	}
	public void integrateDiffusion(double dt) {
		for( int i = 0; i < Settings.num_chemicals; i++) {
			chemical_quantities[i] += chemical_deltas[i]*dt;
		}
		heat += heat_delta*dt;
		
		for( int i = 0; i < Settings.num_chemicals; i++) {
			chemical_deltas[i] = 0;
		}
		heat_delta = 0;
	}

	public void getRateControls() {
		for( int i = 0; i < rate_controls.length; i++) {
			rate_controls[i] = 0;
			for( MorphoGen m : morphoGens) {
				rate_controls[i] += m.reaction_rates[i];
			}
			rate_controls[i] = MorphoGen.logistic_function(rate_controls[i]);
		}
	}
	
	public double getTemperature() {
		double cm = 0;
		for( int i = 0; i < chemical_quantities.length; i++) {
			cm +=  chemical_quantities[i] * Chemical.allChemicals[i].mass * Chemical.allChemicals[i].specific_heat;
		}
		return heat/cm;
	}
	public void doReactions() {
		double T = getTemperature();
		double[] uncontrolled_reaction_rates = new double[Settings.num_uncontrolled_reactions];
		double[] controlled_reaction_rates = new double[Settings.num_controlled_reactions];

		for( int i = 0; i < Settings.num_uncontrolled_reactions; i++) {
			uncontrolled_reaction_rates[i] = Reaction.allUncontrolledReactions[i].getReactionRateForCell(T,this);
		}
		for( int i = 0; i < Settings.num_controlled_reactions; i++) {
			controlled_reaction_rates[i] = Reaction.allControlledReactions[i].getReactionRateForCell(T,this)*rate_controls[i];
		}
		for( int i = 0; i < Settings.num_uncontrolled_reactions; i++) {
			Reaction r = Reaction.allUncontrolledReactions[i];
			heat_delta += r.energy_diff*uncontrolled_reaction_rates[i];
			for( int j = 0; j < Settings.num_chemicals; j++) {
				chemical_deltas[j] += (r.products[j]-r.sources[j])*uncontrolled_reaction_rates[i];
			}
		}		
		for( int i = 0; i < Settings.num_controlled_reactions; i++) {
			Reaction r = Reaction.allControlledReactions[i];
			heat_delta += r.energy_diff*controlled_reaction_rates[i];
			for( int j = 0; j < Settings.num_chemicals; j++) {
				chemical_deltas[j] += (r.products[j]-r.sources[j])*controlled_reaction_rates[i];
			}
		}
}
}
