import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Clustering {

	private int numOfNodes;
	private int numOfEdges;
	private int numOfClusters;
	private ArrayList<Edge> edges;
	private Leader leaders[];
	
	private static final String inputFile = "clustering1.txt";
	//Number of clusters
	private static final int K = 4;
	
	public Clustering() {
		this.edges = new ArrayList<Edge>();
	}
	
	public void readFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			//Parse the first line (special case)
			String line;
			try {
				line = br.readLine();
				numOfNodes = Integer.parseInt(line);
				initializeLeader(numOfNodes);
				line = br.readLine();
				
				while(line != null ) {
					int fromVertex = Integer.parseInt(line.split(" ")[0]);
					int toVertex = Integer.parseInt(line.split(" ")[1]);
					int edgeCost = Integer.parseInt(line.split(" ")[2]);
					edges.add(new Edge(fromVertex,toVertex,edgeCost));
					line = br.readLine();
				}
				//Step 1 : Sort the edges in ascending order
				Collections.sort(edges);
				numOfEdges = edges.size();			
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Initialize every node in its own cluster.
	 * Set the leader to its own self
	 * @param numOfNodes
	 */
	private void initializeLeader(int numOfNodes) {
		//Vertex start from index 1 and dummy index 0
		leaders = new Leader[numOfNodes + 1];
		leaders[0] = null;
		
		numOfClusters = numOfNodes;
		
		for( int i = 1 ; i <= numOfClusters  ; i++) {
			leaders[i] = new Leader(i);
		}
		
		System.out.println("Number of Clusters before Clustering: " +  numOfClusters);
	}
	

	private void computeMaxSpacingCluster() {	
		int count = 0;
		
		while ( numOfClusters != K - 1 && count < numOfEdges) {
			Edge edge = edges.get(count++);
			
			//Find the Leader given vertex
			Leader from = find2(edge.fromVertex);
			Leader to = find2(edge.toVertex);
			
			if (from.leader != to.leader) {
				union2(from, to);
				numOfClusters--;
			}
		}
		--count;
		
		System.out.println("Number of Clusters after Clustering: " +  numOfClusters);
		System.out.println("Maximum Spacing cluster :: " + edges.get(count).edgeWeight);
		
	}
	
	/**
	 * Union the 2 clusters, by updating the leader pointer of the
	 * smaller size cluster to bigger size cluster's leader.
	 * @param fromVertex
	 * @param toVertex
	 */
	private void union(Leader from, Leader to) {
		int leaderNum = from.size >= to.size ? from.leader : to.leader;
		int size = from.size + to.size;
		int fromLeader = from.leader;
		int toLeader = to.leader;
		//Update Leader Number and size
		for (int i = 1; i < leaders.length; i++) {
			if(leaders[i].leader == fromLeader || leaders[i].leader == toLeader ) {
				leaders[i].leader = leaderNum;
				leaders[i].size = size;
			}
		}
	}
	
	public Leader find(int vertex) {
		return leaders[vertex];
	}
	
	public void union2(Leader from, Leader to) {
		Leader fromLeader = find2(from.leader);
		Leader toLeader = find2(to.leader);
		
		int fromVertex = fromLeader.leader;
		int toVertex = toLeader.leader;

		int fromSize = fromLeader.size;
		int toSize = toLeader.size;
		
		if(fromSize < toSize) {
			leaders[fromVertex].leader = toVertex;
			leaders[toVertex].size += leaders[fromVertex].size;
		} else {
			leaders[toVertex].leader = fromVertex;
			leaders[fromVertex].size += leaders[toVertex].size;
		}
	}
	
	public Leader find2(int vertex) {
		int node = vertex;
		while(vertex != leaders[vertex].leader) {
			vertex = leaders[vertex].leader;
		}
		
		//update the pointer if necessary
		while(node != leaders[node].leader) {
			int tempLeader = leaders[node].leader;
			leaders[node].leader = vertex;
			node = tempLeader;
		}
		return leaders[vertex];
	}

 	public static void main(String args[]) {
		Clustering cluster = new Clustering();
		cluster.readFromFile();
		cluster.computeMaxSpacingCluster();
	}

}
