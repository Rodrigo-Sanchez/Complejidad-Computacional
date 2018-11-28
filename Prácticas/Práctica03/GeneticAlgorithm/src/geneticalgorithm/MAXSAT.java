/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.Arrays;
import java.util.ArrayList;
//import java.util.Scanner;
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
        System.out.println(clausule.toString());

        int[][] initialPopulation = createPopulation();
        ArrayList<Integer>[] clausules = clausule.createClausules(initialPopulation);
        // ArrayList<Integer> array[] = createLists(initialPopulation.length);
        // for(int i = 0; i < initialPopulation.length; i++) {
        //     for(int j = 0; j < initialPopulation[0].length; j++) {
        //         array[i].add(initialPopulation[i][j]);
        //     }
        // }

        // Imprimimos 
        //System.out.println(Arrays.deepToString(array));
        
        // printMatrix(initialPopulation);
        printClausules(initialPopulation);
        
        ArrayList<Integer> truthAssignment = createTruthAssignment(initialPopulation[0].length);
        System.out.println("Asignación de verdad de las variables: " + Arrays.toString(truthAssignment.toArray()));

        ArrayList<Integer>[] resultTruthAssignment;
        //System.out.println("Asignación de verdad de las variables: " + Arrays.deepToString(resultTruthAssignment));
        resultTruthAssignment = evaluateTruthAssignment(clausules, truthAssignment);

        printClausules(resultTruthAssignment);

        int fitness = fitness(resultTruthAssignment);
        System.out.println("El fitness de esta población es: "+fitness);
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
        for (int[] population1 : population) {
            // Creamos el random para validar la cardinalidad de las variables.
            variables = ThreadLocalRandom.current().nextInt(minVariable, maxVariable+1);
            for(int v = 0; v < variables; v++) {
                // Aquí agregamos las posiciones de las variables de la cláusula.
                positions.add(ThreadLocalRandom.current().nextInt(n)+1);
            }
            for (int j = 0; j < population[0].length; j++) {
                paridad = ThreadLocalRandom.current().nextInt(2);
                // Si está la variable, agregamos la posición ya sea en positivo o negativo, en otro caso un 0.
                population1[j] = positions.contains(j) ? (paridad > 0 ? j+1 : -(j+1)) : 0;
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


    public static void printClausules(int[][] matrix) {
        int element;
        for(int i = 0; i < matrix.length; i++) {
            System.out.print("Cláusula "+ (i+1) +": [");
            for(int j = 0; j < matrix[0].length; j++) {
                element = matrix[i][j];
                if(element > 0) {
                    System.out.print("x" + element + ", ");
                } else if(element < 0) {
                    System.out.print("¬x" + Math.abs(element) + ", ");
                }
            }
            System.out.println("]");
        }
    }


    public static void printClausules(ArrayList<Integer> array[]) {
        int element;
        for(int i = 0; i < array.length; i++) {
            System.out.print("Cláusula "+ (i+1) +": [");
            for(int j = 0; j < array[0].size(); j++) {
                element = array[i].get(j);
                if(element > 0) {
                    System.out.print("x" + element + ", ");
                } else if(element < 0) {
                    System.out.print("¬x" + Math.abs(element) + ", ");
                }
            }
            System.out.println("]");
        }
    }


    public static ArrayList<Integer>[] evaluateTruthAssignment(ArrayList<Integer>[] population, ArrayList<Integer> truthAsssignment) {
        int element;
        int truth;

        for(ArrayList<Integer> population1 : population) {
            for(int j = 0; j < population[0].size(); j++) {
                element = population1.get(j);
                truth = truthAsssignment.get(j);
                population1.set(j, element*truth);
                // if(element > 0) {
                //     population[i].set(j, element*truth);
                // } else if(element < 0) {
                //     if(truth == 0) {
                //         population[i].set(j, element*-1);
                //     }
                // }
            }
            // array[i].add(population[i][j]);
            // System.out.println(population[i]);
        }

        // Quitamos todos los ceros.
        // ArrayList<Integer> zero = new ArrayList<>();
        // zero.add(0);

        // for(ArrayList<Integer> array : population) {
        //     array.removeAll(zero);
        // }

        return population;
    }


    public static ArrayList<Integer> createTruthAssignment(int n) {
        ArrayList<Integer> truthAsssignment = new ArrayList<Integer>();
        int random;
        for(int i = 0; i < n; i++) {
            // 1 sigmifica True, -1 significa False.
            random = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
            truthAsssignment.add(random);
        }
        return truthAsssignment;
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