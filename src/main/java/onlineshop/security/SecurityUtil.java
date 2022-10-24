package onlineshop.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {
    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder(12).encode(password);
    }
}
