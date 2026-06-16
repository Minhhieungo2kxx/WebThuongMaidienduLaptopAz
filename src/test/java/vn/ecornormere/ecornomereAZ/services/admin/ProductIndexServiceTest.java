package vn.ecornormere.ecornomereAZ.services.admin;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.ecornomere.ecornomereAZ.model.document.ProductDocument;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornormere.ecornomereAZ.util.ProductTestDataFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductIndexServiceTest {
    @Mock
    private ElasticsearchClient elasticsearchClient;

    @InjectMocks
    private ProductIndexService productIndexService;

    private Product product;

    @BeforeEach
    void setup() {
        product = ProductTestDataFactory.createProduct(1L);
    }
    @Test
    void convertToDocument_shouldMapAllFieldsCorrectly() {
        ProductDocument document =
                productIndexService.convertToDocument(product);
        assertAll(
                () -> assertEquals(product.getId(), document.getId()),
                () -> assertEquals(product.getName(), document.getName()),
                () -> assertEquals(product.getPrice(), document.getPrice()),
                () -> assertEquals(product.getFactory(), document.getFactory())
        );
    }
    @Test
    void indexProduct_validProduct_shouldCallElasticsearch() throws Exception {

        IndexResponse response =
                mock(IndexResponse.class);

        when(elasticsearchClient.index(any(Function.class)))
                .thenReturn(response);

        productIndexService.indexProduct(product);

        verify(elasticsearchClient)
                .index(any(Function.class));
    }
    @Test
    void indexProduct_whenIOException_shouldThrowRuntimeException()
            throws Exception {
        when(elasticsearchClient.index(any(Function.class)))
                .thenThrow(new IOException());
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> productIndexService.indexProduct(product)
                );
        assertTrue(exception.getMessage()
                .contains("Failed to index product"));
    }
    @Test
    void deleteIndexElasticsearch_validId_shouldDeleteDocument()
            throws Exception {

        DeleteResponse response =
                mock(DeleteResponse.class);

        when(elasticsearchClient.delete(any(Function.class)))
                .thenReturn(response);

        productIndexService.DeleteIndexElasticsearch(1L);

        verify(elasticsearchClient)
                .delete(any(Function.class));
    }
    @Test
    void deleteIndexElasticsearch_whenIOException_shouldThrowException()
            throws Exception {
        when(elasticsearchClient.delete(any(Function.class)))
                .thenThrow(new IOException());
        assertThrows(
                RuntimeException.class,
                () -> productIndexService.DeleteIndexElasticsearch(1L)
        );
    }
    @Test
    void bulkIndex_emptyList_shouldNotCallElasticsearch() {

        productIndexService.bulkIndex(Collections.emptyList());

        verifyNoInteractions(elasticsearchClient);
    }
    @Test
    void bulkIndex_validProducts_shouldBulkInsert()
            throws Exception {

        List<Product> products = List.of(
                ProductTestDataFactory.createProduct(1L),
                ProductTestDataFactory.createProduct(2L),
                ProductTestDataFactory.createProduct(3L),
                ProductTestDataFactory.createProduct(4L),
                ProductTestDataFactory.createProduct(5L),
                ProductTestDataFactory.createProduct(6L)
        );

        BulkResponse response =
                mock(BulkResponse.class);
        when(response.errors()).thenReturn(false);
        when(elasticsearchClient.bulk(any(BulkRequest.class)))
                .thenReturn(response);
        productIndexService.bulkIndex(products);
        verify(elasticsearchClient)
                .bulk(any(BulkRequest.class));
    }
    @Test
    void bulkIndex_whenIOException_shouldThrowRuntimeException()
            throws Exception {

        List<Product> products =
                List.of(ProductTestDataFactory.createProduct(1L));

        when(elasticsearchClient.bulk(any(BulkRequest.class)))
                .thenThrow(new IOException());

        assertThrows(
                RuntimeException.class,
                () -> productIndexService.bulkIndex(products)
        );
    }

}
