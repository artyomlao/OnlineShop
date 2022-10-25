package onlineshop.controller;

import onlineshop.dto.UserRegDTO;
import onlineshop.model.User;
import onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
