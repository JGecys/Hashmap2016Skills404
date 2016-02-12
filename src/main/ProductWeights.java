package main;

import java.util.ArrayList;
import java.util.List;

public class ProductWeights {
    private static ProductWeights ourInstance = new ProductWeights();

    public static ProductWeights getInstance() {
        return ourInstance;
    }

    private List<Integer> weights = new ArrayList<>();
    private int weightLimit;

    private ProductWeights() {
    }

    public void addWeight(int weight){
        weights.add(weight);
    }

    public Integer getWeight(int type){
        return weights.get(type);
    }

    public void setWeightLimit(int limit){
        this.weightLimit = limit;
    }

    public Integer getWeightLimit(){
        return weightLimit;
    }

}
