package com.example.demo.genetic;

import com.example.demo.genetic.model.Chromosome;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.util.List;

public class GeneticOperations {
    private static final SecureRandom random = new SecureRandom();

    public static Flux<Chromosome> selectByTournament(Chromosome[] population) {
        shuffleArray(population, random);

        return Flux.fromArray(population)
                .buffer(2)
                .map(pair -> {
                    if (pair.size() == 2) {
                        Chromosome first = pair.get(0);
                        Chromosome second = pair.get(1);
                        return first.getFitness() > second.getFitness() ? first : second;
                    } else {
                        return pair.get(0);
                    }
                });
    }

    public static Mono<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        return Mono.fromCallable(() -> {
            int length = parent1.getGenes().length;
            if (length == 0) {
                throw new IllegalArgumentException("El tamaño de los genes debe ser mayor que cero.");
            }

            Chromosome offspring = new Chromosome(length);
            offspring.initialize();
            int crossoverPoint = random.nextInt(length);

            for (int i = 0; i < crossoverPoint; i++) {
                offspring.setGene(i, parent1.getGenes()[i]);
            }
            for (int i = crossoverPoint; i < length; i++) {
                offspring.setGene(i, parent2.getGenes()[i]);
            }

            return offspring;
        });
    }

    public static Flux<Chromosome> mutatePopulation(List<Chromosome> population, double mutationRate) {
        return Flux.fromIterable(population)
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(chromosome -> mutate(chromosome, mutationRate))
                .sequential();
    }

    public static void mutate(Chromosome chromosome, double mutationRate) {
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            if (random.nextDouble() < mutationRate) {
                chromosome.setGene(i, !chromosome.getGenes()[i]);
            }
        }
    }

    private static void shuffleArray(Chromosome[] array, SecureRandom rng) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rng.nextInt(i + 1);
            Chromosome a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
