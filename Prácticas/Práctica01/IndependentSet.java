import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class IndependentSet{

    private Random random;
    private int vertices;
    private int[][] matrix;
    private int k;
    private Graph graph;

    public IndependentSet() {
        graph = new SingleGraph("Independent Set");
        random =  new Random();
        vertices = random.nextInt(10);
        if(vertices < 5)
        	vertices = 5;
        for (int i = 0; i < vertices; i++)
            graph.addNode(Integer.toString(i));

        k = random.nextInt(vertices)+1;
        matrix = new int[vertices][vertices];

        for(int i = 0; i < vertices; i++)
            for(int j = i; j < vertices; j++)
                if(i == j) {
                    matrix[i][j] = 0;
                } else {
                    if(random.nextInt(10) < 5) {
                        matrix[i][j] = 1;
                        matrix[j][i] = 1;
                        graph.addEdge(i+"-"+j,i,j);
                    } else {
                        matrix[i][j] = 0;
                        matrix[j][i] = 0;
                    }
                }
    }

    private ArrayList<Integer> neighbors(int i) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        
        for(int k = 0; k < vertices; k++)
            if(this.matrix[i][k] == 1 && this.matrix[k][i] == 1)
                neighbors.add(k);

        return neighbors;
    }

    private ArrayList<Integer> createIndependient() {
        ArrayList<Integer> solution = new ArrayList<>();
        ArrayList<Integer> aux = new ArrayList<>();
        
        for(int i = 0; i < this.vertices; i++)
            aux.add(i);

        while(!aux.isEmpty()) {
            int i = random.nextInt(aux.size());
            ArrayList<Integer> neighbors = neighbors(aux.get(i));
            solution.add(aux.get(i));
            neighbors.add(aux.get(i));
            aux.removeAll(neighbors);
        }
        
        return solution;
    }

    public static void main(String[] args) {
        IndependentSet is = new IndependentSet();

        ArrayList<Integer> solution = is.createIndependient();
        for (int sol:solution)
            is.graph.getNode(Integer.toString(sol))
                .addAttribute("ui.style", "fill-color: purple;");
        
        is.graph.display();
    }

}
