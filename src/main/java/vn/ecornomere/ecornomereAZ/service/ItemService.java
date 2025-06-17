package vn.ecornomere.ecornomereAZ.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.CartDetail;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.model.Product;
import vn.ecornomere.ecornomereAZ.repository.CartDetailRepository;
import vn.ecornomere.ecornomereAZ.repository.CartRepository;
import vn.ecornomere.ecornomereAZ.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Product> listNameItems(String name) {
        return this.itemRepository.findByTarget(name);

    }

    public List<Product> getAllItems() {
        return this.itemRepository.findAll();

    }

    public List<Product> getBytargetIn(List<String> listtarget) {
        return this.itemRepository.findByTargetIn(listtarget);

    }

    public void addCartItem(Long id, String email, HttpSession session) {

        // 1. Lấy user từ email
        User user = userService.getbyEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }

        // 2. Lấy giỏ hàng hiện có hoặc tạo mới nếu chưa có
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setSum(0); // bạn có thể cập nhật sau nếu cần
            cartRepository.save(cart);
        }

        // 3. Lấy sản phẩm
        Product product = productService.getProductbyId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));

        // 4. Kiểm tra xem CartDetail đã tồn tại chưa
        CartDetail existingDetail = cartDetailRepository.findByCartAndProduct(cart, product);

        if (existingDetail != null) {
            // Nếu đã tồn tại thì tăng số lượng và tổng tiền
            long currentQuantity = existingDetail.getQuantity();
            existingDetail.setQuantity(currentQuantity + 1);
            cartDetailRepository.save(existingDetail);

        } else {
            // Nếu chưa có thì tạo mới
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(product);
            cartDetail.setCart(cart);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setQuantity(1);
            cartDetailRepository.save(cartDetail);
            // Cập nhật số lượng sản phẩm trong giỏ hàng nếu cần
            cart.setSum(cart.getSum() + 1);
            cartRepository.save(cart);
            session.setAttribute("sum", cart.getSum()); // Cập nhật giỏ hàng vào session
        }

    }

    public List<CartDetail> getbyCartDetails(String email) {
        User user = userService.getbyEmail(email);
        Cart cart = cartRepository.findByUser(user);
        List<CartDetail> listCarts = cartDetailRepository.findByCart(cart);
        return listCarts;

    }

    @Transactional
    public void deleteCartDetail(Long id, HttpSession session) {

        CartDetail detail = cartDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cart detail với ID: " + id));

        Cart cart = detail.getCart();

        // Xóa CartDetail
        cartDetailRepository.delete(detail);

        // Cập nhật giỏ hàng
        if (cart.getSum() > 1) {
            int sumcart = cart.getSum() - 1;
            cart.setSum(cart.getSum() - 1);
            session.setAttribute("sum", sumcart);
            cartRepository.save(cart);
        } else {
            // Nếu còn 0, xóa giỏ hàng
            cartRepository.delete(cart);
            session.setAttribute("sum", 0);

        }

    }

}
