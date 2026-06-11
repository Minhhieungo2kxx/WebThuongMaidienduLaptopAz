package vn.ecornomere.ecornomereAZ.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import vn.ecornomere.ecornomereAZ.dto.request.ProductFilterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.ProductSearchRequest;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.service.FilterProductService;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;

@Controller
@RequiredArgsConstructor
public class FilterProductController {
      private final FilterProductService filterProducts;



      @GetMapping("/products-sort")
      public String ShowProduct_Sort(@RequestParam(name = "page", defaultValue = "0") String pageParam,
                  @RequestParam(name = "sort", defaultValue = "") String sort, Model model) {
            return filterProducts.ShowProductSortUser(pageParam,sort,model);
      }

      @GetMapping("/products")
      public String filterProducts(@ModelAttribute ProductFilterDTO filter, Model model) {
            return filterProducts.filterProductUser(filter,model);
      }

      @GetMapping("/product/search")
      public String searchProducts(@ModelAttribute ProductSearchRequest request, Model model) {
            return filterProducts.searchProductUser(request,model);
      }

}
