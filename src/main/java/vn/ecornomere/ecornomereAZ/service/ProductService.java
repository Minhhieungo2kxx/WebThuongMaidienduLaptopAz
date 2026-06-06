package vn.ecornomere.ecornomereAZ.service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import vn.ecornomere.ecornomereAZ.model.document.ProductDocument;
import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.repository.TemporaryUploadRepository;
import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.FileService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;
import vn.ecornomere.ecornomereAZ.model.dto.ProductSearchRequest;

import vn.ecornomere.ecornomereAZ.model.entity.Product;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private UploadFile uploadFile = new UploadFile();

    private final OrderDetailRepository orderDetailRepository;

    private final CartDetailRepository cartDetailRepository;

    private final FileService fileService;

    private final TemporaryUpload temporaryUpload;
    private final ElasticsearchClient elasticsearchClient;
    private final ProductIndexService productIndexService;


    public Product saveProduct(Product product) {
        return this.productRepository.save(product);

    }

    @Transactional
    public void createProduct(Product product) {
        productRepository.save(product);
        try {
            productIndexService.indexProduct(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        temporaryUpload.markAsUsed(product.getPublicId());
    }


    @Transactional
    public void updateProduct(Product updateProduct) {

        Product existingProduct = productRepository.findById(updateProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product không tồn tại"));

        String oldPublicId = existingProduct.getPublicId();

        String newPublicId = updateProduct.getPublicId();

        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(Product.class, Product.class)
                .addMappings(m -> m.skip(Product::setId))
                .addMappings(m -> m.skip(Product::setSold))
                .addMappings(m -> m.skip(Product::setProductSpecs))
                .addMappings(m -> m.skip(Product::setReviews))
                .addMappings(m -> m.skip(Product::setStockdetails));

        mapper.map(updateProduct, existingProduct);

        productRepository.saveAndFlush(existingProduct); // quan trọng
        try {
            productIndexService.indexProduct(existingProduct);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean imageChanged =
                !oldPublicId.equals(newPublicId);

        if (imageChanged) {

            temporaryUpload.markAsUsed(newPublicId);
            temporaryUpload.markAsUnused(oldPublicId);
        }
    }

    public List<Product> getlistProduct() {
        return this.productRepository.findAll();
    }

    public Optional<Product> getProductbyId(Long id) {
        return this.productRepository.findById(id);
    }

    @Transactional
    public void deleteProductById(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Product không hợp lệ");
        }

        Long productId = product.getId();

        // 1. detach relations
        orderDetailRepository.findByProductId(productId)
                .forEach(od -> od.setProduct(null));

        cartDetailRepository.findByProductId(productId)
                .forEach(cd -> cd.setProduct(null));

        orderDetailRepository.flush();
        cartDetailRepository.flush();

        // 2. delete DB first OR last
        productRepository.delete(product);
        productIndexService.DeleteIndexElasticsearch(product);
        // 3. delete Cloudinary (SAU DB SUCCESS)
        try {
            fileService.deleteFile(
                    product.getPublicId(),
                    product.getResourceType());
        } catch (Exception e) {
            log.error("Cannot delete Cloudinary image {}", product.getPublicId(), e);
        }
    }

    public Page<Product> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Page<Product> filterProducts(List<String> factorys, List<String> targets, List<String> priceRanges,
                                        String sortOption, int page, int size) {

        Specification<Product> spec = Specification.where(null);

        if (factorys != null && !factorys.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("factory").in(factorys));
        }

        if (targets != null && !targets.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("target").in(targets));
        }

        if (priceRanges != null) {
            Specification<Product> priceSpec = Specification.where(null);
            for (String range : priceRanges) {
                String[] parts = range.split("-");
                double min = Double.parseDouble(parts[0]);
                double max = Double.parseDouble(parts[1]);
                priceSpec = priceSpec.or((root, query, cb) -> cb.between(root.get("price"), min, max));

            }
            spec = spec.and(priceSpec);
        }

        Sort sort = Sort.unsorted();
        switch (sortOption) {
            case "price-asc":
                sort = Sort.by("price").ascending();
                break;
            case "price-desc":
                sort = Sort.by("price").descending();
                break;
            case "name-asc":
                sort = Sort.by("name").ascending();
                break;
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(spec, pageable);
    }

    public Page<Product> searchProducts(ProductSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        // Gọi phương thức tìm kiếm từ repository
        Page<Product> products = productRepository.searchProducts(request.getSearchTerm(), pageable);
        return products;

    }

    public List<Product> getforUpdate(List<Long> ids) {
        return productRepository.findAllForUpdate(ids);
    }

    public void SaveAll(List<Product> list) {
        productRepository.saveAll(list);
    }

    public List<Product> findRelevantProducts(String question) throws IOException {

        List<Long> ids =searchProducts(question);
        List<Product> products =
                productRepository.findAllById(ids);
        Map<Long, Product> map =
                products.stream()
                        .collect(Collectors.toMap(
                                Product::getId,
                                Function.identity()
                        ));
        return ids.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .toList();
    }
    public List<Long> searchProducts(String question)
            throws IOException {

        SearchResponse<ProductDocument> response =
                elasticsearchClient.search(s -> s
                                .index("products")
                                .size(5)

                                .query(q -> q
                                        .multiMatch(mm -> mm
                                                .query(question)
                                                .fields(
                                                        "name^5",
                                                        "shortDesc^3",
                                                        "detailDesc",
                                                        "factory^2",
                                                        "target^2"
                                                )
                                                .fuzziness("AUTO")
                                        )
                                ),
                        ProductDocument.class
                );
        return response.hits()
                .hits()
                .stream()
                .map(hit -> hit.source().getId())
                .toList();
    }


}
