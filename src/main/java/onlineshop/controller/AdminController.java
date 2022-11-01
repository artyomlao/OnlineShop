package onlineshop.controller;

import onlineshop.dto.ProductDTO;
import onlineshop.dto.UserEditDTO;
import onlineshop.dto.UserRegDTO;
import onlineshop.exception.UserDoesNotExist;
import onlineshop.model.Product;
import onlineshop.model.User;
import onlineshop.service.ProductService;
import onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public AdminController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "adminHome";
    }

    @GetMapping("/addProduct")
    public String addProductPage() {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getImagePath(), productDTO.getPrice());
        productService.addProduct(product);
        return "redirect:/admin/home";
    }

    @GetMapping("/managing")
    public String managing(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "managing";
    }

    @GetMapping("/managing/edit/{id}")
    public String editUserPage(@PathVariable("id") int id,
                           Model model) throws UserDoesNotExist {
        User userById = userService.findById(id);
        model.addAttribute("userDTO", userById);
        return "editUser";
    }

    @PostMapping("/managing/edit/{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("user") UserEditDTO userDTO) throws UserDoesNotExist {
        userService.editUser(id, userDTO);
        return "redirect:/admin/managing";
    }

    @PostMapping("/managing/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) throws UserDoesNotExist {
        userService.deleteUser(id);
        return "redirect:/admin/managing";
    }
}
