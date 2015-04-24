import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Problem : In this assignment you will implement one or more algorithms for the all-pairs shortest-path problem. 
 * Here are data files describing three graphs: graph #1; graph #2; graph #3.
 * The first line indicates the number of vertices and edges, respectively. 
 * Each subsequent line describes an edge (the first two numbers are its tail and head, respectively) and its length (the third number). 
 * NOTE: some of the edge lengths are negative. NOTE: These graphs may or may not have negative-cost cycles.
 * 
 * Your task is to compute the "shortest shortest path". Precisely, you must first identify which, if any, of the 
 * three graphs have no negative cycles. For each such graph, you should compute all-pairs shortest paths and remember the
 * smallest one (i.e., compute minu,v∈Vd(u,v), where d(u,v) denotes the shortest-path distance from u to v).
 * If each of the three graphs has a negative-cost cycle, then enter "NULL" in the box below. 
 * If exactly one graph has no negative-cost cycles, then enter the length of its shortest shortest path in the box below. 
 * If two or more of the graphs have no negative-cost cycles, then enter the smallest of the lengths of their shortest shortest paths
 * in the box below.
 * 
 * @author Gayathri Srinivasan
 */
public class AllPairShortestPath {

	private int numOfNodes;
	private int numOfEdges;
	private ArrayList<HashMap<Integer,Integer>> graph;
	private int A[][][];
	
	private static final String inputFile = "g3.txt";
	
	private void readfromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			try {
				String line = br.readLine();
				numOfNodes = Integer.parseInt(line.split(" ")[0]);
				numOfEdges = Integer.parseInt(line.split(" ")[1]);
				
				graph = new ArrayList<HashMap<Integer,Integer>>(numOfNodes + 1);
				graph.add(null);
				
				System.out.println("Before:");
				// 3D Array
				A  = new int [numOfNodes + 1][numOfNodes + 1][2];
				System.out.println("After::");
				
				for (int i = 1; i < numOfNodes + 1; i++) {
					graph.add(new HashMap<Integer,Integer>());
				}
				
				line = br.readLine();
				
				while(line != null) {
					int fromVertex = Integer.parseInt(line.split(" ")[0]);
					int toVertex = Integer.parseInt(line.split(" ")[1]);
					int edgeCost = Integer.parseInt(line.split(" ")[2]);
					graph.get(fromVertex).put(toVertex, edgeCost);
					line = br.readLine();
				}
				
				System.out.println("Graph Size:: " +  graph.size() + " Graph 0 Size:: " + graph.get(1).size() + "A Lenght: " + A.length);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initFloydsWarshall() {
		for (int i = 1; i < A.length; i++) {
			for (int j = 1; j < A.length; j++) {
				if( i == j) {
					A[i][j][0] = 0;
				} else if( graph.get(i).containsKey(j)) {
					A[i][j][0] = graph.get(i).get(j);
				} else {
					A[i][j][0] = Integer.MAX_VALUE;
				}
			}
		}
	}
	
	private void swap() {
		for (int i = 1; i < A.length; i++) {
			for (int j = 1; j < A.length; j++) {
				A[i][j][0] = A[i][j][1];
			}
		}
		
	}
	
	private void computeFloydsWarshall() {
		//One time
		initFloydsWarshall();
		
		//free Graph memory
		graph = null;
		
		for (int k = 1; k < A.length; k++) {
			for (int i = 1; i < A.length; i++) {
				for (int j = 1; j < A.length; j++) {
					if(A[i][k][0] == Integer.MAX_VALUE || A[k][j][0] == Integer.MAX_VALUE ) {
						A[i][j][1] = Math.min( A[i][j][0], Integer.MAX_VALUE);
					} else {
						A[i][j][1] = Math.min( A[i][j][0], A[i][k][0] + A[k][j][0]);
					}
				}
			}
			//Swap with previous iteration
			swap();
		}
	}
	
	private void print() {
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < A.length; i++) {
			for (int j = 1; j < A.length; j++) {
				if(min > A[i][j][1]) {
					min = A[i][j][1];
				}
			}
		}
		System.out.println(min);
	}
	
	/**
	 * Check the Diagonal of the matrix to check if has
	 * cycles. L(i,i,N) has any cycles.
	 */
	private void checkForCycles() {
		for (int i = 1; i < A.length; i++) {
			if( A[i][i][1] < 0) {
				System.out.println("System has cycles");
				return;
			}
		}
	}
	
	public static void main(String args[]) {
		AllPairShortestPath apsp = new AllPairShortestPath();
		apsp.readfromFile();
		apsp.computeFloydsWarshall();
		apsp.print();
		apsp.checkForCycles();
		System.out.println("Done!");
	}
}
