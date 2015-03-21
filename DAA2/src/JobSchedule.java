import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class JobSchedule {

	private int numOfJobs;
	private long sumOfCompletionTime = 0;
	private JobScore[] jobs;
	
	public void greedyJobScheduler(){
		BufferedReader br;
	    try {
	    	br = new BufferedReader(new FileReader("jobs.txt"));
	        String line = br.readLine();
	        numOfJobs = Integer.parseInt(line);
	        jobs = new JobScore[numOfJobs];
	        int count = 0;
	        line = br.readLine();
	        while (line != null) {
	            String[] parameters  = line.split(" ");
	            Double weight = Double.parseDouble(parameters[0]);
	            Double length = Double.parseDouble(parameters[1]);
	            jobs[count++] = new JobScore(weight, length, 2.0);
	            line = br.readLine();
	        }
	    } catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    
		double cumulativeLength = 0;
		
		Arrays.sort(jobs);
		
		for (JobScore job : jobs) {
			cumulativeLength += job.getLength();
			sumOfCompletionTime += cumulativeLength * job.getWeight();
		}
		System.out.println(sumOfCompletionTime);
	}
		
	public static void main(String args[]) {
		JobSchedule js = new JobSchedule();
		js.greedyJobScheduler();
		// This can also be done in bash as follows:
		// cat jobs.txt | sed '1d' | awk '{ print $1,$2,$1-$2 }' | sort -k3 -k1 -rn | awk '{ l+=$2; sum+=l*$1; } END { printf("%.20G\n", sum) }'
	}
	
}
