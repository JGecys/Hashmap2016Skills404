package main;

public class Product {

    public int type;
    public int count;

    public Product(Integer type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public int getWeight() {
        return ProductWeights.getInstance().getWeight(type) * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (type != product.type) return false;
        return count == product.count;

    }

    public Product clone(){
        return new Product(type, count);
    }

}
