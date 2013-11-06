import java.awt.Point;
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
		System.out.println("*********************************************");
		System.out.println(count);
		System.out.println("*********************************************");
		return count == 1 ? true : false;
	}
	
//	public static boolean is_target(state x) {
////		char[][] grid = x.grid;
////		for (int l = 0; l < 4; ++l) {
////			try{
////				if(grid[curi + dx[k]][curj + dy[k]] == 'r') {
////					boolean found = false;
////					for (int p = 0; p < connected_parts.size(); ++p) {
////						if (connected_parts.get(p).x == curi + dx[k] && connected_parts.get(p).y == curj + dy[k]) {
////							found = true;
////							break;
////						}
////					}
////					if(!found) {
////						connected_parts.add(new Point(curi + dx[k], curj + dy[k]));
////						connected_parts_old.add(new Point(curi + dx[k], curj + dy[k]));
////					}
////				}
////			}
////			catch (Exception e){
////				
////			}
////		}
//	}
	
	public static void display(char [][] grid){
		for (int i =0 ; i < grid.length; i++){
			System.out.println();
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
		}
		System.out.println();
	}

	public static ArrayList<state> move(state current) {
		ArrayList<state> new_states = new ArrayList<state>();
		char[][] grid = current.grid;
		int cur_cost = current.cost;
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[0].length; ++j) {
				display(grid);
				if (grid[i][j] == 'r') {// found robot part
					System.out.println("in if");
					ArrayList<Point> connected_parts = new ArrayList<Point>();
					ArrayList<Point> connected_parts_old = new ArrayList<Point>();
					for (int k = 0; k < 4; ++k) {// loop on direction
						int curi = i, curj = j;
						int cost = 0;
						for (int l = 0; l < 4; ++l) {
							try{
								if(grid[curi + dx[k]][curj + dy[k]] == 'r') {
									boolean found = false;
									for (int p = 0; p < connected_parts.size(); ++p) {
										if (connected_parts.get(p).x == curi + dx[k] && connected_parts.get(p).y == curj + dy[k]) {
											found = true;
											break;
										}
									}
									if(!found) {
										connected_parts.add(new Point(curi + dx[k], curj + dy[k]));
										connected_parts_old.add(new Point(curi + dx[k], curj + dy[k]));
									}
								}
							}
							catch (Exception e){
								
							}
						}
						if (connected_parts.size() != 0) {
							System.out.println("if");
							boolean running = true;
							while(running) {
								for (int p = 0; p < connected_parts.size(); ++p) {
									if(!(connected_parts.get(p).x + dx[k] >= 0 && connected_parts.get(p).x + dx[k] < grid.length && connected_parts.get(p).y + dy[k] >= 0 && connected_parts.get(p).y + dy[k] < grid[0].length 
										&& grid[connected_parts.get(p).x + dx[k]][connected_parts.get(p).y + dy[k]] != '#' && grid[connected_parts.get(p).x + dx[k]][connected_parts.get(p).y + dy[k]] != 'r')) {
										curi = connected_parts.get(p).x;
										curj = connected_parts.get(p).y;
										running = false;
										break;
									}
								}
								if (running){
									for (int p = 0; p < connected_parts.size(); ++p) {
										connected_parts.get(p).x = connected_parts.get(p).x + dx[k];
										connected_parts.get(p).y = connected_parts.get(p).y + dy[k];
									}
								}
							}
						}
						else {
							System.out.println("in else");
							while (curi + dx[k] >= 0 && curi + dx[k] < grid.length && curj + dy[k] >= 0 && curj + dy[k] < grid[0].length 
									&& grid[curi + dx[k]][curj + dy[k]] != '#' && grid[curi + dx[k]][curj + dy[k]] != 'r') {
								curi = curi + dx[k];
								curj = curj + dy[k];
								cost++;
							}
						}
						//System.out.println("cost" +cost);
						if (curi + dx[k] < 0 || curi + dx[k] >= grid.length || curj + dy[k] < 0|| curj + dy[k] >= grid[0].length)
							continue;
						char nxt = grid[curi + dx[k]][curj + dy[k]];
						if (nxt == 'r') {
							// new move collided with robot party
							char[][] new_grid = new char[grid.length][grid[0].length];
							for (int ii = 0; ii < grid.length; ++ii)
								for (int jj = 0; jj < grid[0].length; ++jj)
									new_grid[ii][jj] = grid[ii][jj];
							
							if (connected_parts.size() != 0) {
								for (int p = 0; p < connected_parts_old.size(); ++p) {
									new_grid[connected_parts_old.get(p).x][connected_parts_old.get(p).y] = '.';
								}
								for (int p = 0; p < connected_parts.size(); ++p) {
									new_grid[connected_parts.get(p).x][connected_parts.get(p).y] = 'r';
								}
							}
							else {
								new_grid[i][j] = '.';
								new_grid[curi][curj] = 'r';
							}
							state x = new state(new_grid, cur_cost + cost);
							x.partAttached();
							new_states.add(x);
							//System.out.println();
							System.out.println("==============found r===============");
							//display(new_grid);
							//System.out.println("*********************************************");
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
							//System.out.println();
							//System.out.println("==============found #===============");
							//display(new_grid);
							//System.out.println("*********************************************");
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
			System.out.println("in bfs");
			state current = bfs_queue.poll();
			char[][] grid = current.grid;
			String grid_shape = current.toString();
			System.out.println(grid_shape);
			int current_cost = current.cost;
			if (is_target(current)) {
				System.out.println("istarget");
				return current_cost;
			}
			if (vis.contains(grid_shape))
				continue;
			vis.add(grid_shape);
			// moves
			System.out.println("lol");
			ArrayList<state> new_states = move(current);
			System.out.println(new_states.size());
			//System.out.println("new states " + new_states.size());
			for (state s : new_states)
				bfs_queue.offer(s);
		}
		return -1;
	}
	
	public static int dfs(state start) {
		
		Stack<state> dfs_stack = new Stack<state>();
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
			for (int i = new_states.size()-1; i >= 0; i--)
				dfs_stack.push(new_states.get(i));
		}
		
		return -1;
		
	}
	
	public static int uniform(state start) {
		
		ArrayList<state> uniformQ = new ArrayList<state>();
		uniformQ.add(start);
		HashSet<String> vis = new HashSet<String>();
		while (!uniformQ.isEmpty()) {
			//get the index of the state with minimum cost
			int minCost = 0;
			for (int i = 1;i < uniformQ.size() ; i++){
				if(uniformQ.get(i).cost < minCost)
					minCost = i;
			}
				
			state current = (state) uniformQ.get(minCost);
			uniformQ.remove(minCost);
			
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
				uniformQ.add(s);
		}
		
		return -1;
		
	}
	public static int greedy(state state){
		
		ArrayList<state> priorityQueue = new ArrayList<state>();
		state.heuristic = heurstic1(state.grid);
		//System.out.println("wth faksjd");
		priorityQueue.add(state);
		HashSet<String> vis = new HashSet<String>();
		
		while(!priorityQueue.isEmpty()){
			int currentIndex = bestOptionGreedy(priorityQueue);
			//System.out.println(currentIndex);
			state current = priorityQueue.get(currentIndex);
			priorityQueue.remove(currentIndex);
			
			if(is_target(current)){
				return current.cost;
			}
			System.out.println("*********************************************");
			//System.out.println(current.toString());
			System.out.println(current.heuristic);
			display(current.grid);
			String gridShape = current.toString();
			if (vis.contains(gridShape))
				continue;
			vis.add(gridShape);
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (state s : new_states){
				s.heuristic = heurstic1(s.grid);
				priorityQueue.add(s);
			}
		}
		
		
		return -1;
	}
	public static int aStar(state state){
		
		ArrayList<state> priorityQueue = new ArrayList<state>();
		state.heuristic = heurstic1(state.grid);
		//System.out.println("wth faksjd");
		priorityQueue.add(state);
		HashSet<String> vis = new HashSet<String>();
		
		while(!priorityQueue.isEmpty()){
			int currentIndex = bestOptionAStar(priorityQueue);
			//System.out.println(currentIndex);
			state current = priorityQueue.get(currentIndex);
			priorityQueue.remove(currentIndex);

			if(is_target(current)){
				return current.cost;
			}
			System.out.println("*********************************************");
			//System.out.println(current.toString());
			System.out.println(current.heuristic);
			display(current.grid);
			String gridShape = current.toString();
			if (vis.contains(gridShape))
				continue;
			vis.add(gridShape);
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (state s : new_states){
				s.heuristic = heurstic1(s.grid);
				priorityQueue.add(s);
			}
		}
		
		
		return -1;
	}
	
	public static int bestOptionGreedy(ArrayList<state> list){
		int shortestHeuristic = list.get(0).heuristic;
		int locationInList = 0;
		for (int i = 0; i < list.size(); i++){
			System.out.println("hello33  "+list.get(i).heuristic);
			if (list.get(i).heuristic < shortestHeuristic){
				shortestHeuristic = list.get(i).heuristic;
				System.out.println("hello"+shortestHeuristic);
				locationInList = i;
			}
		}
		return locationInList;
	}
	public static int bestOptionAStar(ArrayList<state> list){
		int shortestHeuristicPlusCost = list.get(0).heuristic + list.get(0).cost;
		int locationInList = 0;
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).heuristic < shortestHeuristicPlusCost){
				shortestHeuristicPlusCost = list.get(i).heuristic + list.get(i).cost;
				System.out.println("hello   " + shortestHeuristicPlusCost);
				locationInList = i;
			}
		}
		return locationInList;
	}
	
	public static int heurstic1(char[][] grid){
		
		int retVal = 0, dx = 0, dy = 0;
		loop:
		for (int i = 0; i<grid.length; i++){
			for (int j = 0; j<grid[0].length; j++){
				if(grid[i][j] == 'r'){
					dx = i;
					dy = j;
					break loop;
					
				}
			}
		}
		for (int ii = 0; ii < grid.length; ii++){
			for(int jj = 0; jj< grid[0].length; jj++){
				if(grid[ii][jj] == 'r' && (dx != ii) && (dy != jj)){
					retVal = ((Math.abs(dx - ii ) + Math.abs(dy -jj))-1) + retVal;
					System.out.println(retVal+ "dx :" +dx + "dy :" + dy);
					dx = ii;
					dy = jj;
						
				}
			}
		}
		//System.out.println(retVal);
		return retVal;

		}
		

	public static int heurstic2(){
		return 0;
		
	}

	// ..#.
	// r..#
	// #...
	// .r#.
	public static void main(String[] args) throws Exception {
		char[][] test_board = new char[4][4];
		test_board[0][0] = '.';
		test_board[0][1] = '.';
		test_board[0][2] = '.';
		test_board[0][3] = '.';
		test_board[1][0] = 'r';
		test_board[1][1] = '.';
		test_board[1][2] = 'r';
		test_board[1][3] = '.';
		test_board[2][0] = '.';
		test_board[2][1] = '.';
		test_board[2][2] = '.';
		test_board[2][3] = '.';
		test_board[3][0] = 'r';
		test_board[3][1] = '.';
		test_board[3][2] = '.';
		test_board[3][3] = '.';
		display(test_board);
		Grid test = new Grid();
		test.display();
		System.out.println();
		System.out.println("=====================");
		state start = new state(test_board, 0);
		System.out.println("lama nshuf");
		System.out.println(test.grid[0][0]);
		int ans = bfs(start);
		System.out.println("total cost = " + ans);
	}
}
