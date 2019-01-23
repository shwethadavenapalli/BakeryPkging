package backery.model;

import backery.domain.IProduct;

import java.util.List;
import java.util.Objects;

public class ShippingBag {
    private List<IProduct> products;

    public ShippingBag(List<IProduct> products) {
        this.products = products;
    }

    public int getPrice() {
        return products.stream()
                .map(IProduct::getPrice)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public List<IProduct> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingBag that = (ShippingBag) o;
        return Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products);
    }

    @Override
    public String toString() {
        return "ShippingBag{" +
                "products=" + products +
                '}';
    }
}
