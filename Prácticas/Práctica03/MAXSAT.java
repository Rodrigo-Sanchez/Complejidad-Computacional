import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MAXSAT {
    
    public static void main(String args[]) {
        System.out.println("Algoritmo Genético para MAXSAT.\n");

        int[][] initialPopulation = createPopulation();
        ArrayList<Integer> array[] = createLists(initialPopulation[0].length);
        for(int i = 0; i < initialPopulation.length; i++) {
            for(int j = 0; j < initialPopulation[0].length; j++) {
                array[i].add(initialPopulation[i][j]);
            }
        }
        System.out.println(Arrays.deepToString(array));
        //printMatrix(initialPopulation);
        //printClausules(initialPopulation);
    }

    public static int[][] createPopulation() {
        // Random random = new Random();
        int minClausule = 50;
        int maxClausule = 60;
        int minVariable = 3;
        int maxVariable = 5;

        // Cantidad de cláusulas.
        int m = ThreadLocalRandom.current().nextInt(minClausule, maxClausule+1);

        // Cantidad de variables.
        int n = ThreadLocalRandom.current().nextInt(100)+1;

        int[][] population = new int[m][n];

        // Entero para contar las variables que vamos a generar en una cláusula.
        int variables;
        // En este arreglo vamos a guardar las variables declaradas arriba.
        ArrayList<Integer> positions = new ArrayList<Integer>();

        // A cada cláusula le agregamos de 3 a 5 variables.
        for(int i = 0; i < population.length; i++) {

            // Creamos el random para validar la cardinalidad de las variables.
            variables = ThreadLocalRandom.current().nextInt(minVariable, maxVariable+1);
            
            for(int v = 0; v < variables; v++) {
                // Aquí agregamos las posiciones de las variables de la cláusula.
                positions.add(ThreadLocalRandom.current().nextInt(n)+1);
            }

            for(int j = 0; j < population[0].length; j++) {
                // Si está la variable, agregamos un 1, en otro caso un 0.
                population[i][j] = positions.contains(j) ? j+1 : -(j+1);
                //population[i][j] = positions.contains(j) ? 1 : 0;
            }

            // Al finalizar esta iteración limpiamos el arreglo donde guardamos las posiciones.
            positions.clear();
        }

        return population;
    }

    public static void printMatrix(int[][] matrix) {
        Arrays.stream(matrix)
        .forEach(
            (row) -> {
                System.out.print("[");
                Arrays.stream(row)
                .forEach((element) -> System.out.print(" " + element + " "));
                System.out.println("]");
            }
        );
    }

    public static void printClausules(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            // System.out.println("Cláusula "+i+": ");
            System.out.print("(");
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] > 0) {
                    System.out.print("x" + matrix[i][j] + " ∨ ");
                } else {
                    System.out.print("¬x" + Math.abs(matrix[i][j]) + " ∨ ");
                }
            }
            System.out.println(") ∧ ");
        }
        // Arrays.stream(matrix)
        // .forEach(
        //     (row) -> {
        //         System.out.print("Cláusula: ");
        //         Arrays.stream(row)
        //         .forEach(
        //             (element) -> {
        //                 if(element > 0) 
        //                     System.out.print("x" + element + " ");
        //             }
        //         );
        //         System.out.println();
        //     }
        // );
    }

    /**
     * Crea t arreglos de enteros.
     * @param t numero de arreglos a crear.
     * @return Lista de arreglos.
     */
    public static ArrayList<Integer>[] createLists(int t) {
        ArrayList<Integer> list[] = new ArrayList[t];
        for(int i = 0; i < t; i++) {
            list[i] = new ArrayList<Integer>();
        }
        return list;
    }

}