class state {
	char grid[][];
	int cost;

	public state(char[][] grid, int cost) {
		this.grid = grid;
		this.cost = cost;
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < grid.length; ++i)
			for (int j = 0; j < grid[0].length; ++j)
				ret += grid[i][j];
		return ret;
	}
}