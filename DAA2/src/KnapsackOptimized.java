import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class KnapsackOptimized {

  private int numOfItems;
  private int totalWeight;
  private Items[] items;
  private ArrayList<HashMap<Integer,Integer>> a;
  private static final String inputFile = "knapsack_big.txt";
  
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
        
        a = new ArrayList<HashMap<Integer,Integer>>(numOfItems + 1);
        
        for (int i = 0; i <= numOfItems; i++) {
          a.add(new HashMap<Integer, Integer>());
        }
        
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
   * Calculate Knapsack
   * 
   * @param n NumOfNodes
   * @param w Total Weight
   * @return Optimized Result
   */
  
  private int knapsack(int n, int w) {
    //Memoized
    if(a.get(n).containsKey(w)) {
      return a.get(n).get(w);
    }
 
    if(n < 1) {
      return 0;
    }
    
    int currWeight = items[n].weight;
    int currValue = items[n].value;
    
    int result;
    
    if(currWeight > w) {
      result = knapsack(n - 1, w);
    } else {
      result = Math.max(knapsack(n - 1, w), knapsack(n -1, w - currWeight) + currValue );
    }
    a.get(n).put(w, result);
    return result;
  }
  
  public static void main(String args[]) {
	KnapsackOptimized k = new KnapsackOptimized();
    k.readfFromFile();
    int result = k.knapsack(k.numOfItems, k.totalWeight );
    System.out.println("Result::" + result);
  }
}
