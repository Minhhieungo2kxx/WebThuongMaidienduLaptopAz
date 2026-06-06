package vn.ecornomere.ecornomereAZ.service.Elasticsearch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductMigrationService {
    private final ProductRepository productRepository;
    private final ProductIndexService productIndexService;

    @Transactional(readOnly = true)
    public void migrateAllProducts() throws IOException {
        int page = 0;
        int size = 500;
        Page<Product> productPage;
        do {
            productPage =
                    productRepository.findAll(
                            PageRequest.of(page, size));
            for (Product product : productPage.getContent()) {
                productIndexService.indexProduct(product);
            }
            page++;
        } while (productPage.hasNext());
    }
}
