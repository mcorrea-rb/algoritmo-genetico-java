package com.example.demo.genetic;

import com.example.demo.genetic.model.Chromosome;
import com.example.demo.genetic.model.Responce;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class GeneticService {

    public Mono<Responce> generate(
            int populationSize,
            int generations,
            double mutationRate,
            List<Integer> prices,
            int numItems,
            int maxCouponAmount
    ) {
        return Mono.from(Flux.range(0, populationSize)
                .map(i -> new Chromosome(numItems))
                .doOnNext(Chromosome::initialize)
                .collectList()
                .flatMapMany(population -> Flux.range(0, generations)
                        .flatMap(gen -> Flux.fromIterable(population)
                                .flatMap(chromosome -> GeneticOperations.selectByTournament(population.toArray(new Chromosome[0]))
                                        .collectList()
                                        .flatMap(parents -> GeneticOperations.crossover(parents.get(0), parents.size() > 1 ? parents.get(1) : parents.get(0)))
                                        .flatMap(offspring -> FitnessCalculator.calculateFitness(offspring, prices, maxCouponAmount)
                                                .map(fitness -> {
                                                    offspring.setFitness(fitness);
                                                    return offspring;
                                                }))
                                )
                                .collectList()
                                .flatMap(newPopulation -> GeneticOperations.mutatePopulation(newPopulation, mutationRate)
                                        .then(Mono.just(newPopulation)))
                                .flatMap(newPopulation -> FitnessCalculator.calculateFitnessInParallel(newPopulation, prices, maxCouponAmount)
                                        .then(Mono.just(newPopulation)))
                        )
                        .last()
                )
                .flatMap(c -> getMaxFitness(c, prices)));
    }

    private Mono<Responce> getMaxFitness(List<Chromosome> population, List<Integer> prices) {
        return Flux.fromIterable(population)
                .sort((c1, c2) -> Double.compare(c2.getFitness(), c1.getFitness()))
                .reduce(Chromosome::maxCromosome)
                .map(chromosome -> {

                    List<Integer> pricesResponse = new ArrayList<>();
                    for (int i = 0; i < chromosome.getGenes().length; i++) {
                        if (chromosome.getGenes()[i]) {
                            pricesResponse.add(prices.get(i));
                        }
                    }

                    Responce response = new Responce();
                    response.setPrices(pricesResponse);
                    //response.setPosibilidades(population);  // Convertir arreglo a lista para el response
                    int total = pricesResponse.stream().reduce(0, Integer::sum);
                    response.setTotal(total);
                    return response;
                });
    }
}
