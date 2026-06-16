package vn.ecornormere.ecornomereAZ.services.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import vn.ecornomere.ecornomereAZ.dto.request.ForgotPasswordDTO;
import vn.ecornomere.ecornomereAZ.dto.request.RegisterDTO;
import vn.ecornomere.ecornomereAZ.dto.request.Userupdate;
import vn.ecornomere.ecornomereAZ.dto.response.OrderHistoryDTO;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.model.entity.Role;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.service.ForgotPasswordService;
import vn.ecornomere.ecornomereAZ.service.HomeService;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UploadFile.TemporaryUpload;
import vn.ecornomere.ecornomereAZ.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeServiceTest {
    @InjectMocks
    private HomeService homeService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private ForgotPasswordService forgotPasswordService;

    @Mock
    private TemporaryUpload temporaryUpload;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Test
    void createRegisterClient_ShouldReturnRegisterPage_WhenValidationFail() {

        RegisterDTO dto = new RegisterDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = homeService.createRegisterClient(dto, bindingResult, model);
        assertThat(result).isEqualTo("client/authentication/register");
        verify(model).addAttribute("newRegister", dto);
        verifyNoInteractions(userService);
    }
    @Test
    void createRegisterClient_ShouldRegisterSuccessfully() {

        RegisterDTO dto = new RegisterDTO();
        dto.setPassword("123456");
        User user = new User();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.registertoDTO(dto)).thenReturn(user);
        when(passwordEncoder.encode("123456"))
                .thenReturn("Hieusomegirl");
        Role role = new Role();
        when(roleService.findRoleByName("USER")).thenReturn(role);
        String result = homeService.createRegisterClient(dto, bindingResult, model);

        assertThat(result).isEqualTo("redirect:/login");
        assertThat(user.getPassword()).isEqualTo("Hieusomegirl");
        assertThat(user.getRole()).isEqualTo(role);
        verify(userService).handleSaveUser(user);
    }
    @Test
    void createRegisterClient_ShouldNotEncodePassword_WhenPasswordNull() {

        RegisterDTO dto = new RegisterDTO();
        User user = new User();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.registertoDTO(dto)).thenReturn(user);
        Role role = new Role();
        when(roleService.findRoleByName("USER")).thenReturn(role);
        homeService.createRegisterClient(dto, bindingResult, model);
        verify(passwordEncoder, never()).encode(anyString());
    }
    @Test
    void processForgotPassword_ShouldReturnPage_WhenValidationFail() {

        ForgotPasswordDTO dto = new ForgotPasswordDTO();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = homeService.processForgotPasswordClient(dto, bindingResult, model, null);
        assertThat(result).isEqualTo("client/authentication/forgot-password");
        verifyNoInteractions(forgotPasswordService);
    }
    @Test
    void processForgotPassword_ShouldShowSuccessMessage() {

        ForgotPasswordDTO dto = new ForgotPasswordDTO();
        dto.setEmail("test@gmail.com");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(forgotPasswordService.processForgotPassword("test@gmail.com")).thenReturn(true);
        String result = homeService.processForgotPasswordClient(dto, bindingResult, model, null);
        assertThat(result).isEqualTo("client/authentication/forgot-password");
        verify(model).addAttribute(eq("successMessage"), contains("Mật khẩu mới"));
    }
    @Test
    void processForgotPassword_ShouldShowErrorMessage() {

        ForgotPasswordDTO dto = new ForgotPasswordDTO();
        dto.setEmail("abc@gmail.com");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(forgotPasswordService.processForgotPassword(anyString())).thenReturn(false);
        String result = homeService.processForgotPasswordClient(dto, bindingResult, model, null);
        assertThat(result).isEqualTo("client/authentication/forgot-password");
        verify(model).addAttribute(eq("errorMessage"), contains("Email không tồn tại"));
    }
    @Test
    void showUserupdateClient_ShouldLoadUserInfo() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setFullName("Test");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        when(userService.getbyEmail("test@gmail.com")).thenReturn(user);
        String result = homeService.showUserupdateClient(model, request);
        assertThat(result).isEqualTo("client/authentication/updateuser");
        verify(model).addAttribute(eq("Userupdate"), any(Userupdate.class));
    }
    @Test
    void settingUserClient_ShouldReturnForm_WhenValidationFail() {

        Userupdate dto = new Userupdate();
        when(bindingResult.hasErrors()).thenReturn(true);
        String result = homeService.SettingUserClient(dto, bindingResult, request);
        assertThat(result).isEqualTo("client/authentication/updateuser");
    }
    @Test
    void settingUserClient_ShouldUpdateUserSuccessfully() {

        Userupdate dto = new Userupdate();
        dto.setEmail("test@gmail.com");
        dto.setFullName("New Name");
        dto.setPhone("123");
        dto.setAddress("Hai Phong");
        User user = new User();
        user.setEmail("test@gmail.com");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.getbyEmail("test@gmail.com")).thenReturn(user);
        when(request.getSession(false)).thenReturn(session);
        String result = homeService.SettingUserClient(dto, bindingResult, request);
        assertThat(result).isEqualTo("redirect:/");
        verify(userService).handleSaveUser(user);
        verify(session).setAttribute("avatar", user.getAvatar());
    }
    @Test
    void settingUserClient_ShouldEncodePassword_WhenPasswordChanged() {

        Userupdate dto = new Userupdate();

        dto.setEmail("test@gmail.com");
        dto.setPassword("newPassword");

        User user = new User();
        user.setPassword("oldPassword");

        when(bindingResult.hasErrors()).thenReturn(false);

        when(userService.getbyEmail(anyString())).thenReturn(user);

        when(passwordEncoder.encode("newPassword")).thenReturn("ENCODED");

        when(request.getSession(false)).thenReturn(session);

        homeService.SettingUserClient(dto, bindingResult, request);

        assertThat(user.getPassword()).isEqualTo("ENCODED");
        verify(passwordEncoder).encode("newPassword");
    }
    @Test
    void settingUserClient_ShouldUpdateAvatar() {

        Userupdate dto = new Userupdate();

        dto.setEmail("test@gmail.com");
        dto.setAvatar("newAvatar");
        dto.setAvatarPublicId("newPublicId");
        dto.setAvatarResourceType("image");

        User user = new User();

        user.setAvatarPublicId("oldPublicId");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.getbyEmail(anyString())).thenReturn(user);

        when(request.getSession(false)).thenReturn(session);
        homeService.SettingUserClient(dto, bindingResult, request);
        verify(temporaryUpload).markAsUnused("oldPublicId");
        verify(temporaryUpload).markAsUsed("newPublicId");

    }
    @Test
    void showOrderHistory_ShouldReturnOrderHistory() {

        User user = new User();

        Page<Order> page = new PageImpl<>(List.of(new Order()));
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        when(userService.getbyEmail(anyString())).thenReturn(user);
        when(userService.getlistHistory(user, 0, 6)).thenReturn(page);
        when(userService.toDTO(any(Order.class))).thenReturn(new OrderHistoryDTO());
        String result = homeService.showOrderHistoryClient("0", model, request);
        assertThat(result).isEqualTo("client/cart/orderhistory");
    }
    @Test
    void showOrderHistory_ShouldDefaultPageZero_WhenInvalidPage() {

        User user = new User();

        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        when(userService.getbyEmail(anyString())).thenReturn(user);
        when(userService.getlistHistory(user, 0, 6)).thenReturn(page);
        homeService.showOrderHistoryClient("abc", model, request);
        verify(userService).getlistHistory(user, 0, 6);
    }
    @Test
    void cancelOrder_ShouldReturnOkResponse() {

        User user = new User();
        user.setId(1L);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        when(userService.getbyEmail(anyString())).thenReturn(user);
        when(userService.cancelOrderDetailAjax(10L, 1L)).thenReturn(data);
        ResponseEntity<?> response = homeService.cancelOrderDetailAjaxClient(10L, request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void cancelOrder_ShouldReturnBadRequest_WhenExceptionOccurs() {

        User user = new User();
        user.setId(1L);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("test@gmail.com");
        when(userService.getbyEmail(anyString())).thenReturn(user);
        when(userService.cancelOrderDetailAjax(anyLong(), anyLong())).thenThrow(new RuntimeException("Order invalid"));
        ResponseEntity<?> response = homeService.cancelOrderDetailAjaxClient(1L, request);
        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
    }


}
