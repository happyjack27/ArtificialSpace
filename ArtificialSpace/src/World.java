import java.util.*;

public class World {
	Vector<Cell> cells = new Vector<Cell>();
	Cell[][][] grid = new Cell[Settings.world_size][Settings.world_size][Settings.world_size];
	
	World() {
		Chemical.init();
		Reaction.init();
		
		int size = Settings.world_size;
		for( int i = 0; i < size; i++) {
			for( int j = 0; j < size; j++) {
				for( int k = 0; k < size; k++) {
					grid[i][j][k] = new Cell();
					cells.add(grid[i][j][k]);
				}			
			}			
		}
		
		for( int i = 0; i < size; i++) {
			for( int j = 0; j < size; j++) {
				for( int k = 0; k < size; k++) {
					grid[i][j][k].neighbors = new Cell[]{
							grid[(i - 1) % size][(j - 1) % size][(k - 1) % size], grid[(i - 1) % size][(j - 1) % size][(k - 0) % size], grid[(i - 1) % size][(j - 1) % size][(k + 1) % size], 
							grid[(i - 1) % size][(j - 0) % size][(k - 1) % size], grid[(i - 1) % size][(j - 0) % size][(k - 0) % size], grid[(i - 1) % size][(j - 0) % size][(k + 1) % size], 
							grid[(i - 1) % size][(j + 1) % size][(k - 1) % size], grid[(i - 1) % size][(j + 1) % size][(k - 0) % size], grid[(i - 1) % size][(j + 1) % size][(k + 1) % size], 

							grid[(i - 0) % size][(j - 1) % size][(k - 1) % size], grid[(i - 0) % size][(j - 1) % size][(k - 0) % size], grid[(i - 0) % size][(j - 1) % size][(k + 1) % size], 
							grid[(i - 0) % size][(j - 0) % size][(k - 1) % size], grid[(i - 0) % size][(j - 0) % size][(k - 0) % size], grid[(i - 0) % size][(j - 0) % size][(k + 1) % size], 
							grid[(i - 0) % size][(j + 1) % size][(k - 1) % size], grid[(i - 0) % size][(j + 1) % size][(k - 0) % size], grid[(i - 0) % size][(j + 1) % size][(k + 1) % size], 

							grid[(i + 1) % size][(j - 1) % size][(k - 1) % size], grid[(i + 1) % size][(j - 1) % size][(k - 0) % size], grid[(i + 1) % size][(j - 1) % size][(k + 1) % size], 
							grid[(i + 1) % size][(j - 0) % size][(k - 1) % size], grid[(i + 1) % size][(j - 0) % size][(k - 0) % size], grid[(i + 1) % size][(j - 0) % size][(k + 1) % size], 
							grid[(i + 1) % size][(j + 1) % size][(k - 1) % size], grid[(i + 1) % size][(j + 1) % size][(k - 0) % size], grid[(i + 1) % size][(j + 1) % size][(k + 1) % size], 
					};
				}			
			}			
		}
	}
	public void iterate( double dt) {
		for(Cell c : cells) {
			c.iterateReaction();
		}
		for(Cell c : cells) {
			c.integrateReaction(dt);
		}
		for(Cell c : cells) {
			c.iterateDiffusion();
		}
		for(Cell c : cells) {
			c.integrateDiffusion(dt);
		}
	}

}
