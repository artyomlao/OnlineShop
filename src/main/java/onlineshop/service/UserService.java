package onlineshop.service;

import onlineshop.dto.UserRegDTO;
import onlineshop.model.Role;
import onlineshop.model.Status;
import onlineshop.model.User;
import onlineshop.repository.UserRepository;
import onlineshop.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User wasn't found"));
    }

    public User registerUser(UserRegDTO userRegDTO) {
        User user = new User();

        user.setEmail(userRegDTO.getEmail());
        user.setPassword(SecurityUtil.encodePassword(userRegDTO.getPassword()));
        user.setName(userRegDTO.getName());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);

        return userRepository.save(user);
    }



    public boolean matchPassword(UserRegDTO userRegDTO) {
        return userRegDTO.getPassword()
                .equals(userRegDTO.getMatchingPassword());
    }
}
