package vn.ecornomere.ecornomereAZ.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.dto.ProductFilterDTO;
import vn.ecornomere.ecornomereAZ.model.dto.ProductSearchRequest;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;

@Controller
public class FilterProductController {
      @Autowired
      private ItemService itemService;
      @Autowired
      private ProductService productService;

      @GetMapping("/products-sort")
      public String ShowProduct_Sort(@RequestParam(name = "page", defaultValue = "0") String pageParam,
                  @RequestParam(name = "sort", defaultValue = "") String sort, Model model) {
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

      @GetMapping("/products")
      public String filterProducts(@ModelAttribute ProductFilterDTO filter, Model model) {
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

      @GetMapping("/product/search")
      public String searchProducts(@ModelAttribute ProductSearchRequest request, Model model) {

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
