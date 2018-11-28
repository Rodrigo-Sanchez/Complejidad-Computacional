/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author rodd
 */
public class MAXSAT {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.out.println("Algoritmo Genético para MAXSAT.\n");

        Clausule clausule = new Clausule();
        TruthAssignment truthAssignment = new TruthAssignment();

        int[][] initialPopulation = createPopulation();
        ArrayList<Clausule>[] clausules = clausule.createClausules(initialPopulation);
        System.out.println(clausule.toString(clausules));

        // Imprimimos 
        //System.out.println(Arrays.deepToString(array));
        
        // printMatrix(initialPopulation);
        Clausule.printClausules(initialPopulation);
        
        ArrayList<Integer> assignment = truthAssignment.create(initialPopulation[0].length);
        System.out.println(truthAssignment.toString());

        ArrayList<Clausule>[] resultTruthAssignment;
        //System.out.println("Asignación de verdad de las variables: " + Arrays.deepToString(resultTruthAssignment));
        resultTruthAssignment = TruthAssignment.evaluate(clausules, assignment);

        //printClausules(resultTruthAssignment);

        //int fitness = fitness(resultTruthAssignment);
        //System.out.println("El fitness de esta población es: "+fitness);
    }


    public static int[][] createPopulation() {
        int minClausule = 50;
        int maxClausule = 60;
        int minVariable = 3;
        int maxVariable = 5;

        // Cantidad de cláusulas.
        int m = ThreadLocalRandom.current().nextInt(minClausule, maxClausule+1);

        // Cantidad de variables.
        int n = ThreadLocalRandom.current().nextInt(100)+1;

        // Random para determinar si es positivo o negativo.
        int paridad;

        int[][] population = new int[m][n];

        // Entero para contar las variables que vamos a generar en una cláusula.
        int variables;
        // En este arreglo vamos a guardar las variables declaradas arriba.
        ArrayList<Integer> positions = new ArrayList<>();

        // A cada cláusula le agregamos de 3 a 5 variables.
        for (int[] pop : population) {
            // Creamos el random para validar la cardinalidad de las variables.
            variables = ThreadLocalRandom.current().nextInt(minVariable, maxVariable+1);
            for(int v = 0; v < variables; v++) {
                // Aquí agregamos las posiciones de las variables de la cláusula.
                positions.add(ThreadLocalRandom.current().nextInt(n)+1);
            }
            for (int j = 0; j < population[0].length; j++) {
                paridad = ThreadLocalRandom.current().nextInt(2);
                // Si está la variable, agregamos la posición ya sea en positivo o negativo, en otro caso un 0.
                pop[j] = positions.contains(j) ? (paridad > 0 ? j+1 : -(j+1)) : 0;
                // population[i][j] = positions.contains(j) ? j+1 : -(j+1);
                // population[i][j] = positions.contains(j) ? 1 : 0;
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
                .forEach(
                    (element) -> {
                        if(element != 0)
                            System.out.print(+ element + ", ");
                    }
                );
                System.out.println("]");
            }
        );
    }


    public static int fitness(ArrayList<Integer>[] population) {
        int total = 0;
        for(int i = 0; i < population.length; i++) {
            for(int j = 0; j < population[0].size(); j++) {
                total += population[i].get(j) > 0 ? 1 : 0;
            }
        }
        return total;
    }
}