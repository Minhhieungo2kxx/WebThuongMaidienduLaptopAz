package vn.ecornomere.ecornomereAZ.controller.admin;

import java.util.LinkedHashMap;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.entity.Product;

import vn.ecornomere.ecornomereAZ.service.Elasticsearch.ProductIndexService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

@Controller
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;



    // Save san pham
    @GetMapping("/admin/product/create")
    public String createProductForm(Model model) {
        model.addAttribute("NewProduct", new Product());
        return "admin/product/create_product";
    }

    @PostMapping("/admin/product/create")
    public String createNewProduct(@ModelAttribute("NewProduct") @Valid Product product, BindingResult result,
            RedirectAttributes redirectAttributes) {
       return productService.createNewProductAd(product,result,redirectAttributes);
    }

    // Hiển thị danh sách
    @GetMapping("/admin/product")
    public String listProducts(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        return productService.listProductsAdmin(pageParam,model);
    }

    // Detail :

    @GetMapping("/admin/product/detail/{id}")
    public String showDetailForm(@PathVariable(value = "id", required = false) Long id, Model model) {
        return productService.showDetailFormAD(id,model);
    }

    // Update User:
    // Hiển thị form cập nhật
    @GetMapping("/admin/product/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        return productService.showEditFormAd(id,model);
    }

    @PostMapping("/admin/product/edit")
    public String editProduct(
            @ModelAttribute("updateProduct") Product updateProduct,
            RedirectAttributes redirectAttributes) {
       return productService.editProductAd(updateProduct,redirectAttributes);
    }

    // Delete user

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.getProductbyId(id).ifPresent(productService::deleteProductById);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa Product  thành công!");
        return "redirect:/admin/product";

    }
}
