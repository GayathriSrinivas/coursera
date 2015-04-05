import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


public class MinimumSpanningTree {
	private int numOfNodes;
	private int numOfEdges;
	private int startVertex;
	private String inputFile;
	ArrayList<TreeSet<EdgeEntry>> graph;
	HashSet<Integer> visisted =  new HashSet<Integer>();	
	HashSet<Integer> unVisisted =  new HashSet<Integer>();
	
	public MinimumSpanningTree() {
		this.inputFile = "edges.txt";
	}
	
	public void readFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			//Parse the first line (special case)
			String line = br.readLine();
			numOfNodes = Integer.parseInt(line.split(" ")[0]);
			numOfEdges = Integer.parseInt(line.split(" ")[1]);
			
			//Array list set the capacity if known prior
			graph = new ArrayList<TreeSet<EdgeEntry>>(numOfNodes + 1);
			//ignore zero index values
			graph.add(null);
			
			for (int i = 1; i < numOfNodes + 1; i++) {
				graph.add(new TreeSet<EdgeEntry>());
			}
			
			//Parse the rest of the file and construct the graph
			line = br.readLine();
			startVertex = Integer.parseInt(line.split(" ")[0]);
			
			while(line != null ) {
				int fromVertex = Integer.parseInt(line.split(" ")[0]);
				int toVertex = Integer.parseInt(line.split(" ")[1]);
				int edgeCost = Integer.parseInt(line.split(" ")[2]);
				
				//Undirected graph
				graph.get(fromVertex).add(new EdgeEntry(edgeCost, toVertex));
				graph.get(toVertex).add(new EdgeEntry(edgeCost, fromVertex));
				
				unVisisted.add(fromVertex);
				unVisisted.add(toVertex);
				//Read next line of file
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printGraph() {
		System.out.println("Total # of Vertices " + graph.size());
		for (int i = 1; i < 5; i++) {
			System.out.print("Index ::" + i + "........");
			Iterator<EdgeEntry> edgeEntrySet = graph.get(i).iterator();
			while(edgeEntrySet.hasNext()) {
				EdgeEntry e = edgeEntrySet.next();
				System.out.print(e.toVertex + ":" + e.edgeCost + ",");
			}
			System.out.println("");
		}
	}
	
	public void computeVisited(int vertex) {
		visisted.add(vertex);
		unVisisted.remove(vertex);
		//System.out.println("Visisted Size ::" + visisted.size() + " Unvisited Size::" + unVisisted.size());
	}
	
	public void computeMST() {

		computeVisited(startVertex);
		long totalMSTDistance = 0;
		
		while(visisted.size() < numOfNodes ) {
			Iterator<Integer> visitedList = visisted.iterator();
			
			//init for every iteration
			boolean isMinDistanceSet = false;
			int minDistance = numOfEdges;
			int minVertex = 0;
			
			//For every vertex in visited
			while(visitedList.hasNext()) {
				Integer visitedVertex = visitedList.next();
				Iterator<EdgeEntry> treeSetList = graph.get(visitedVertex).iterator();
				
				while(treeSetList.hasNext()) {
					EdgeEntry entry = treeSetList.next();
					if(unVisisted.contains(entry.toVertex)) {
						int distance = entry.edgeCost;
						int toVertex = entry.toVertex;
						if(!isMinDistanceSet) {
							minDistance = distance;
							minVertex = toVertex;
							isMinDistanceSet = true;
						}
						if (distance < minDistance && isMinDistanceSet) {
							minVertex = toVertex;
							minDistance = distance;
						}
					}
				}
			}
			totalMSTDistance += minDistance;
			computeVisited(minVertex);
		}
		System.out.println(totalMSTDistance);
	}
	
	public static void main(String args[]) {
		MinimumSpanningTree mst = new MinimumSpanningTree();
		mst.readFromFile();
		mst.printGraph();
		long start = System.currentTimeMillis();
		mst.computeMST();
		System.out.println("Time Taken " + (System.currentTimeMillis() - start) + " Milli seconds");
	}
}
