package vn.ecornomere.ecornomereAZ.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.ProductSpec;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.utils.UploadFile;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    private UploadFile uploadFile = new UploadFile();

    // Save san pham
    @GetMapping("/admin/product/create")
    public String createProductForm(Model model) {
        model.addAttribute("NewProduct", new Product());
        return "admin/product/create_product";
    }

    // Save san pham
    @PostMapping("/admin/product/create")
    public String createnewProduct(@ModelAttribute("NewProduct") @Valid Product product, BindingResult result,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes)
            throws IllegalStateException, IOException {

        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            return "admin/product/create_product"; // Trả về form với lỗi
        }
        product.setImage(uploadFile.getnameFile(avatarFile, "products"));
        // Lưu user vào database
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");

        return "redirect:/admin/product"; // Sau khi lưu thì chuyển về danh sách Product
    }

    // Hiển thị danh sách
    @GetMapping("/admin/product")
    public String listProducts(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        int page = 0;
        int pageSize = 5;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }

        Page<Product> productPage = productService.getProductsPaginated(page, pageSize);
        model.addAttribute("Listproduct", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "admin/product/product_index";
    }

    // Detail :

    @GetMapping("/admin/product/detail/{id}")
    public String showDetailForm(@PathVariable(value = "id", required = false) Long id, Model model) {
        Product detail = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));
        model.addAttribute("detailProduct", detail);
        return "admin/product/detail_product"; //
    }

    // Update User:
    // Hiển thị form cập nhật
    @GetMapping("/admin/product/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));

        model.addAttribute("updateProduct", product);

        // Danh sách Factory
        Map<String, String> factoryList = new LinkedHashMap<>();
        factoryList.put("Apple", "Apple (Macbook)");
        factoryList.put("Asus", "Asus");
        factoryList.put("Lenovo", "Lenovo");
        factoryList.put("Dell", "Dell");
        factoryList.put("LG", "LG");
        factoryList.put("Acer", "Acer");
        factoryList.put("HP", "HP");

        model.addAttribute("factoryList", factoryList);

        // Danh sách Target
        Map<String, String> targetList = new LinkedHashMap<>();
        targetList.put("Gaming", "Gamming");
        targetList.put("Văn phòng", "Sinh viên - Văn phòng");
        targetList.put("Thiết kế đồ họa", "Thiết kế đồ họa");
        targetList.put("Mỏng nhẹ", "Mỏng nhẹ");
        targetList.put("Doanh nhân", "Doanh nhân");

        model.addAttribute("targetList", targetList);

        return "admin/product/edit_product";
    }

    @PostMapping("/admin/product/edit")
    public String editProduct(
            @ModelAttribute("updateProduct") Product updateProduct,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) {

        Product existingProduct = productService.getProductbyId(updateProduct.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid product Id:" + updateProduct.getId()));

        String oldImage = existingProduct.getImage();

        ModelMapper map = new ModelMapper();
        map.typeMap(Product.class, Product.class)
                .addMappings(m -> m.skip(Product::setId))
                .addMappings(m -> m.skip(Product::setImage))
                .addMappings(m -> m.skip(Product::setSold))
                .addMappings(m -> m.skip(Product::setProductSpecs))
                .addMappings(m -> m.skip(Product::setReviews))
                .addMappings(m -> m.skip(Product::setStockdetails));

        map.map(updateProduct, existingProduct);

        try {
            if (avatarFile != null && !avatarFile.isEmpty()) {
                String newImage = uploadFile.getnameFile(avatarFile, "products");
                existingProduct.setImage(newImage);
                uploadFile.deleteImageFile(oldImage, "products");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/product/edit/" + updateProduct.getId();
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage", "Lỗi upload ảnh, vui lòng thử lại");
            return "redirect:/admin/product/edit/" + updateProduct.getId();
        }

        List<ProductSpec> newSpecs = updateProduct.getProductSpecs();
        if (newSpecs != null) {
            existingProduct.getProductSpecs().clear();
            for (ProductSpec spec : newSpecs) {
                spec.setProduct(existingProduct);
                existingProduct.getProductSpecs().add(spec);
            }
        }

        productService.saveProduct(existingProduct);

        redirectAttributes.addFlashAttribute(
                "successMessage", "Edit sản phẩm thành công!");
        return "redirect:/admin/product";
    }

    // Delete user

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.getProductbyId(id).ifPresent(productService::deleteProductById);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa Product  thành công!");
        return "redirect:/admin/product";

    }
}
