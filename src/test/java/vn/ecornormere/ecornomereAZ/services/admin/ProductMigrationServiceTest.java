package vn.ecornormere.ecornomereAZ.services.admin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductMigrationService;
import vn.ecornormere.ecornomereAZ.util.ProductTestDataFactory;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductMigrationServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductIndexService productIndexService;
    @InjectMocks
    private ProductMigrationService productMigrationService;

    @Test
    void migrateAllProducts_singlePage_shouldIndexAllProducts() {

        List<Product> products = List.of(
                ProductTestDataFactory.createProduct(1L),
                ProductTestDataFactory.createProduct(2L)
        );
        Page<Product> page = new PageImpl<>(products);
        when(productRepository.findAll(PageRequest.of(0, 500)))
                .thenReturn(page);
        productMigrationService.migrateAllProducts();
        verify(productIndexService)
                .bulkIndex(products);
        verify(productRepository)
                .findAll(PageRequest.of(0,500));
    }
    @Test
    void migrateAllProducts_emptyPage_shouldNotFail() {

        Page<Product> emptyPage =
                Page.empty(PageRequest.of(0,500));

        when(productRepository.findAll(PageRequest.of(0,500)))
                .thenReturn(emptyPage);

        productMigrationService.migrateAllProducts();

        verify(productIndexService)
                .bulkIndex(Collections.emptyList());
    }
}
