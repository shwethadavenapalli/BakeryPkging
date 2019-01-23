package backery.domain;

import java.util.Objects;

public class Package implements IProduct {

    private final IProduct product;
    private final int quantity;
    private final int price;

    private String productCode;

    public Package(IProduct product, int quantity, int price) {

        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getPrice() {

        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCode() {
        return product.getCode();
    }

    @Override
    public String toString() {
        return "Package{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productCode='" + productCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return quantity == aPackage.quantity &&
                price == aPackage.price &&
                Objects.equals(product, aPackage.product) &&
                Objects.equals(productCode, aPackage.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, price, productCode);
    }
}
