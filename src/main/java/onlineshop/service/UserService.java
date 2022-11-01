package onlineshop.service;

import onlineshop.dto.UserEditDTO;
import onlineshop.dto.UserRegDTO;
import onlineshop.exception.UserDoesNotExist;
import onlineshop.model.Role;
import onlineshop.model.Status;
import onlineshop.model.User;
import onlineshop.repository.UserRepository;
import onlineshop.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
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

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Integer id) throws UserDoesNotExist {
        return userRepository.findById(id).orElseThrow(() -> new UserDoesNotExist("User wasn't found"));
    }

    public void editUser(int id, UserEditDTO userDTO) throws UserDoesNotExist {
        User user = findById(id);

        user.setEmail(userDTO.getEmail());
        user.setStatus(Status.valueOf(userDTO.getStatus()));
        user.setRole(Role.valueOf(userDTO.getRole()));
        user.setName(userDTO.getName());

        userRepository.save(user);
    }

    public void deleteUser(int id) throws UserDoesNotExist {
        User user = findById(id);

        userRepository.delete(user);
    }
}
