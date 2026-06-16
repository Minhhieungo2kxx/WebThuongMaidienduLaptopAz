package vn.ecornormere.ecornomereAZ.services.admin;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.ecornomere.ecornomereAZ.dto.record.ProductDeletedEvent;
import vn.ecornomere.ecornomereAZ.dto.record.ProductEvent;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.FileService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private FileService fileService;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private CartDetailRepository cartDetailRepository;

    @Mock
    private TemporaryUpload temporaryUpload;

    @Mock
    private ElasticsearchClient elasticsearchClient;

    @Mock
    private ProductIndexService productIndexService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    @Spy
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Macbook Pro");
        product.setPrice(2000);
        product.setQuantity(10);
        product.setSold(0);
        product.setFactory("Apple");
        product.setTarget("Doanh nhân");
        product.setPublicId("old-public-id");
        product.setResourceType("image");
    }
    @Test
    void saveProduct_ShouldReturnSavedProduct() {
        when(productRepository.save(product)).thenReturn(product);
        Product result = productService.saveProduct(product);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Macbook Pro");
        verify(productRepository).save(product);
    }
    @Test
    void createProduct_ShouldSaveAndPublishEvent() {
        when(productRepository.save(product)).thenReturn(product);
        productService.createProduct(product);
        verify(productRepository).save(product);
        verify(temporaryUpload).markAsUsed("old-public-id");
        verify(eventPublisher).publishEvent(any(ProductEvent.class));
    }
    @Test
    void getProductById_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> result = productService.getProductbyId(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }
    @Test
    void getProductById_ShouldReturnEmpty() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Product> result = productService.getProductbyId(1L);
        assertThat(result).isEmpty();
    }
    @Test
    void updateProduct_ShouldUpdateAndMarkImage() {
        Product update = new Product();
        update.setId(1L);
        update.setName("Macbook M4");
        update.setPublicId("new-public-id");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.updateProduct(update);
        verify(productRepository).saveAndFlush(any(Product.class));

        verify(temporaryUpload).markAsUsed("new-public-id");
        verify(temporaryUpload).markAsUnused("old-public-id");
        verify(eventPublisher).publishEvent(any(ProductEvent.class));
    }
    @Test
    void updateProduct_WhenImageNotChanged_ShouldNotMarkImage() {

        Product update = new Product();
        update.setId(1L);
        update.setPublicId("old-public-id");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.updateProduct(update);
        verify(temporaryUpload, never()).markAsUsed(any());
        verify(temporaryUpload, never()).markAsUnused(any());
        verify(productRepository).saveAndFlush(any(Product.class));
    }
    @Test
    void updateProduct_WhenProductNotFound_ShouldThrowException() {
        Product update = new Product();
        update.setId(99L);
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() ->
                productService.updateProduct(update))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product không tồn tại");
    }
    @Test
    void deleteProduct_ShouldDeleteAndPublishEvent() {
        productService.deleteProductById(product);
        verify(orderDetailRepository).flush();
        verify(cartDetailRepository).flush();
        verify(productRepository).delete(product);
        verify(eventPublisher).publishEvent(any(ProductDeletedEvent.class));
    }
    @Test
    void deleteProduct_WhenProductNull_ShouldThrowException() {
        assertThatThrownBy(() ->
                productService.deleteProductById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product không hợp lệ");
    }
    @Test
    void getProductsPaginated_ShouldReturnPage() {
        Page<Product> page = new PageImpl<>(List.of(product));
        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<Product> result = productService.getProductsPaginated(0,5);
        assertThat(result.getContent()).hasSize(1);
        verify(productRepository)
                .findAll(any(Pageable.class));
    }
    @Test
    void getListProduct_ShouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> result = productService.getlistProduct();
        assertThat(result).hasSize(1);
        verify(productRepository).findAll();
    }
    @Test
    void saveAll_ShouldSaveProducts() {
        List<Product> products = List.of(product);
        productService.SaveAll(products);
        verify(productRepository).saveAll(products);
    }
    @Test
    void getForUpdate_ShouldReturnProducts() {
        List<Long> ids = List.of(1L,2L);
        when(productRepository.findAllForUpdate(ids)).thenReturn(List.of(product));
        List<Product> result = productService.getforUpdate(ids);
        assertThat(result).hasSize(1);
        verify(productRepository).findAllForUpdate(ids);
    }
    @Test
    void findRelevantProducts_ShouldReturnProducts() throws Exception {
        doReturn(List.of(1L))
                .when(productService)
                .searchProducts("macbook");
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(product));
        List<Product> result = productService.findRelevantProducts("macbook");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    

}
