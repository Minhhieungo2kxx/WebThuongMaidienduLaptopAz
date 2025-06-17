package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.Product;

import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/product/detail/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model) {
        Product detail = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));
        model.addAttribute("detailProduct", detail);
        return "client/product/Detailproduct"; //
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Lấy tất cả sản phẩm
        List<Product> allProducts = itemService.getAllItems();
        model.addAttribute("allProducts", allProducts);
        // Lấy sản phẩm theo từng loại
        model.addAttribute("gamingProducts", itemService.listNameItems("Gaming"));
        model.addAttribute("officeProducts", itemService.listNameItems("Van phong"));
        model.addAttribute("designProducts", itemService.listNameItems("Thiet ke do hoa"));
        List<String> targets = Arrays.asList("Mong nhe", "Doanh nhan");
        List<Product> filtered = itemService.getBytargetIn(targets);
        model.addAttribute("personalProducts", filtered);

        return "client/homepage/home";
    }

    @PostMapping("/add-cart/{id}")
    public String AddCartItem(@PathVariable Long id, HttpServletRequest request) {
        // Set thông tin vào session
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null || email.isEmpty()) {
            return "redirect:/login"; // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        } else {
            // Gọi service để thêm sản phẩm vào giỏ hàng
            itemService.addCartItem(id, email, session);
        }

        return "redirect:/"; // Sau khi lưu thì chuyển về
    }

    @GetMapping("/cart")
    public String ShowCartDetail(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<CartDetail> list = itemService.getbyCartDetails(email);
        if (list == null) {
            list = new ArrayList<>();

        }
        // Tính tổng tiền
        double totalPrice = 0.0;
        for (CartDetail detail : list) {
            totalPrice += detail.getQuantity() * detail.getPrice();
        }
        model.addAttribute("sumPrice", totalPrice); // Gửi tổng tiền xuống view
        model.addAttribute("listCartDetails", list);
        return "client/cart/cartdetails";
    }
    // Delete CartDetail

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("email") == null) {
            return "redirect:/login";
        }
        itemService.deleteCartDetail(id, session);
        return "redirect:/cart";
    }

}
