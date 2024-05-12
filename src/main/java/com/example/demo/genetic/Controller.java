package com.example.demo.genetic;

import com.example.demo.genetic.model.Responce;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller {

    @GetMapping
    public Mono<Responce> testFitness() {
        GeneticService geneticService = new GeneticService();

        SecureRandom random = new SecureRandom();
        List<Integer> randomNumbers = Stream.generate(() -> random.nextInt(10000) + 500)
                .limit(10000)
                .collect(Collectors.toList());
        int maxCouponAmount = 5000;

        return geneticService.generate(
                50,
                5,
                0.05,
                randomNumbers,
                randomNumbers.size(),
                maxCouponAmount
        );
    }
}
