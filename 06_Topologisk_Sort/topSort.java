import java.util.LinkedList;
import java.util.Queue;

public class topSort extends enkelGraf{
    int[] ingrader;
    public topSort(String filNavn) {
        super(filNavn);
        ingrader = new int[n];
    }

    public void findAndPrint(){
        findInDeg();
        Queue<Integer> starters = findStarters();

        int counter = 0;
        while ( (!starters.isEmpty()) && counter < n){
            int node = starters.poll();
            System.out.print(data[node] + "  ");

            for (int i = 0; i < n; i++) {
                if (nabo[node][i] && node != i) {
                    ingrader[i]--;
                    // Hvis denne naboen nå har fått inngrad lik 0, legg den til i køen starters
                    if (ingrader[i] == 0)
                        starters.offer(i);
                }
            }
            counter++;
        }

        if (counter != n ) System.out.println("\nFound a cycle!");
    }


    private void findInDeg(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (nabo[j][i] && i != j)
                    ingrader[i]++;
            }
        }
    }


    private Queue<Integer> findStarters(){
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++){
            if (ingrader[i] == 0) queue.offer(i);
        }
        return queue;
    }
}
