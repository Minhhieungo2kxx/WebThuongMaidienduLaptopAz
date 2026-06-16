package vn.ecornormere.ecornomereAZ.util;

import vn.ecornomere.ecornomereAZ.model.entity.Product;

public class ProductTestDataFactory {
    public static Product createProduct(Long id) {
        Product product = new Product();

        product.setId(id);
        product.setName("Iphone 15");
        product.setShortDesc("Short");
        product.setDetailDesc("Detail");
        product.setFactory("Apple");
        product.setTarget("USER");
        product.setPrice(1000.0);
        product.setSold(10L);
        product.setQuantity(100L);
        return product;
    }
}
