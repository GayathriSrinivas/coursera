import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class MaxClustering {
	private int numOfBits;
	private HashSet<Integer> nodes;
	private int K;	
	private static final String inputFile = "clustering_big.txt";
	private HashMap<Integer,Leader> leaders;
	
	public MaxClustering() {
		nodes = new HashSet<Integer>();
		leaders = new HashMap<Integer, Leader>();
	}
	
	public void readfromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			//Parse the first line (special case)
			String line;
			try {
				line = br.readLine();
				numOfBits = Integer.parseInt(line.split(" ")[1]);
				System.out.println("Number of Bits: " + numOfBits);
				
				line = br.readLine();
				
				int node;
				while(line != null) {
					line = line.replace(" ","");
					node = Integer.parseInt(line,2);
					nodes.add(node);
					leaders.put(node, new Leader(node));
					line = br.readLine();
				}
				
				System.out.println(nodes.size());
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Integer> findHammingDistance() {
		 ArrayList<Integer> oneAndTwoHammingDistance = new ArrayList<Integer>();

		 int num;
		//Compute distance of 1 and 2
		for (int i = 0; i < numOfBits; i++) {
			num = (1 << i);
			oneAndTwoHammingDistance.add(num);
			for (int j = i + 1; j < numOfBits; j++) {
				num =  (1 << i) | (1 << j); 
				oneAndTwoHammingDistance.add(num);
			}
		}
		
		System.out.println("Number of 1 & 2 Hamming distance nodes :" + oneAndTwoHammingDistance.size());
		//System.out.println(oneAndTwoHammingDistance);
		return oneAndTwoHammingDistance;
	}

	private void computeHammingDistance() {
		K = nodes.size();
		ArrayList<Integer> bitmap = findHammingDistance();
		Iterator<Integer> iter =  nodes.iterator();
		while(iter.hasNext()) {
			int p = iter.next();
			for (int i = 0; i < bitmap.size(); i++) {
				int q = p ^ bitmap.get(i);
				if(nodes.contains(q) && find(p) != find(q)) {
					//Find if they are in diff clusters
					union(leaders.get(p),leaders.get(q));
					K--;
				}
			}
		}
		System.out.println("Num of clusters: " + K);
	}
	
	/**
	 * Union of Clusters from and to
	 * @param from
	 * @param to
	 */
	public void union(Leader from, Leader to) {
		Leader fromLeader = find(from.leader);
		Leader toLeader = find(to.leader);
		
		int fromVertex = fromLeader.leader;
		int toVertex = toLeader.leader;

		int fromSize = fromLeader.size;
		int toSize = toLeader.size;
		
		if(fromSize < toSize) {
			updateUnion(fromVertex, toVertex);
		} else {
			updateUnion(toVertex, fromVertex);
		}
	}
		
	/**
	 * Find the leader of the cluster 
	 * @param vertex
	 * @return
	 */
	public Leader find(int vertex) {
		int node = vertex;
		while(vertex != leaders.get(vertex).leader) {
			vertex = leaders.get(vertex).leader;
		}	
		//update the pointer if necessary
		while(node != leaders.get(node).leader) {
			int tempLeader = leaders.get(node).leader;
			Leader l = leaders.get(node);
			l.leader = vertex;
			leaders.put(node, l);
			node = tempLeader;
		}	
		return leaders.get(vertex);
	}
	
	public void updateUnion(int fromVertex, int toVertex) {
		Leader l1 = leaders.get(fromVertex);
		l1.leader = toVertex;
		leaders.put(fromVertex, l1);
		
		Leader l2 = leaders.get(toVertex);
		l2.size = leaders.get(toVertex).size +  l1.size;
		leaders.put(toVertex, l2);
	}
	
	public static void main(String args[]) {
		MaxClustering clustering = new MaxClustering();
		clustering.readfromFile();
		clustering.computeHammingDistance();
	}
}
