package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.model.Product;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);

    }

    public List<Product> getlistProduct() {
        return this.productRepository.findAll();
    }

    public Optional<Product> getProductbyId(Long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProductbyId(Product product) {
        this.productRepository.delete(product);
    }

    public Page<Product> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

}
