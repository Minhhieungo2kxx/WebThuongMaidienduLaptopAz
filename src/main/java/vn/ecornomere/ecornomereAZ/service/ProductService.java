package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.OrderDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.ProductRepository;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.OrderDetail;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.dto.ProductSearchRequest;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

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

    @Transactional
    public void deleteProductById(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product không hợp lệ");
        }

        Long productId = product.getId();

        // Xử lý các OrderDetail liên quan
        List<OrderDetail> orderDetails = orderDetailRepository.findByProductId(productId);
        if (!orderDetails.isEmpty()) {
            for (OrderDetail od : orderDetails) {
                od.setProduct(null);
            }
            orderDetailRepository.saveAll(orderDetails);
        }

        // Xử lý các CartDetail liên quan
        List<CartDetail> cartDetails = cartDetailRepository.findByProductId(productId);
        if (!cartDetails.isEmpty()) {
            for (CartDetail cd : cartDetails) {
                cd.setProduct(null);
            }
            cartDetailRepository.saveAll(cartDetails);
        }

        // Cuối cùng mới xóa product
        productRepository.delete(product);
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

}
