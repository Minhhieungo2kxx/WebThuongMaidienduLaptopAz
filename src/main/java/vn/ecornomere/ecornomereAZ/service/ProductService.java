package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

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

}
