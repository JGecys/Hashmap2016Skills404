package main;

public class Product {

    public Integer type;
    public Integer count;

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

        return !(type != null
                ? !type.equals(product.type)
                : product.type != null)
            && !(count != null
                ? !count.equals(product.count)
                : product.count != null);

    }
}
