package onlineshop.controller;

import onlineshop.dto.UserRegDTO;
import onlineshop.model.Product;
import onlineshop.model.User;
import onlineshop.service.ProductService;
import onlineshop.service.UserLabelView;
import onlineshop.service.UserService;
import onlineshop.service.ConversionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public ShopController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginPost(@RequestBody UserAuthDTO userAuthDTO) {
//        try{
//            Authentication authentication = authenticationManager.authenticate
//                    (new UsernamePasswordAuthenticationToken(userAuthDTO.getUsername(), userAuthDTO.getPassword()));
//            System.out.println("nice");
//        } catch (AuthenticationException e) {
//            System.out.println("sth went wrong");
//            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
//        }
//        return ResponseEntity.ok("nice auth!");
//    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @GetMapping("/credits")
    public String getCredits() {
        return "credits";
    }

    @GetMapping("/account")
    public String accountPage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);

        UserLabelView userLabelView = new ConversionView();
        userLabelView.setUser(user);
        model.addAttribute("userLabelView", userLabelView);

        return "account";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("userDTO") UserRegDTO userDTO
            , BindingResult bindingResult) {
        if(!userService.matchPassword(userDTO)) {
            bindingResult.addError(new FieldError
                    ("error", "matchingPassword", "Пароли не соответствуют"));
            return "registration";
        }
        userService.registerUser(userDTO);

        return "redirect:/shop/login";
    }

    @GetMapping("/buy")
    public ResponseEntity<String> checkAdmin() {
        return new ResponseEntity<>("admin check", HttpStatus.OK);
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication) {
        return "home";
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
