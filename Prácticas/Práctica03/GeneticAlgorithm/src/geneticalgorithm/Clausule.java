/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Clase que representa una cláusula.
 * @author rodd
 */
class Clausule {
    
    ArrayList<Integer> variables;
    int satisfy;

    public Clausule() {
        this.variables = new ArrayList<Integer>();
    }

    public Clausule(Clausule clausule) {
        this.satisfy = clausule.satisfy;
        this.variables = new ArrayList<Integer>();
        this.variables.addAll(clausule.variables);
    }

    public Clausule(int[] arr) {
        this.variables = new ArrayList<Integer>();
        for(int i = 0; i < arr.length; i++) {
            this.variables.add(arr[i]);
        }
    }

    /**
     * Crea n arreglos de enteros.
     * @param n numero de arreglos a crear.
     * @return Lista de arreglos.
     */
    public ArrayList<Clausule>[] createLists(int n) {
        ArrayList<Clausule> list[] = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        return list;
    }

    /*
     *
     *
     */
    public ArrayList<Clausule>[] createClausules(int[][] initialPopulation) {
        int n = initialPopulation.length;
        Clausule clausule;
        ArrayList<Clausule> clausules[] = createLists(n);
        for(int i = 0; i < n; i++) {
            clausule = new Clausule(initialPopulation[i]);
            clausules[i].add(clausule);
            // for(int j = 0; j < initialPopulation[0].length; j++) {
            //     clausules[i].add(initialPopulation[i][j]);
            // }
        }
        return clausules;
    }


    public int getSatisfy() {
        return this.satisfy;
    }


    public void setSatisfy(int satisfy) {
        this.satisfy = satisfy;
    }

    public ArrayList<Clausule> getClausule(ArrayList<Clausule>[] clausules, int i) {
        // ArrayList<Clausule> clausule;
        return clausules[i];
    }


    @Override
    public String toString() {
        return Arrays.toString(this.variables.toArray());
    }

    
    public static String toString(ArrayList<Clausule>[] variables) {
        return Arrays.deepToString(variables);
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
}