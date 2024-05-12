package com.example.demo.genetic.model;

import java.security.SecureRandom;
import java.util.Arrays;

public class Chromosome {
    private final boolean[] genes;
    private Integer fitness;
    private int count;

    public Chromosome(int numItems) {
        this.genes = new boolean[numItems];
        this.fitness = 0;
        this.count = 0;
    }

    public static Chromosome maxCromosome(Chromosome chromosome1, Chromosome chromosome2) {
        if (chromosome1.getFitness() > chromosome2.getFitness()) {
            return chromosome1;
        }else {
            return chromosome2;
        }
    }

    public void initialize() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextBoolean();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount() {
        this.count++;
    }

    public void resetCount() {
        this.count = 0;
    }

    public boolean[] getGenes() {
        return genes;
    }

    public void deleteGene(int gene) {
        genes[gene] = false;
    }

    public void setGene(int index, boolean value) {
        this.genes[index] = value;
    }

    public Integer getFitness() {
        return fitness;
    }

    public void setFitness(Integer fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "\nGenes=" + Arrays.toString(genes) + "\n" +
                "Fitness=" + fitness;
    }
}
