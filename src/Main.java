import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;



public class Main {
	static int dx[] = new int[] { 1, -1, 0, 0 };
	static int dy[] = new int[] { 0, 0, 1, -1 };
	
	
	/*========================================
	 				is_target(state)
	 Method used to check whether the state is 
	 a goal state.
	 ===========================================*/
	public static boolean is_target(state x) {
		char[][] grid = x.grid;
		ArrayList<Point> connected_parts = new ArrayList<Point>();
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[0].length; ++j) {
				if (grid[i][j] == 'r') {
					connected_parts.add(new Point(i, j));
					for (int count = 0; count < connected_parts.size(); ++count) {
						for (int k = 0; k < 4; ++k) {
							try{
								if(grid[connected_parts.get(count).x + dx[k]][connected_parts.get(count).y + dy[k]] == 'r') {// checks if there is any neighbor part
									boolean found = false;
									for (int p = 0; p < connected_parts.size(); ++p) {
										if ((connected_parts.get(p).x == connected_parts.get(count).x + dx[k]) && (connected_parts.get(p).y == connected_parts.get(count).y + dy[k])) {
											found = true;
											break;
										}
									}
									if(!found) {
										connected_parts.add(new Point(connected_parts.get(count).x + dx[k], connected_parts.get(count).y + dy[k]));// adds the part to the array of connected parts
									}
								}
							}
							catch (Exception e){
								
							}
						}
					}
					int count = 0;
					for(int a = 0 ; a < x.grid.length ; ++a)
						for(int s = 0 ; s < x.grid[0].length ; ++s)
							if(x.grid[a][s] == 'r')
								count++;
					
					if (connected_parts.size() == count) {
						
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*=========================================
						move(state)
	discovers the current state and returns an array
	list of states. 
	===========================================*/

	public static ArrayList<state> move(state current) {
		ArrayList<state> new_states = new ArrayList<state>();
		char[][] grid = current.grid;
		int cur_cost = current.cost;
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid[0].length; ++j) {
				//display(grid);
				if (grid[i][j] == 'r') {// found robot part
					ArrayList<Point> connected_parts = new ArrayList<Point>();
					ArrayList<Point> connected_parts_old = new ArrayList<Point>();
					
					connected_parts.add(new Point(i, j));
					connected_parts_old.add(new Point(i, j));
					for (int count = 0; count < connected_parts.size(); ++count) {
						for (int k = 0; k < 4; ++k) {
							try{
								if(grid[connected_parts.get(count).x + dx[k]][connected_parts.get(count).y + dy[k]] == 'r') {
									boolean found = false;
									for (int p = 0; p < connected_parts.size(); ++p) {
										if ((connected_parts.get(p).x == connected_parts.get(count).x + dx[k]) && (connected_parts.get(p).y == connected_parts.get(count).y + dy[k])) {
											found = true;
											break;
										}
									}
									if(!found) {
										connected_parts.add(new Point(connected_parts.get(count).x + dx[k], connected_parts.get(count).y + dy[k]));
										connected_parts_old.add(new Point(connected_parts.get(count).x + dx[k], connected_parts.get(count).y + dy[k]));
									}
								}
							}
							catch (Exception e){
								
							}
						}
					}
					
					for (int k = 0; k < 4; ++k) {// loop on direction
						int curi = i, curj = j;
						int cost = 0;
						if (connected_parts.size() != 1) {
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
							curi = connected_parts.get(0).x;
							curj = connected_parts.get(0).y;
							while (curi + dx[k] >= 0 && curi + dx[k] < grid.length && curj + dy[k] >= 0 && curj + dy[k] < grid[0].length 
									&& grid[curi + dx[k]][curj + dy[k]] != '#' && grid[curi + dx[k]][curj + dy[k]] != 'r') {
								curi = curi + dx[k];
								curj = curj + dy[k];
								cost++;
							}
						}
						if (curi + dx[k] < 0 || curi + dx[k] >= grid.length || curj + dy[k] < 0|| curj + dy[k] >= grid[0].length)
							continue;
						char nxt = grid[curi + dx[k]][curj + dy[k]];
						if (nxt == 'r') {
							// new move collided with robot party
							char[][] new_grid = new char[grid.length][grid[0].length];
							for (int ii = 0; ii < grid.length; ++ii)
								for (int jj = 0; jj < grid[0].length; ++jj)
									new_grid[ii][jj] = grid[ii][jj];

							
							if (connected_parts.size() != 1) {
								
								
								
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
							state x = new state(new_grid, cur_cost + cost, current.lvl + 1);

							x.partAttached();
							new_states.add(x);
							
						}
						if (nxt == '#') {
							// new move collided with obstacle
							char[][] new_grid = new char[grid.length][grid[0].length];
							for (int ii = 0; ii < grid.length; ++ii)
								for (int jj = 0; jj < grid[0].length; ++jj)
									new_grid[ii][jj] = grid[ii][jj];
							new_grid[i][j] = '.';
							new_grid[curi][curj] = 'r';

							new_states.add(new state(new_grid, cur_cost + cost, current.lvl + 1));
							

						}
					}
				}
			}
		}
		return new_states;
	}
	
	
	/*=================================================
	 				 Printing methods
	  display(grid):
	  prints the grid in the console
	  displayNodes(Arraylist):
	  prints the nodes path of the search algorithm
	  
	 ===================================================*/
	
	public static void display(char [][] grid){// displays the grid
		for (int i =0 ; i < grid.length; i++){
			System.out.println();
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
		}
		System.out.println();
	}
	
	public static void displayNodes(ArrayList<String> x){// displays the Nodes discovered
		System.out.println("*********Nodes Discovered By Order************");
		for (int i = 0 ; i < x.size()-1; i++){
			System.out.print(x.get(i) +" ==> ");
		}
		System.out.print(x.get(x.size()-1) + " is The Goal");
		System.out.println();
		System.out.println("*********End Of Nodes Discovered By Order************");
	}

	/*=================================================
	 				Search methods
	Search method helpers for the method Search()


	===================================================*/
	public static int bfs(state start, char visualize) {
		Queue<state> bfs_queue = new LinkedList<state>();
		bfs_queue.offer(start);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		while (!bfs_queue.isEmpty()) {
			state current = bfs_queue.poll();
			String grid_shape = current.toString();
			if (is_target(current)) {

				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}
			if (vis.contains(grid_shape))
				continue;
			vis.add(grid_shape);
			nodes.add( current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				
				display(current.grid);
			}
			// moves
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (state s : new_states)
				bfs_queue.offer(s);
		}
		return -1;
	}
	
	public static int dfs(state start, char visualize) {
		
		Stack<state> dfs_stack = new Stack<state>();
		dfs_stack.push(start);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		while (!dfs_stack.isEmpty()) {
			state current = (state) dfs_stack.pop();
			String grid_shape = current.toString();	
			
			if (is_target(current)) {
				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}
			if (vis.contains(grid_shape))
				continue;

			nodes.add(current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				display(current.grid);
			}
			vis.add(grid_shape);
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (int i = new_states.size()-1; i >= 0; i--)
				dfs_stack.push(new_states.get(i));
		}
		
		return -1;
		
	}
	
	public static int uniform(state start , char visualize) {
		
		ArrayList<state> uniformQ = new ArrayList<state>();
		uniformQ.add(start);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		while (!uniformQ.isEmpty()) {
			//get the index of the state with minimum cost
			int minCost = 0;
			for (int i = 1;i < uniformQ.size() ; i++){
				if(uniformQ.get(i).cost < minCost)
					minCost = i;
			}
				
			state current = (state) uniformQ.get(minCost);
			uniformQ.remove(minCost);
			
			String grid_shape = current.toString();
			//System.out.println(grid_shape);
			
			if (is_target(current)) {

				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}
			if (vis.contains(grid_shape))
				continue;

			nodes.add(current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				display(current.grid);
			}
			vis.add(grid_shape);
			// moves
			ArrayList<state> new_states = move(current);
			//System.out.println("new states " + new_states.size());
			for (state s : new_states)
				uniformQ.add(s);
		}
		
		return -1;
		
	}
	
	
	public static int iterative(state start , char visualize) {
		int curMaxLvl = 0;
		
		Stack<state> dfs_stack = new Stack<state>();
		dfs_stack.push(start);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		ArrayList<state> new_states = new ArrayList<state>();
		
		boolean maxLvlReached = false;

		while (true) {
			state current = (state) dfs_stack.pop();
			String grid_shape = current.toString();
			
			boolean dontExpand = false;
			
			if(current.lvl == curMaxLvl){
				dontExpand = true;
				maxLvlReached = true;
			}
			
			if (is_target(current)) {

				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}
			
			if (vis.contains(grid_shape))
				dontExpand = true;
			vis.add(grid_shape);

			nodes.add(current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				display(current.grid);
			}
			
			if(!dontExpand){
				new_states = move(current);
				for (state s : new_states)
					dfs_stack.push(s);
			}
			
			if(dfs_stack.isEmpty()){
				if(!maxLvlReached)
					return -1;
				maxLvlReached = false;
				dfs_stack = new Stack<state>();
				dfs_stack.push(start);
				vis = new HashSet<String>();
				curMaxLvl++;
			}
		}
	}
	
	public static int greedy(state state, String heuristic, char visualize){
		
		ArrayList<state> priorityQueue = new ArrayList<state>();
		if (heuristic.equals("GR1")) {
		state.heuristic = heurstic1(state.grid);
		}
		if (heuristic.equals("GR2")) {
			state.heuristic = heurstic2(state.grid);
		}
		priorityQueue.add(state);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		while(!priorityQueue.isEmpty()){
			int currentIndex = bestOptionGreedy(priorityQueue);
			state current = priorityQueue.get(currentIndex);
			priorityQueue.remove(currentIndex);
			
			if(is_target(current)){

				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}
			String gridShape = current.toString();
			if (vis.contains(gridShape))
				continue;

			nodes.add(current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				System.out.println("Heuristic:  "  + current.heuristic);
				display(current.grid);
			}
			vis.add(gridShape);
			ArrayList<state> new_states = move(current);
			for (state s : new_states){
				if (heuristic.equals("GR1")) {
					s.heuristic = heurstic1(s.grid);
					}
				if (heuristic.equals("GR2")) {
					s.heuristic = heurstic2(s.grid);
					}
				priorityQueue.add(s);
			}
		}
		
		
		return -1;
	}
	
	
	
	public static int aStar(state state, String heuristic, char visualize){
		
		ArrayList<state> priorityQueue = new ArrayList<state>();
		if (heuristic.equals("AS1")) {
		state.heuristic = heurstic1(state.grid);
		}
		if (heuristic.equals("AS2")) {
			state.heuristic = heurstic2(state.grid);
		}
		priorityQueue.add(state);
		HashSet<String> vis = new HashSet<String>();
		ArrayList<String> nodes = new ArrayList<String>();
		while(!priorityQueue.isEmpty()){
			int currentIndex = bestOptionAStar(priorityQueue);
			state current = priorityQueue.get(currentIndex);
			priorityQueue.remove(currentIndex);
			;
			if(is_target(current)){

				System.out.println("*****************GOAL****************");
				System.out.println(current.nodeNumber);
				display(current.grid);
				nodes.add(current.nodeNumber+"");
				displayNodes(nodes);
				System.out.println("*****************GOAL****************");
				System.out.println("*****************Cost****************");
				System.out.println("Total Cost: " + current.cost);
				System.out.println("*************************************");
				return current.cost;
			}

			String gridShape = current.toString();
			if (vis.contains(gridShape))
				continue;
			nodes.add(current.nodeNumber + "");
			if (visualize == 't') {
				System.out.println("*********************************************");
				System.out.println(current.nodeNumber);
				System.out.println("Node Number" +current.nodeNumber);
				System.out.println("current cost" +current.cost);
				System.out.println("Heuristic:  "  + current.heuristic);
				display(current.grid);
			}
			vis.add(gridShape);
			ArrayList<state> new_states = move(current);
			for (state s : new_states){
				if (heuristic.equals("AS1")) {
					s.heuristic = heurstic1(s.grid);
					}
				if (heuristic.equals("AS2")) {
					s.heuristic = heurstic2(s.grid);
					}
				priorityQueue.add(s);
			}
		}
		
		
		return -1;
	}
	/*====================================================
	 		Arraylist get methods for greedy and Astar
	 bestOptionGreedy(list of states):
	 Takes a list of states and returns the index of the
	 state with the least heuristic value.
	 bestOptionAstar(list of states):
	 Takes a list of states and returns the index of the
	 state with the least heuristic value + Cost value.
	 =====================================================*/
	public static int bestOptionGreedy(ArrayList<state> list){
		int shortestHeuristic = list.get(0).heuristic;
		int locationInList = 0;
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).heuristic < shortestHeuristic){
				shortestHeuristic = list.get(i).heuristic;
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
				locationInList = i;
			}
		}
		return locationInList;
	}
	
	
	/*========================================
					Heuristics
	heurstic1(grid):
	passes through the grid, captures one robot
	part and makes all the others move towards 
	it while keeping count of the difference 
	between the (x,y).
	heuristic2(grid):
	passes through the grid, captures one robot
	part and makes all the others move towards 
	it, however here it tries to get the 
	distance between the robot parts using the 
	virtual straight path.

	===========================================*/
	
	
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
					dx = ii;
					dy = jj;
						
				}
			}
		}
		return retVal;

		}
		

	public static int heurstic2(char [][] grid){
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
					retVal = Math.max( Math.abs(dx - ii ) , Math.abs(dy -jj)) + retVal;

						
				}
			}
		}
		return retVal;
		
	}

	
	
	
	
	
	
	
	/*================================================
						GenGrid()
	Purpose: generates a random grid, places random
	number of robot parts and random number of 
	obstacles in random locations.
	Usage:
	    Grid x = GenGrid();
		Grid y = GenGrid("-v");
		Grid z = GenGrid("-vv");
    passing no variables will not show any info about
    the grid in the console.
    -v option verbose prints the grid 
    -vv veryverbose option prints the grid and all 
        accomapnied information 
	
	================================================*/
	public static Grid GenGrid(){
		Grid randomGrid = new Grid();
		return randomGrid;
	}
	public static Grid GenGrid(String verbose){
		Grid randomGrid = new Grid();
		if (verbose.equals("-v")){
			System.out.println();
			System.out.println("*******Random Grid*******");
			display(randomGrid.grid);
			System.out.println("*******End Random Grid*******");
		}
		else{
			if (verbose.equals("-vv")){
				System.out.println();
				System.out.println("*******Random Grid*******");
				display(randomGrid.grid);
				System.out.println("*******Number of Parts*******");
				System.out.println(randomGrid.numberOfParts);
				System.out.println("*******Number of Obstacles*******");
				System.out.println(randomGrid.numberOfObstacles);
				System.out.println("*******Grid Size*******");
				System.out.println(randomGrid.length + " x " + randomGrid.width);
				System.out.println("*******End Random Grid*******");
				
			}
			else{
				System.out.println("Invalid option for GenGrid()");
			}
		}
		
		
		return randomGrid;
	}
	
	/*================================================
	Search(Grid , Strategy , Visualize)
	Purpose: Searches through the given grid using the
	passed strategy.
	Usage:
 	Search(grid, {"BF|DF|ID|AS1|AS2|GR1|GR2"},{'t|f'})
 	
	
	================================================*/
	public static void Search(Grid grid, String Strategy, char visualize){
		if (Strategy.equals("BF")){
			state start = new state(grid.grid, 0, 0);
			int ret = bfs(start,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("DF")){
			state start = new state(grid.grid, 0, 0);
			int ret = dfs(start,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("ID")){
			state start = new state(grid.grid, 0, 0);
			int ret = iterative(start,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("GR1")){
			state start = new state(grid.grid, 0, 0);
			int ret = greedy(start,Strategy,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("GR2")){
			state start = new state(grid.grid, 0, 0);
			int ret = greedy(start,Strategy,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("AS1")){
			state start = new state(grid.grid, 0, 0);
			int ret = aStar(start,Strategy,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
		if (Strategy.equals("AS2")){
			state start = new state(grid.grid, 0, 0);
			int ret = aStar(start,Strategy,visualize);
			if (ret == -1){
				System.out.println("No Solution found");
			}
		}
	}

	public static void main(String[] args) throws Exception {

		Grid z = GenGrid("-vv");
		Search(z,"AS2",'t');
//		display(z.grid);


	}
}
