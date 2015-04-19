import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Knapsack {

	private int numOfItems;
	private int totalWeight;
	private Items[] items;
	private int A[][];
	private static final String inputFile = "knapsack1.txt";
	
	private void readfFromFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			try {
				String line = br.readLine();
				totalWeight = Integer.parseInt(line.split(" ")[0]);
				numOfItems = Integer.parseInt(line.split(" ")[1]);
				
				//init Array with total numOfItems
				items = new Items[numOfItems + 1];
				items[0] = null;
				
				//init optimal solution
				A = new int[numOfItems + 1][totalWeight + 1];
				
				line = br.readLine();
				int count = 0;
				while(line != null) {
					int value = Integer.parseInt(line.split(" ")[0]);
					int weight = Integer.parseInt(line.split(" ")[1]);
					items[++count] = new Items(value, weight);
					line = br.readLine();
				}
				
				System.out.println("Total Size of Items :" + items.length + "::" + items[100].weight );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Optimal value Knapsack.
	 */
	private void calculateKnapsack() {
		
		for (int i = 0; i <= totalWeight; i++) {
			A[0][i] = 0;
		}
		
		for (int i = 1; i <= numOfItems; i++) {
			Items item = items[i];
			int weight = item.weight;
			int value = item.value;
			for (int j = 0; j <= totalWeight; j++) {
				if(weight <= j) {
					A[i][j] = Math.max(A[i-1][j], A[i-1][j-weight] + value);
				} else {
					A[i][j] = A[i-1][j];
				}
			}
		}
		
		System.out.println("Optimal Value ::" + A[numOfItems][totalWeight]);
	}
	
	public static void main(String args[]) {
		Knapsack k = new Knapsack();
		k.readfFromFile();
		k.calculateKnapsack();
	}
}
