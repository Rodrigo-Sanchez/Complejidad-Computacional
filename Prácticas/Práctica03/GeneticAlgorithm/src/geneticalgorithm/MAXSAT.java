/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

/**
 *
 * @author rodd
 */
public class MAXSAT {

    static ArrayList<Clausule> clausules;
    Clausule clausule;
    static Clausule globalOptimum;
    static Clausule localOptimum;
    static Clausule best;
    TruthAssignment truthAssignment;

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

    /**
     * La “aptitud” de cada individuo estará determinada por el número total de
     * claúsulas que satisface la asignación de verdad que codifica.
     */
    public static int fitness(Clausule clausule) {
        int total = 0;
        for(int i = 0; i < clausule.variables.size(); i++) {
            total += clausule.variables.get(i) > 0 ? 1 : 0;
        }
        clausule.satisfy = total;
        // System.out.println(clausule.satisfy);
        return total;
    }
    

    public static int calculateFitness(ArrayList<Clausule> clausules) {
        int total = 0;
        for(Clausule clausule : clausules) {
            fitness(clausule);
            total += clausule.satisfy;
        }
        return total;
    }


    public static ArrayList<Clausule> selection(int n) {
        localOptimum.copy(clausules.get(0));
        ArrayList<Clausule> clausuleV = new ArrayList<>();
        int max = 0;

        for(Clausule c : clausules) {
            if(c.satisfy < localOptimum.satisfy) {
                localOptimum.copy(c);
            } else if(c.satisfy > max) {
                max = c.satisfy;
            }
        }

        // Si el óptimo local es mejor que el óptimo global, lo sustituimos.
        if(localOptimum.satisfy < globalOptimum.satisfy) {
            globalOptimum.copy(localOptimum);
        }

        best = new Clausule(localOptimum);
        clausuleV.add(localOptimum);
        
        int[] ru = new int[clausules.size()];
        ru[0] = max - clausules.get(0).satisfy + 100;
        for(int i=1; i < clausules.size(); i++) {
            ru[i] = ru[i-1] + max - clausules.get(i).satisfy + 100;
        }

        for(int i=0;i<n-1;i++) {
            int bola = ThreadLocalRandom.current().nextInt(ru[ru.length-1]);
            int j=0;
            boolean asignado = false;
            while(!asignado) {
                if(bola<=ru[j]) {
                    Clausule nuevo = new Clausule(clausules.get(j));
                    clausuleV.add(nuevo);
                    asignado = true;
                }
                j++;
            }
        }

        return clausuleV;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Clausule clausule = new Clausule();
        TruthAssignment truthAssignment = new TruthAssignment();

        int[][] initialPopulation = createPopulation();

        clausules = clausule.create(initialPopulation);
        //System.out.println(clausules.toString());

        System.out.println("Algoritmo Genético para MAXSAT.");

        //Imprimimos las variables de la cláusula.
        //Clausule.printVariables(initialPopulation);

        //Imprimimos las cláusulas.
        Clausule.printClausules(initialPopulation);

        System.out.print("La asignación de verdad que se tomó en cuenta es la siguiente: ");
        ArrayList<Integer> assignment = truthAssignment.create(initialPopulation[0].length);
        System.out.println(assignment.toString());

        ArrayList<Clausule> resultTruthAssignment;
        resultTruthAssignment = TruthAssignment.evaluate(clausules, assignment);

        Clausule.printClausules(resultTruthAssignment);

        // Calculamos el fitness de la población.
        int fitness = calculateFitness(resultTruthAssignment);
        System.out.println("El fitness de esta población es: "+fitness);

        Scanner sc = new Scanner(System.in);
        System.out.print("Digita el número de iteraciones: ");
        int iteration = sc.nextInt();

        int best_i = 0, best_fitness = 0;
        for(int i=0; i < iteration; i++) {
            ArrayList<Clausule> children = new ArrayList<>();

            for(int j = 0; j < 25; j++) {
                Clausule parent1 = new Clausule(resultTruthAssignment.get(ThreadLocalRandom.current().nextInt(resultTruthAssignment.size())));
                Clausule parent2 = new Clausule(resultTruthAssignment.get(ThreadLocalRandom.current().nextInt(resultTruthAssignment.size())));

                ArrayList<Clausule> ht = ReproductionProcess.crossover(parent1, parent2, ThreadLocalRandom.current().nextInt(1));
                children.addAll(ht);
            }

            for(Clausule c : children) {
                int prob = ThreadLocalRandom.current().nextInt(100); // La probabilidad es del 2%
                ReproductionProcess.mutation(c, prob);
            }

            resultTruthAssignment.clear();
            resultTruthAssignment.addAll(children);
            //Clausule.printClausules(resultTruthAssignment);

            fitness = calculateFitness(resultTruthAssignment); // Calculamos el fitness de la nueva generación
            System.out.println("El fitness de la población "+ (i+1) +" es: "+fitness);

            if(fitness > best_fitness) {
                best_fitness = fitness;
                best_i = i+1;
            }
        }

        System.out.println("La mejor población fue la "+ best_i +" que tiene un fitness de: "+ best_fitness);
    }

}