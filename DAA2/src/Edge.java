
public class Edge implements Comparable<Edge> {
	public Integer edgeWeight;
	public int fromVertex;
	public int toVertex;
	
	public Edge(int fromVertex,int toVertex, int edgeWeight) {
		this.edgeWeight = edgeWeight;
		this.fromVertex = fromVertex;
		this.toVertex  = toVertex;
	}
	
	@Override
	public int compareTo(Edge o) {
		return this.edgeWeight.compareTo(o.edgeWeight);
	}
}



