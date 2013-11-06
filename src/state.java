class state {
	char grid[][];
	int cost;
	int lvl;
	int numberOfParts;

	int heuristic;
	public state(char[][] grid, int cost, int lvl  ) {
		this.grid = grid;
		this.cost = cost;
		this.lvl = lvl;
		this.numberOfParts = this.setNumberOfParts();
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < grid.length; ++i)
			for (int j = 0; j < grid[0].length; ++j)
				ret += grid[i][j];
		return ret;
	}
	public int setNumberOfParts(){
		int count = 0;
		for(int i = 0 ; i < grid.length ; ++i)
			for(int j = 0 ; j < grid[0].length ; ++j)
				if(grid[i][j] == 'r')
					++count;
		return count;
	}
	public void partAttached(){
		numberOfParts-- ;
	}
	public int numberOfParts(){
		return numberOfParts;
	}
}