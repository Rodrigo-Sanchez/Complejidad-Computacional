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
    
    // Una cláusula contiene una lista de variables.
    ArrayList<Integer> variables;
    // Tiene un número que representa la cantidad de variables que satisface la asignación de verdad que codifica.
    int satisfy;

    /**
     * Constructor de una cláusula.
     */
    public Clausule() {
        this.variables = new ArrayList<>();
    }

    /**
     * Constructor de una cláusula a partir de otra.
     * @param clausule La cláusula con la que va a crear otra cláusula.
     */
    public Clausule(Clausule clausule) {
        this.satisfy = clausule.satisfy;
        this.variables = new ArrayList<>();
        this.variables.addAll(clausule.variables);
    }

    /**
     * Constructor que crea una cláusula a partir de un arreglo.
     * @param arr Arreglo con el cual vamos a crear una cláusula.
     */
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
    public ArrayList<Clausule> createLists(int n) {
        ArrayList<Clausule> array = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            array.add(new Clausule());
        }
        return array;
    }

    /**
     * Crea un arreglo de cláusulas a partir de una matriz.
     * @param initialPopulation Matriz de enteros bidimensional.
     * @return clausules El arreglo de cláusulas.
     */
    public ArrayList<Clausule> create(int[][] initialPopulation) {
        int n = initialPopulation.length;
        ArrayList<Clausule> clausules = createLists(n);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < initialPopulation[0].length; j++) {
                clausules.get(i).variables.add(initialPopulation[i][j]);
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

    public ArrayList<Integer> getVariables() {
        return this.variables;
    }


    public void setVariables(ArrayList<Integer> variables) {
        this.variables.addAll(variables);
    }

    
    public int getVariable(int n) {
        return this.variables.get(n);
    }

    public void setVariable(int i, int n) {
        this.variables.set(i, n);
    }
    
    public void removeVariable(int i) {
        this.variables.remove(i);
    }    

    public void copy(Clausule clausule){
        this.variables.clear();
        this.variables.addAll(clausule.variables);
        this.satisfy = clausule.satisfy;
    }

    public ArrayList<Clausule> getClausule(ArrayList<Clausule>[] clausules, int i) {
        return clausules[i];
    }

    public int size() {
        return this.variables.size();
    }

    @Override
    public String toString() {
        return Arrays.toString(this.variables.toArray());
    }

    
    public static String toString(ArrayList<Clausule>[] variables) {
        return Arrays.deepToString(variables);
    }


    public static void printVariables(int[][] matrix) {
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


    public static void printClausules(ArrayList<Clausule> array) {
        int element;
        for(int i = 0; i < array.size(); i++) {
            System.out.print("Cláusula "+ (i+1) +": [");
            for(int j = 0; j < array.get(i).size(); j++) {
                element = array.get(i).getVariable(j);
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