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
class TruthAssignment {

    ArrayList<Integer> assignment;


    public TruthAssignment() {
        this.assignment = new ArrayList<>();
    }


    public ArrayList<Integer> create(int n) {
        int random;
        for(int i = 0; i < n; i++) {
            // 1 significa True, -1 significa False.
            random = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
            this.assignment.add(random);
        }
        return this.assignment;
    }


    public static ArrayList<Clausule> evaluate(ArrayList<Clausule> population, ArrayList<Integer> truthAsssignment) {
        int variable, assignment;

        for(int i = 0; i < population.size(); i++) {
            for(int j = 0; j < population.get(i).size(); j++) {
                if(population.get(i).getVariable(j) != 0) {
                    //System.out.println("Variable: "+population.get(i).getVariable(j));
                    //System.out.println("Asignación: "+truthAsssignment.get(j));
                    population.get(i).setVariable(j, population.get(i).getVariable(j) * truthAsssignment.get(j));
                    //System.out.println("Nueva: "+population.get(i).getVariable(j));
                }
            }
        }

        return population;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.assignment.toArray());
    }

    public static String toString(ArrayList<Clausule>[] assignment) {
        return Arrays.deepToString(assignment);
    }
}
