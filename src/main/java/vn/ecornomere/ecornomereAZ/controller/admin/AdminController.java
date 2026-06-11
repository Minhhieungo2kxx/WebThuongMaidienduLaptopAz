package vn.ecornomere.ecornomereAZ.controller.admin;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.ecornomere.ecornomereAZ.dto.request.ProductSales;
import vn.ecornomere.ecornomereAZ.model.entity.Order;
import vn.ecornomere.ecornomereAZ.service.AdminService;
import vn.ecornomere.ecornomereAZ.service.ItemService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;


    @GetMapping("/admin")
    public String getHomePage(Model model) {
        return adminService.getHomePageAdmin(model);
    }
}
