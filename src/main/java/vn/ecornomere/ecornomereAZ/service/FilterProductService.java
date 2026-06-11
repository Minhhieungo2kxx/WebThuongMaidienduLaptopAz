package vn.ecornomere.ecornomereAZ.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import vn.ecornomere.ecornomereAZ.dto.request.ProductFilterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSearchRequest;
import vn.ecornomere.ecornomereAZ.model.entity.Product;

@Service
@RequiredArgsConstructor
public class FilterProductService {

    private final ItemService itemService;

    private final ProductService productService;
    public String ShowProductSortUser( String pageParam, String sort, Model model) {
        int page = 0;
        int pageSize = 6;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        // Lấy tất cả sản phẩm
        Page<Product> productPage = itemService.getAllItemsPaginatedSorted(page, pageSize, sort);
        model.addAttribute("allProducts", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sort", sort); // để giữ lại lựa chọn trên view
        return "client/homepage/productfilter";
    }
    public String filterProductUser(ProductFilterDTO filter, Model model) {
        int page = 0;
        try {
            page = Integer.parseInt(filter.getPage());
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        int size = 6;
        String sortOption = (filter.getSort() != null && !filter.getSort().isEmpty()) ? filter.getSort().get(0)
                : "no-sort";

        Page<Product> productPage = productService.filterProducts(filter.getFactory(), filter.getTarget(),
                filter.getPrice(), sortOption, page,
                size);

        model.addAttribute("allProducts", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "client/homepage/productfilter";
    }
    public String searchProductUser( ProductSearchRequest request, Model model) {

        // Gọi phương thức tìm kiếm từ repository
        Page<Product> products = productService.searchProducts(request);
        model.addAttribute("allProducts", products.getContent());
        model.addAttribute("currentPage", products.getNumber());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("size", request.getSize());
        model.addAttribute("totalResults", products.getTotalElements()); // int
        model.addAttribute("searchTerm", request.getSearchTerm()); // String

        return "client/homepage/searchproducs";
    }


}
