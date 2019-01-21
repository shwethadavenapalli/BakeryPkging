package backery;

import backery.exception.ProductNotFound;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BakerTest {

    private Baker baker;
    private Package packageBB2, packageBB5, packageBB8, packageCR3, packageCR5, packageCR9, packageVS3, packageVS5,
            packageA3, packageA2;

    @Before
    public void setup() {
        Product productA = new Product("Apple", "A", 5);
        Product productB = new Product("Banana", "B", 2);

        Product productBlueBerry = new Product("BlueBerry", "BB", 2);
        Product productCrossiant = new Product("Crossiant", "CR", 2);
        Product productVegScroll = new Product("VegScroll", "VS", 2);

        packageA3 = new Package(productA, 3, 10);
        packageA2 = new Package(productA, 2, 7);

        Package packageB1 = new Package(productB, 3, 10);
        packageBB2 = new Package(productBlueBerry, 2, 5);
        packageBB5 = new Package(productBlueBerry, 5, 7);

        packageBB8 = new Package(productBlueBerry, 8, 9);

        packageCR3 = new Package(productCrossiant, 3, 9);
        packageCR5 = new Package(productCrossiant, 5, 15);
        packageCR9 = new Package(productCrossiant, 9, 27);

        packageVS3 = new Package(productVegScroll, 3, 9);
        packageVS5 = new Package(productVegScroll, 5, 15);

        baker = new Baker(
                productA, packageA3, packageA2,
                productB, packageB1,
                packageBB2, packageBB5, packageBB8,
                packageCR3, packageCR5, packageCR9,
                packageVS3, packageVS5
        );
    }
    @Test
    public void givenProduct_A_WithCount3_Then_Should_Return_PackedItem() throws ProductNotFound {
        ShippingBag packages = baker.getPacks("A", 3);
        assertEquals(10, packages.getPrice());
    }
    @Test
    public void givenProduct_B_Should_Return_PackedItem() throws ProductNotFound {
        ShippingBag packages = baker.getPacks("B",
                3);
        assertEquals(10, packages.getPrice());
    }
    @Test
    public void givenNonExistentProduct_ShouldThrow_ProductNotFound() {
        assertThatExceptionOfType(ProductNotFound.class).isThrownBy(
                () -> baker.getPacks("C", 3)
        );
    }
    @Test
    public void givenMultiplePkgTypes_OfA_AndCount3_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag expectedPkg = baker.getPacks("A", 3);
        assertEquals(10, expectedPkg.getPrice());
    }

    @Test
    public void givenMultiplePkgTypes_OfA_AndCount6_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag expectedPkg = baker.getPacks("A", 6);
        assertEquals(20, expectedPkg.getPrice());
    }

    @Test
    public void givenMultiplePkgTypes_OfA_AndCount5_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag expectedPkg = baker.getPacks("A", 5);
        assertEquals(17, expectedPkg.getPrice());
    }
    @Test
    public void givenMultiplePkgTypes_OfA_AndCount10_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag shippingBag = baker.getPacks("A", 10);
        assertEquals(34, shippingBag.getPrice());
        List<IProduct> expectedPkgs = Arrays.asList(packageA3, packageA3, packageA2, packageA2);
        assertEquals(expectedPkgs.size(), shippingBag.getProducts().size());
        assertThat(expectedPkgs).containsAll(shippingBag.getProducts());
    }
    @Test
    public void givenMultiplePkgTypes_OfA_AndCount2_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag expectedPkg = baker.getPacks("A", 2);
        assertEquals(7, expectedPkg.getPrice());
    }
    @Test
    public void givenMultiplePkgTypes_OfA_AndCount4_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag expectedPkg = baker.getPacks("A", 4);
        assertEquals(14, expectedPkg.getPrice());
    }
    @Test
    public void givenMultiplePkgTypes_OfBlueberry_AndCount14_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag actualPkgs = baker.getPacks("BB", 14);
        assertEquals(24, actualPkgs.getPrice());
        List<Package> expectedPkgs = Arrays.asList(packageBB8, packageBB2, packageBB2, packageBB2);
        assertEquals(expectedPkgs.size(), actualPkgs.getProducts().size());
        assertThat(expectedPkgs).isEqualTo(actualPkgs.getProducts());
    }
    @Test
    public void givenMultiplePkgTypes_OfVegScroll_AndCount10_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag actualPkgs = baker.getPacks("VS", 10);
        assertEquals(30, actualPkgs.getPrice());
        List<Package> expectedPkgs = Arrays.asList(packageVS5, packageVS5);
        assertEquals(expectedPkgs.size(), actualPkgs.getProducts().size());
        assertThat(expectedPkgs).isEqualTo(actualPkgs.getProducts());
    }
    @Test
    public void givenMultiplePkgTypes_OfCrossiant_AndCount13_ShouldReturnPackedItem() throws ProductNotFound {
        ShippingBag shippingBag = baker.getPacks("CR", 10);
        assertEquals(30, shippingBag.getPrice());
        List<Package> expectedPkgs = Arrays.asList(packageCR5, packageCR5);
        assertEquals(expectedPkgs.size(), shippingBag.getProducts().size());
        assertThat(expectedPkgs).isEqualTo(shippingBag.getProducts());
    }
    @Test
    public void givenProductAndCount_ShouldReturnEmptyPkg() throws ProductNotFound {
        ShippingBag shippingBag = baker.getPacks("CR", 2);
        assertEquals(0, shippingBag.getPrice());
        assertTrue(shippingBag.getProducts().isEmpty());
    }
}