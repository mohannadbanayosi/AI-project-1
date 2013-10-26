import java.util.ArrayList;




public class Grid {
	
	public char[][] grid;
	public int length;
	public int width;
	public int numberOfParts;
	public int numberOfObstacles;
	public ArrayList<String> partsLocations;
	public ArrayList<String> obstaclesLocations;
	
	public Grid(){
		length = (int)((Math.random() * 6)+3) ;
		width = (int)((Math.random() * 6)+3) ;
		System.out.println("n" + length+", " +"p"+ width);
		numberOfObstacles = ((int) (Math.random() * 5)) +1;
		obstaclesLocations = new ArrayList<String>();
		numberOfParts = ((int) (Math.random() * 5))+1;
		partsLocations = new ArrayList<String>();
		//System.out.println("n" + numberOfObstacles+", " +"p"+ numberOfParts);
		generateParts();
		generateObstacles();
		generateGridSize();
		
	}
	public Grid(String gridSize){
		
	}
	
	public void generateGridSize(){
		
		//System.out.println("lne"+length+"dasdasd  " +width);
		grid = new char [length][width];
		for (int i=0;i < length;i++){
			for (int j =0; j < width ; j++){
				grid[i][j] = '.';
			}
		}
		for (int i = 0;i < partsLocations.size(); i++){
			grid[Integer.parseInt(partsLocations.get(i).split(",")[0])][Integer.parseInt(partsLocations.get(i).split(",")[1])] = 'r';
		}
		for (int i = 0;i < obstaclesLocations.size(); i++){
			grid[Integer.parseInt(obstaclesLocations.get(i).split(",")[0])][Integer.parseInt(obstaclesLocations.get(i).split(",")[1])] = '#';
		}
	}
	
	public void generateParts(){
		
		int dx, dy;
		String position;
		int i =0;
		while ( i < numberOfParts){
		    dx = (int) ((Math.random() * length-1)+1) ;
			dy = (int) ((Math.random() * width-1)+1) ;
			
			position = dx + "," + dy;
			//System.out.println(position);
			if (checkIsLocationEmpty(position) == 0){
				partsLocations.add(position);
				i++;
			}
			
		}
	}
	
	public void generateObstacles(){
		
		int dx, dy;
		String position;
		int i = 0;
		while ( i < numberOfObstacles){
		    dx = (int)((Math.random() * length-1)+1);
			dy = (int) ((Math.random() * width-1)+1);
			position = dx + "," + dy;
			if (checkIsLocationEmpty(position) == 0){
				obstaclesLocations.add(position);
				i++;
			}
			
		}
	}
	
	public int checkIsLocationEmpty(String location){
		for (int i = 0 ;i < partsLocations.size(); i++){
			if (partsLocations.get(i).equals(location)){
				return -1;
			}
		}
		for (int i = 0 ;i < obstaclesLocations.size(); i++){
			if (obstaclesLocations.get(i).equals(location)){
				return -1;
			}
		}
		return 0;
	}
	public void display(){
		for (int i =0 ; i < grid.length; i++){
			System.out.println();
			for (int j = 0; j < grid[i].length; j++){
				System.out.print(grid[i][j]);
			}
		}
	}
}
