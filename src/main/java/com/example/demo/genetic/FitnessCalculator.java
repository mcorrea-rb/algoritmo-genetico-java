package com.example.demo.genetic;

import com.example.demo.genetic.model.Chromosome;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class FitnessCalculator {
    public static Mono<Integer> calculateFitness(Chromosome chromosome, List<Integer> prices, int maxCouponAmount) {
        return Mono.fromCallable(() -> {
            int totalCost = 0;
            for (int i = 0; i < chromosome.getGenes().length; i++) {
                if (chromosome.getGenes()[i] && totalCost + prices.get(i) <= maxCouponAmount) {
                    totalCost += prices.get(i);
                } else {
                    chromosome.deleteGene(i);
                }
            }
            return totalCost;
        });
    }

    public static Mono<Void> calculateFitnessInParallel(List<Chromosome> population, List<Integer> prices, int maxCouponAmount) {
        return Mono.from(Flux.fromIterable(population)
                .parallel()
                .runOn(Schedulers.parallel())
                .doOnNext(chromosome -> calculateFitness(chromosome, prices, maxCouponAmount)
                        .subscribe(chromosome::setFitness))
                .sequential()
                .then());
    }
}

