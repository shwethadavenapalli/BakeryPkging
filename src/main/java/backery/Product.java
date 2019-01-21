package backery;

import java.util.Objects;

public class Product implements  IProduct{
    private final String name;
    private final String code;
    private final int price;

    public Product(String name, String code, int price) {
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                Objects.equals(name, product.name) &&
                Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, price);
    }
}
