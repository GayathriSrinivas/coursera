
public class JobScore implements Comparable<JobScore>{
	private Double weight;
	private Double length;
	private Double score;
	
	public Double getWeight() {
		return weight;
	}

	public Double getLength() {
		return length;
	}

	public Double getScore() {
		return score;
	}
	
	public Double computeScoreByDifference(Double weight, Double length) {
		return weight - length;
	}
	
	public Double computeScoreByRatio(Double weight, Double length) {
		return weight/length;
	}

	public JobScore(Double weight, Double length, Double algoNum) {
		this.weight = weight;
		this.length = length;
		if(algoNum == 1.0) {
			this.score = computeScoreByDifference(weight, length);
		} else {
			this.score = computeScoreByRatio(weight, length);
		}
	}
		
	@Override
	public int compareTo(JobScore o) {
		int result = o.score.compareTo(this.score);
		if (result == 0) {
			result = o.weight.compareTo(this.weight);
		}
		return result;
	}
}
