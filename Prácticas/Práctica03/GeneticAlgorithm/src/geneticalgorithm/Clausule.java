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
        this.variables = new ArrayList<>();
    }

    public Clausule(Clausule clausule) {
        this.satisfy = clausule.satisfy;
        this.variables = new ArrayList<>();
        this.variables.addAll(clausule.variables);
    }

    public Clausule(int[] arr) {
        this.variables = new ArrayList<>();
        for(int i = 0; i < arr.length; i++) {
            this.variables.add(arr[i]);
        }
    }

    /**
     * Crea n arreglos de enteros.
     * @param n numero de arreglos a crear.
     * @return Lista de arreglos.
     */
    public static ArrayList<Integer>[] createLists(int n) {
        ArrayList<Integer> list[] = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        return list;
    }

    /*
     *
     *
     */
    public ArrayList<Integer>[] createClausules(int[][] initialPopulation) {
        int n = initialPopulation.length;
        ArrayList<Integer> clausules[] = createLists(n);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < initialPopulation[0].length; j++) {
                clausules[i].add(initialPopulation[i][j]);
            }
        }
        return clausules;
    }


    public int getSatisfy() {
        return this.satisfy;
    }

 
    public void setSatisfy(int satisfy) {
        this.satisfy = satisfy;
    }

    @Override
    public String toString() {
        return Arrays.toString(variables.toArray());
    }
}