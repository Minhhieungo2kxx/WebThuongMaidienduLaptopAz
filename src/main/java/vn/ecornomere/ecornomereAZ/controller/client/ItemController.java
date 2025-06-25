package vn.ecornomere.ecornomereAZ.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.model.dto.PaymentDefault;

import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.ProductService;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;

    @GetMapping("/product/detail/{id}")
    public String ShowDetailItem(@PathVariable Long id, Model model, HttpServletRequest request) {
        Product detail = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product Id: " + id));
        model.addAttribute("detailProduct", detail);
        return "client/product/Detailproduct"; //
    }

    @GetMapping("/")
    public String showHomePage(@RequestParam(name = "page", defaultValue = "0") String pageParam, Model model) {
        int page = 0;
        int pageSize = 8;

        try {
            page = Integer.parseInt(pageParam);
            if (page < 0)
                page = 0;
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập sai, mặc định về trang đầu
            page = 0;
        }
        List<String> targets = Arrays.asList("Mong nhe", "Doanh nhan");
        model.addAttribute("allProducts", itemService.getAllItems());
        model.addAttribute("gamingProducts", itemService.listNameItems("Gaming"));
        model.addAttribute("officeProducts", itemService.listNameItems("Van phong"));
        model.addAttribute("designProducts", itemService.listNameItems("Thiet ke do hoa"));
        model.addAttribute("personalProducts", itemService.getBytargetIn(targets));
        // Lấy tất cả sản phẩm
        Page<Product> productPage = itemService.getAllItemsPaginated(page, pageSize);
        model.addAttribute("allProducts", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        // Lấy sản phẩm theo từng loại
        Page<Product> productPageGamming = itemService.pagelistNameItems("Gaming", page, pageSize);
        model.addAttribute("gamingProducts", productPageGamming.getContent());
        model.addAttribute("currentPage1", page);
        model.addAttribute("totalPages1", productPageGamming.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPageOffice = itemService.pagelistNameItems("Van phong", page, pageSize);
        model.addAttribute("officeProducts", productPageOffice.getContent());
        model.addAttribute("currentPage2", page);
        model.addAttribute("totalPages2", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại
        Page<Product> productPagedesign = itemService.pagelistNameItems("Thiet ke do hoa", page, pageSize);
        model.addAttribute("designProducts", productPagedesign.getContent());
        model.addAttribute("currentPage3", page);
        model.addAttribute("totalPages3", productPageOffice.getTotalPages());

        // Lấy sản phẩm theo từng loại + phan trang

        Page<Product> productPersonal = itemService.getBytargetInPaginated(targets, page, pageSize);
        model.addAttribute("personalProducts", productPersonal.getContent());
        model.addAttribute("currentPage4", page);
        model.addAttribute("totalPages4", productPageOffice.getTotalPages());
        return "client/homepage/home";
    }

    // Hiển thị danh sách

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

    @GetMapping("/check-out")
    public String ShowCheckout(Model model, HttpServletRequest request) {
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
        model.addAttribute("PaymentDefault", new PaymentDefault()); // hoặc đối tượng thật từ DB
        return "client/cart/checkout";
    }

    @GetMapping("/payment-success")
    public String ShowPaymentSuccess(Model model, HttpServletRequest request) {

        return "client/cart/payment-success";
    }

    @PostMapping("/cart/update")
    public String updateCartQuantity(@RequestParam("cartDetailId") Long cartDetailId,
            @RequestParam("quantity") int quantity) {
        // Cập nhật lại trong database
        itemService.updateQuantity(cartDetailId, quantity);
        return "redirect:/cart"; // redirect lại trang giỏ hàng
    }

    @PostMapping("/place-order")
    public String SavePlaceOrder(@Valid @ModelAttribute("PaymentDefault") PaymentDefault paymentDefault,
            BindingResult result,
            HttpServletRequest request) {
        // Kiểm tra lỗi validation
        if (result.hasErrors()) {

            // Gán lại dữ liệu như bên GET va gui lai du lieu ve form thong bao loi
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");

            List<CartDetail> list = itemService.getbyCartDetails(email);
            if (list == null) {
                list = new ArrayList<>();
            }

            double totalPrice = 0.0;
            for (CartDetail detail : list) {
                totalPrice += detail.getQuantity() * detail.getPrice();
            }

            request.setAttribute("listCartDetails", list);
            request.setAttribute("sumPrice", totalPrice);
            return "client/cart/checkout"; // ❗Không redirect!
        }
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        double sumtien = paymentDefault.getSummoney();
        itemService.SavePlaceOrder(email, paymentDefault, session);
        return "redirect:/payment-success"; // redirect lại trang giỏ hàng
    }

}
