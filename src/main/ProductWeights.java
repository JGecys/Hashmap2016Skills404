package main;

import java.util.ArrayList;
import java.util.List;

public class ProductWeights {
    private static ProductWeights ourInstance = new ProductWeights();

    public static ProductWeights getInstance() {
        return ourInstance;
    }

    private List<Integer> weights = new ArrayList<>();
    private Integer weightLimit;

    private ProductWeights() {
    }

    public void addWeight(Integer weight){
        weights.add(weight);
    }

    public Integer getWeight(Integer type){
        return weights.get(type);
    }

    public void setWeightLimit(Integer limit){
        this.weightLimit = limit;
    }

    public Integer getWeightLimit(){
        return weightLimit;
    }

}
