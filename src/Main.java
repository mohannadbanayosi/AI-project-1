import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;



public class Main {
	static int dx[] = new int[] { 1, -1, 0, 0 };
	static int dy[] = new int[] { 0, 0, 1, -1 };

	public static boolean is_target(state x) {
		int count = x.numberOfParts();
		return count == 1 ? true : false;
	}
	
	public static void display(char [][] grid){
		for (int i =0 ; i < grid.length; i++){
			System.out.println();
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
		}
	}

	public static ArrayList<state> move(state current) {
		ArrayList<state> new_states = new ArrayList<state>();
		char[][] grid = current.grid;
		int cur_cost = current.cost;
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[0].length; ++j) {
				if (grid[i][j] == 'r') {// found robot part
					for (int k = 0; k < 4; ++k) {// loop on direction
						int curi = i, curj = j;
						int cost = 0;
						while (curi + dx[k] >= 0 && curi + dx[k] < grid.length && curj + dy[k] >= 0 && curj + dy[k] < grid[0].length 
								&& grid[curi + dx[k]][curj + dy[k]] != '#' && grid[curi + dx[k]][curj + dy[k]] != 'r') {
							curi = curi + dx[k];
							curj = curj + dy[k];
							cost++;
						}
						System.out.println("cost" +cost);
						if (curi + dx[k] < 0 || curi + dx[k] >= grid.length || curj + dy[k] < 0|| curj + dy[k] >= grid[0].length)
							continue;
						char nxt = grid[curi + dx[k]][curj + dy[k]];
						if (nxt == 'r') {
							// new move collided with robot party
							char[][] new_grid = new char[grid.length][grid[0].length];
							for (int ii = 0; ii < grid.length; ++ii)
								for (int jj = 0; jj < grid[0].length; ++jj)
									new_grid[ii][jj] = grid[ii][jj];
							new_grid[i][j] = '.';
							new_grid[curi][curj] = 'r';
							state x = new state(new_grid, cur_cost + cost);
							x.partAttached();
							new_states.add(x);
							System.out.println();
							System.out.println("==============found r===============");
							display(new_grid);
						}
						if (nxt == '#') {
							// new move collided with obstacle
							char[][] new_grid = new char[grid.length][grid[0].length];
							for (int ii = 0; ii < grid.length; ++ii)
								for (int jj = 0; jj < grid[0].length; ++jj)
									new_grid[ii][jj] = grid[ii][jj];
							new_grid[i][j] = '.';
							new_grid[curi][curj] = 'r';
							new_states.add(new state(new_grid, cur_cost + cost));
							System.out.println();
							System.out.println("==============found #===============");
							display(new_grid);
						}
					}
				}
			}
		}
		return new_states;
	}

	public static int bfs(state start) {
		Queue<state> bfs_queue = new LinkedList<state>();
		bfs_queue.offer(start);
		HashSet<String> vis = new HashSet<String>();
		while (!bfs_queue.isEmpty()) {
			state current = bfs_queue.poll();
			char[][] grid = current.grid;
			String grid_shape = current.toString();
			//System.out.println(grid_shape);
			int current_cost = current.cost;
			if (is_target(current)) {
				return current_cost;
			}
			if (vis.contains(grid_shape))
				continue;
			vis.add(grid_shape);
			// moves
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (state s : new_states)
				bfs_queue.offer(s);
		}
		return -1;
	}
	
	public static int dfs(state start) {
		
		Stack dfs_stack = new Stack();
		dfs_stack.push(start);
		HashSet<String> vis = new HashSet<String>();
		while (!dfs_stack.isEmpty()) {
			state current = (state) dfs_stack.pop();
			char[][] grid = current.grid;
			String grid_shape = current.toString();
			System.out.println(grid_shape);
			int current_cost = current.cost;
			if (is_target(current)) {
				return current_cost;
			}
			if (vis.contains(grid_shape))
				continue;
			vis.add(grid_shape);
			// moves
			ArrayList<state> new_states = move(current);
			System.out.println("new states " + new_states.size());
			for (state s : new_states)
				dfs_stack.push(s);
		}
		
		return -1;
		
	}

	// ..#.
	// r..#
	// #...
	// .r#.
	public static void main(String[] args) throws Exception {
		char[][] test_board = new char[4][4];
		test_board[0][0] = '.';
		test_board[0][1] = '.';
		test_board[0][2] = '#';
		test_board[0][3] = '.';
		test_board[1][0] = 'r';
		test_board[1][1] = '.';
		test_board[1][2] = '.';
		test_board[1][3] = '#';
		test_board[2][0] = '#';
		test_board[2][1] = 'r';
		test_board[2][2] = '.';
		test_board[2][3] = '.';
		test_board[3][0] = '.';
		test_board[3][1] = '.';
		test_board[3][2] = '#';
		test_board[3][3] = '.';
		display(test_board);
//		Grid test = new Grid();
//		test.display();
		System.out.println();
		System.out.println("=====================");
		state start = new state(test_board, 0);
		int ans = bfs(start);
		System.out.println("total cost = " + ans);
	}
}
