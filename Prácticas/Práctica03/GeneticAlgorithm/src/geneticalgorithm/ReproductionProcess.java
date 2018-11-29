/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase que representa el proceso de reproducción.
 * @author rodd
 */
public class ReproductionProcess {

    /**
     * Función que aplica Partially Mapped Crossover.
     * @param parent1 Padre 1 para aplicar partiallyMappedCrossover.
     * @param parent2 Padre 2 para aplicar partiallyMappedCrossover.
     * @return array El arreglo como resultado de aplicar partiallyMappedCrossover.
     */
    static ArrayList<Clausule> partiallyMappedCrossover(Clausule parent1, Clausule parent2) {
        int corte1=10,corte2=0;
        int[] h1 = new int[parent1.variables.size()];
        int[] h2 = new int[parent1.variables.size()];
        ArrayList<Integer> subsec1 = new ArrayList<>();
        ArrayList<Integer> subsec2 = new ArrayList<>();
        for(int i=0;i<h1.length;i++) {
            h1[i] = -1;
            h2[i] = -1;
        }
        
        //Esto es para asegurarnos de que el corte 2 sea mayor al corte 1.
        while(corte1 >= corte2) {
            corte1 = ThreadLocalRandom.current().nextInt(parent1.variables.size()-1);
            corte2 = ThreadLocalRandom.current().nextInt(parent1.variables.size()-1);
        }
        
        for(int i = corte1+1; i <= corte2; i++) {
            h1[i]=parent2.variables.get(i);
            subsec1.add(parent2.variables.get(i));
            h2[i]=parent1.variables.get(i);
            subsec2.add(parent1.variables.get(i));
        }
        
        for(int i = 0; i < h1.length; i++) {
            if(!subsec1.contains(parent1.variables.get(i)) && (i<=corte1 || i>corte2)) {
                h1[i] = parent1.variables.get(i);
                subsec1.add(parent1.variables.get(i));
            }

            if(!subsec2.contains(parent2.variables.get(i)) && (i<=corte1 || i>corte2)) {
                h2[i] = parent2.variables.get(i);
                subsec2.add(parent2.variables.get(i));
            }
        }
        
        parent1.variables.removeAll(subsec2);
        parent2.variables.removeAll(subsec1);
        
        for(int i=0; i<h1.length; i++) {
            if(h1[i] == -1) {
                h1[i] = parent2.variables.remove(0);
            }
            if(h2[i] == -1) {
                h2[i] = parent1.variables.remove(0);
            }
        }
        
        Clausule tn1 = new Clausule(h1);
        Clausule tn2 = new Clausule(h2);
        
        ArrayList<Clausule> array = new ArrayList<>();
        array.add(tn1);
        array.add(tn2);
        
        return array;
    }
    
    /**
     * Función que aplica Order Crossover.
     * @param parent1 Padre 1 para aplicar orderCrossover.
     * @param parent2 Padre 2 para aplicar orderCrossover.
     * @return array El arreglo como resultado de aplicar orderCrossover.
     */
    static ArrayList<Clausule> orderCrossover(Clausule parent1, Clausule parent2) {
        int corte1=10, corte2=0;
        int[] h1 = new int[parent1.variables.size()];
        int[] h2 = new int[parent1.variables.size()];
        ArrayList<Integer> subsec1 = new ArrayList<>();
        ArrayList<Integer> subsec2 = new ArrayList<>();
        for(int i=0;i<h1.length;i++) {
            h1[i] = -1;
            h2[i] = -1;
        }
        
        //Esto es para asegurarnos de que el corte 2 sea mayor al corte 1.
        while(corte1 >= corte2) {
            corte1 = ThreadLocalRandom.current().nextInt(parent1.variables.size()-1);
            corte2 = ThreadLocalRandom.current().nextInt(parent1.variables.size()-1);
        }
        
        for(int i=corte1+1;i<=corte2;i++) {
            h1[i]=parent2.variables.get(i);
            subsec1.add(parent2.variables.get(i));
            h2[i]=parent1.variables.get(i);
            subsec2.add(parent1.variables.get(i));
        }
        
        parent1.variables.removeAll(subsec1);
        parent2.variables.removeAll(subsec2);
        
        for(int i=0;i<h1.length;i++) {
            if(h1[i]==-1) {
                h1[i] = parent1.variables.remove(0);
            }
            
            if(h2[i]==-1) {
                h2[i] = parent2.variables.remove(0);
            }
        }
        
        Clausule tn1 = new Clausule(h1);
        Clausule tn2 = new Clausule(h2);
        
        ArrayList<Clausule> array = new ArrayList<>();
        array.add(tn1);
        array.add(tn2);
        
        return array;
    }

    /**
     * Aplica la mutación de inserción y desplazamiento.
     * @param parent1 Padre 1 para aplicar crossover.
     * @param parent2 Padre 2 para aplicar crossover.
     * @param n Entero para la elección.
     * @return array El arreglo como resultado de aplicar crossover.
     */
    public static ArrayList<Clausule> crossover(Clausule parent1, Clausule parent2, int n) {
        ArrayList<Clausule> array;
        switch(n) {
            case 0:
                array = partiallyMappedCrossover(parent1, parent2);
                break;
            case 1:
                array = orderCrossover(parent1, parent2);
                break;
            default:
                array = new ArrayList<>();
                break;
        }
        return array;
    }
    
    /**
     * Aplica la mutación de inserción y desplazamiento al tour que recibe.
     * @param clausule La cláusula donde vamos a aplicar displacement.
     */
    public static void displacement(Clausule clausule) {
        int i1 = ThreadLocalRandom.current().nextInt(clausule.variables.size());
        int i2 = ThreadLocalRandom.current().nextInt(clausule.variables.size());
        Integer temp = clausule.variables.remove(i1);
        clausule.variables.add(i2,temp);
    }
    
    /**
     * Aplica la mutación de intercambio al tour que recibe.
     * @param clausule La cláusula donde vamos a aplicar exchange.
     */
    public static void exchange(Clausule clausule) {
        int i1 = ThreadLocalRandom.current().nextInt(clausule.variables.size());
        int i2 = ThreadLocalRandom.current().nextInt(clausule.variables.size());
        Integer temp = clausule.variables.get(i1);
        clausule.variables.set(i1, clausule.variables.get(i2));
        clausule.variables.set(i2, temp);
    }
    
    /**
     * Aplica la mutación a un tour.
     * @param clausule Cláusula donde vamos a aplicar la mutación.
     * @param n Entero para ecoger la mutación.
     */
    public static void mutation(Clausule clausule, int n) {
        switch(n) {
            case 0:
                displacement(clausule);
                break;
            case 1:
                exchange(clausule);
                break;
        }
    }
}
