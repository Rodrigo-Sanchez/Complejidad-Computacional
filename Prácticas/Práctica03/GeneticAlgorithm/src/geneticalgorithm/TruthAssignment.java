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

//    public TruthAssignment(TruthAssignment truthAssignment) {
//        this.assignment = new ArrayList<>();
//        this.assignment.addAll(asssignment.assignment);
//    }


    public ArrayList<Integer> create(int n) {
        int random;
        for(int i = 0; i < n; i++) {
            // 1 significa True, -1 significa False.
            random = ThreadLocalRandom.current().nextBoolean() ? 1 : -1;
            this.assignment.add(random);
        }
        return this.assignment;
    }


    public static ArrayList<Clausule>[] evaluate(ArrayList<Clausule>[] population, ArrayList<Integer> truthAsssignment) {
        int variable, assignment;

        System.out.println(Clausule.toString(population));
//        for(int i = 0; i < population.length; i++) {
//            for(int j = 0; j < population[0].size(); j++) {
//                variable = population[i].get(j);
//                assignment = truthAsssignment.get(j);
//                clausule.set(j, variable*assignment);
//            }
//        }

        return population;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.assignment.toArray());
    }
}
