package com.example.demo.genetic.model;

import java.math.BigInteger;
import java.util.List;

public class Responce {
    private List<Integer> prices;
    private Integer total;
    private List<Chromosome> posibilidades;

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Chromosome> getPosibilidades() {
        return posibilidades;
    }

    public void setPosibilidades(List<Chromosome> posibilidades) {
        this.posibilidades = posibilidades;
    }

    @Override
    public String toString() {
        return "Precios=" + prices + "\n" +
                "Total=" + total + "\n" +
                "Se exploraron " + posibilidades.size() + " posibilidades";
    }
}
