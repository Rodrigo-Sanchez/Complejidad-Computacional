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
        ArrayList<Clausule> clausules = clausule.create(initialPopulation);
        System.out.println(clausules.toString());

        //Imprimimos las variables de la cláusula.
        //Clausule.printVariables(initialPopulation);
        
        //Imprimimos las cláusulas.
        Clausule.printClausules(initialPopulation);

        ArrayList<Integer> assignment = truthAssignment.create(initialPopulation[0].length);
        System.out.println(assignment.toString());

        ArrayList<Clausule> resultTruthAssignment;
        resultTruthAssignment = TruthAssignment.evaluate(clausules, assignment);

        Clausule.printClausules(resultTruthAssignment);

        int fitness = calculaFitness(resultTruthAssignment);
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

    
    public static int fitness(Clausule clausule) {
        int total = 0;
        for(int i = 0; i < clausule.variables.size(); i++) {
            total += clausule.variables.get(i) > 0 ? 1 : 0;
        }
        clausule.satisfy = total;
        // System.out.println(clausule.satisfy);
        return total;
    }
    

    public static int calculaFitness(ArrayList<Clausule> clausules) {
        int total = 0;
        for(Clausule clausule : clausules){
            fitness(clausule);
            total += clausule.satisfy;
        }
        return total;
    }
    

//    public static int fitness(ArrayList<Clausule> population) {
//        int total = 0;
//        for(int i = 0; i < population.size(); i++) {
//            for(int j = 0; j < population.get(i).size(); j++) {
//                total += population.get(i).getVariable(j) > 0 ? 1 : 0;
//            }
//        }
//        return total;
//    }

}