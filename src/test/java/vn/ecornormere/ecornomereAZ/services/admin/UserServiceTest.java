package vn.ecornormere.ecornomereAZ.services.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.ecornomere.ecornomereAZ.dto.request.RegisterDTO;
import vn.ecornomere.ecornomereAZ.dto.response.OrderDetailDTO;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.model.entity.*;
import vn.ecornomere.ecornomereAZ.repository.*;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private ProductService productService;

    @Mock
    private TemporaryUpload temporaryUpload;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password("123456")
                .fullName("Test User")
                .build();
    }
    @Test
    void findUserById_ShouldReturnUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getEmail())
                .isEqualTo("test@gmail.com");

        verify(userRepository).findById(1L);
    }
    @Test
    void findUserById_ShouldReturnEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<User> result = userService.findUserById(99L);
        assertThat(result).isEmpty();
    }
    @Test
    void getByEmail_ShouldReturnUser() {

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        User result = userService.getbyEmail("test@gmail.com");
        assertThat(result).isNotNull();
        assertThat(result.getFullName())
                .isEqualTo("Test User");
    }
    @Test
    void createUserAd_ShouldSaveUser() {
        Role role = new Role();
        role.setId(1L);
        when(roleService.findRoleId(1L))
                .thenReturn(role);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        user.setAvatarPublicId("avatar123");
        String result = userService.createUserAd(user, mock(BindingResult.class), 1L);
        assertThat(result).isEqualTo("redirect:/admin/list/user");
        verify(userRepository).save(any(User.class));
        verify(temporaryUpload)
                .markAsUsed("avatar123");
    }
    @Test
    void createUserAd_ShouldReturnCreatePage_WhenValidationFail() {
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        String view = userService.createUserAd(user, result, 1L);
        assertThat(view).isEqualTo("admin/user/create");
        verify(userRepository, never()).save(any());
    }
    @Test
    void createUserAd_ShouldThrowException_WhenRoleInvalid() {
        when(roleService.findRoleId(1L)).thenReturn(null);
        assertThatThrownBy(() ->
                userService.createUserAd(
                        user,
                        mock(BindingResult.class),
                        1L
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid role id");
    }
    @Test
    void updateUser_ShouldEncodePassword() {

        User existing = User.builder().id(1L).password("old").build();
        User updated = User.builder().id(1L).password("newPassword").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newPassword")).thenReturn("encoded");
        RedirectAttributes ra = mock(RedirectAttributes.class);
        String result = userService.updateUserAD(updated, ra);
        assertThat(existing.getPassword()).isEqualTo("encoded");
        assertThat(result).isEqualTo("redirect:/admin/list/user");
        verify(userRepository).save(existing);
    }
    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                userService.updateUserAD(
                        user,
                        mock(RedirectAttributes.class)
                ))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void deleteUser_ShouldDeleteSuccessfully() {

        Order order = new Order();
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(order));
        user.setAvatarPublicId("avatar123");
        userService.deleteUser(user);
        verify(orderRepository).saveAll(anyList());
        verify(temporaryUpload).markAsUnused("avatar123");
        verify(userRepository).delete(user);
    }
    @Test
    void deleteUser_ShouldThrowException_WhenUserNull() {

        assertThatThrownBy(() ->
                userService.deleteUser(null))
                .isInstanceOf(
                        IllegalArgumentException.class);
    }
    @Test
    void createOAuth2User_ShouldCreateUser() {
        Role role = new Role();
        role.setName("USER");
        when(roleService.findRoleByName("USER")).thenReturn(role);

        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));
        User result = userService.createOAuth2User(
                        "oauth@gmail.com",
                        "OAuth User",
                        "avatar.jpg"
                );
        assertThat(result.getEmail()).isEqualTo("oauth@gmail.com");
        assertThat(result.getRole()).isEqualTo(role);
        verify(userRepository).save(any(User.class));
    }
    @Test
    void updateOAuth2User_ShouldUpdateFullName() {

        user.setFullName("Old Name");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.updateOAuth2User(
                        user,
                        "New Name",
                        null
                );
        assertThat(result.getFullName()).isEqualTo("New Name");
        verify(userRepository).save(user);
    }
    @Test
    void updateOAuth2User_ShouldNotSave_WhenNoChange() {

        user.setFullName("Same Name");
        User result = userService.updateOAuth2User(
                        user,
                        "Same Name",
                        null
                );
        verify(userRepository, never()).save(any());
        assertThat(result).isEqualTo(user);
    }
    @Test
    void getCountUser_ShouldReturnCount() {
        when(userRepository.count()).thenReturn(10L);
        Long count = userService.getCountUser();
        assertThat(count).isEqualTo(10L);
    }
    @Test
    void getCountProduct_ShouldReturnCount() {
        when(productRepository.count()).thenReturn(20L);
        assertThat(
                userService.getCountProduct())
                .isEqualTo(20L);
    }
    @Test
    void getCountOrder_ShouldReturnCount() {
        when(orderRepository.count()).thenReturn(30L);
        assertThat(
                userService.getCountOrder())
                .isEqualTo(30L);
    }
    @Test
    void getUserPaginated_ShouldReturnPage() {
        Page<User> page =
                new PageImpl<>(List.of(user));
        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(page);
        Page<User> result = userService.getUserPaginated(0, 5);
        assertThat(result.getContent()).hasSize(1);
    }
    @Test
    void registerToDTO_ShouldMapCorrectly() {
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("Nguyen");
        dto.setLastName("Van A");
        dto.setEmail("abc@gmail.com");
        User user = userService.registertoDTO(dto);
        assertThat(user.getFullName())
                .isEqualTo("Nguyen Van A");
        assertThat(user.getEmail())
                .isEqualTo("abc@gmail.com");
    }
    @Nested
    class CancelOrderDetailAjaxTest {
        private User user;
        private Order order;
        private OrderDetail detail;
        private Product product;

        @BeforeEach
        void setup() {
            user = User.builder().id(1L).build();
            product = new Product();
            product.setId(1L);
            product.setQuantity(10);
            product.setSold(5);

            order = new Order();
            order.setId(1L);
            order.setUser(user);

            detail = new OrderDetail();
            detail.setId(100L);
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(2);
            detail.setTotalPrice(200000);

        }
        @Test
        void shouldThrowException_WhenOrderDetailNotFound() {
            when(orderDetailRepository.findById(100L))
                    .thenReturn(Optional.empty());
            assertThatThrownBy(() ->
                    userService.cancelOrderDetailAjax(
                            100L,
                            1L
                    ))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining(
                            "Không tìm thấy OrderDetail");
            verify(orderDetailRepository).findById(100L);
            verifyNoMoreInteractions(productService);
        }
        @Test
        void shouldThrowException_WhenUserDoesNotOwnOrder() {
            User anotherUser = User.builder().id(999L).build();
            order.setUser(anotherUser);
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));
            assertThatThrownBy(() ->
                    userService.cancelOrderDetailAjax(
                            100L,
                            1L
                    ))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining(
                            "Bạn không có quyền hủy");

            verify(orderDetailRepository).findById(100L);

            verify(productService, never()).saveProduct(any());
        }
        @Test
        void shouldCancelSuccessfully_WhenCODAndPending() {

            order.setPaymentMethod("COD");
            order.setStatus("Pending");
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));
            when(orderDetailRepository.findByOrder(order))
                    .thenReturn(List.of(
                            mock(OrderDetail.class)
                    ));
            Map<String, Object> result = userService.cancelOrderDetailAjax(100L, 1L);
            verify(productService).saveProduct(product);
            verify(orderDetailRepository).delete(detail);
            assertThat(result.get("orderDeleted")).isEqualTo(false);
        }
        @Test
        void shouldCancelSuccessfully_WhenOnlineAndUnpaid() {

            order.setPaymentMethod("VNPAY");
            order.setPaymentStatus("Unpaid");
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));

            when(orderDetailRepository.findByOrder(order))
                    .thenReturn(List.of(
                            mock(OrderDetail.class)
                    ));
            userService.cancelOrderDetailAjax(
                    100L,
                    1L
            );
            verify(orderDetailRepository).delete(detail);
            verify(productService).saveProduct(product);
        }
        @Test
        void shouldCancelSuccessfully_WhenPaidAndPending() {

            order.setPaymentMethod("VNPAY");
            order.setPaymentStatus("Paid");
            order.setStatus("Pending");
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));
            when(orderDetailRepository.findByOrder(order)).thenReturn(List.of(
                            mock(OrderDetail.class)
                    ));
            userService.cancelOrderDetailAjax(
                    100L,
                    1L
            );
            verify(orderDetailRepository)
                    .delete(detail);
        }
        @Test
        void shouldThrowException_WhenPaidAndShipping() {

            order.setPaymentMethod("VNPAY");
            order.setPaymentStatus("Paid");
            order.setStatus("Shipping");

            when(orderDetailRepository.findById(100L))
                    .thenReturn(Optional.of(detail));

            assertThatThrownBy(() ->
                    userService.cancelOrderDetailAjax(
                            100L,
                            1L
                    ))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining(
                            "Không thể hủy chi tiết đơn hàng");
            verify(orderDetailRepository, never())
                    .delete(any());
        }
        @Test
        void shouldRollbackInventoryCorrectly() {

            order.setPaymentMethod("COD");
            order.setStatus("Pending");
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));
            when(orderDetailRepository.findByOrder(order))
                    .thenReturn(List.of(
                            mock(OrderDetail.class)
                    ));
            userService.cancelOrderDetailAjax(
                    100L,
                    1L
            );
            assertThat(product.getQuantity()).isEqualTo(12);
            assertThat(product.getSold()).isEqualTo(3);
            verify(productService).saveProduct(product);
        }
        @Test
        void shouldDeleteOrder_WhenNoOrderDetailRemain() {

            order.setPaymentMethod("COD");
            order.setStatus("Pending");
            when(orderDetailRepository.findById(100L))
                    .thenReturn(Optional.of(detail));
            when(orderDetailRepository.findByOrder(order)).thenReturn(Collections.emptyList());
            Map<String, Object> result =
                    userService.cancelOrderDetailAjax(
                            100L,
                            1L
                    );

            assertThat(result.get("orderDeleted")).isEqualTo(true);
            verify(orderRepository)
                    .delete(order);
        }
        @Test
        void shouldRecalculateTotalPrice() {

            order.setPaymentMethod("COD");
            order.setStatus("Pending");

            OrderDetail remain1 = new OrderDetail();
            remain1.setTotalPrice(100000);

            OrderDetail remain2 = new OrderDetail();
            remain2.setTotalPrice(200000);
            when(orderDetailRepository.findById(100L)).thenReturn(Optional.of(detail));

            when(orderDetailRepository.findByOrder(order))
                    .thenReturn(List.of(
                            remain1,
                            remain2
                    ));
            Map<String, Object> result =
                    userService.cancelOrderDetailAjax(
                            100L,
                            1L
                    );

            assertThat(result.get("newTotal"))
                    .isEqualTo(300000.0);

            assertThat(result.get("newTotalShip"))
                    .isEqualTo(350000.0);

            assertThat(result.get("remainCount"))
                    .isEqualTo(2);
        }
        @Test
        void shouldExecuteFlowInCorrectOrder() {

            order.setPaymentMethod("COD");
            order.setStatus("Pending");
            when(orderDetailRepository.findById(100L))
                    .thenReturn(Optional.of(detail));
            when(orderDetailRepository.findByOrder(order))
                    .thenReturn(List.of(
                            mock(OrderDetail.class)
                    ));
            userService.cancelOrderDetailAjax(
                    100L,
                    1L
            );
            InOrder inOrder = inOrder(
                    productService,
                    orderDetailRepository
            );
            inOrder.verify(productService)
                    .saveProduct(product);
            inOrder.verify(orderDetailRepository)
                    .delete(detail);
        }


    }




}
