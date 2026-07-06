import java.util.*;

public class LexBFS {
    
    static int n = 5;
    static boolean[][] adj = {
        {false, true,  true,  false, false},
        {true,  false, true,  true,  false},
        {true,  true,  false, true,  true },
        {false, true,  true,  false, true },
        {false, false, true,  true,  false}
    };

    public static void main(String[] args) {
        System.out.println("Graph mit " + n + " Knoten");
        
        int[] order = lexBFS();
        System.out.println("LexBFS Reihenfolge:");
        for (int i = 0; i < n; i++) {
            System.out.print("v" + (order[i]+1) + " ");
        }
        
        boolean isChordal = isChordal(order);
        System.out.println("\nIst der Graph chordal? " + 
            (isChordal ? "JA" : "NEIN"));
    }

    static int[] lexBFS() {
        List<List<Integer>> partition = new ArrayList<>();
        List<Integer> all = new ArrayList<>();
        for (int i = 0; i < n; i++) all.add(i);
        partition.add(all);
        
        int[] order = new int[n];
        boolean[] visited = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            int v = partition.get(0).remove(0);
            if (partition.get(0).isEmpty()) partition.remove(0);
            order[i] = v;
            visited[v] = true;
            
            List<List<Integer>> newPartition = new ArrayList<>();
            for (List<Integer> group : partition) {
                List<Integer> neighbors = new ArrayList<>();
                List<Integer> nonNeighbors = new ArrayList<>();
                for (int u : group) {
                    if (!visited[u]) {
                        if (adj[v][u]) neighbors.add(u);
                        else nonNeighbors.add(u);
                    }
                }
                if (!neighbors.isEmpty()) newPartition.add(neighbors);
                if (!nonNeighbors.isEmpty()) newPartition.add(nonNeighbors);
            }
            partition = newPartition;
        }
        return order;
    }

    static boolean isChordal(int[] order) {
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) pos[order[i]] = i;
        
        for (int i = 0; i < n; i++) {
            int v = order[i];
            List<Integer> rightNeighbors = new ArrayList<>();
            for (int u = 0; u < n; u++) {
                if (adj[v][u] && pos[u] > i) rightNeighbors.add(u);
            }
            if (!rightNeighbors.isEmpty()) {
                int w = rightNeighbors.stream()
                    .min(Comparator.comparingInt(x -> pos[x])).get();
                for (int u : rightNeighbors) {
                    if (u != w && !adj[w][u]) return false;
                }
            }
        }
        return true;
    }
}
