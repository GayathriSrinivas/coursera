
public class EdgeEntry implements Comparable<EdgeEntry>{

	public Integer edgeCost;
	public Integer toVertex;
	
	public EdgeEntry(int edgeCost, int toVertex) {
		this.edgeCost = edgeCost;
		this.toVertex = toVertex;
	}
	
	@Override
	public int compareTo(EdgeEntry o) {
		int value = this.edgeCost.compareTo(o.edgeCost);
		if(value == 0) {
			value = this.toVertex.compareTo(o.toVertex);	
			if(value == 0) {
				value = +1;
			}
		}
		return value;
	}
	
}
