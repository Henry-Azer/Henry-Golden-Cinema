package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.User;
import henry.goldencinema.entity.enums.ERole;
import henry.goldencinema.repository.RoleRepository;
import henry.goldencinema.repository.UserRepository;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User addUser(User user) throws Exception {
        if (userRepository.findUserByEmail(user.getEmail()) != null)
            throw new Exception("User already exist for email: " + user.getEmail());

        user.getRoles().add(roleRepository.findRoleByName(ERole.ROLE_USER));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        System.out.println("save user: " + user);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        isUserExist(user.getId(), null);

        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(String id) {
        isUserExist(id, null);

        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(String id) throws UsernameNotFoundException {
        isUserExist(id, null);

        return userRepository.findUserById(id);
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        isUserExist(null, email);

        return userRepository.findUserByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() throws Exception {
        if (userRepository.findAll() == null)
            throw new Exception("There are no users");

        return userRepository.findAll();
    }

    private void isUserExist(String id, String email) throws UsernameNotFoundException {
        if (id == null) {
            if (userRepository.findUserByEmail(email) == null)
                throw new UsernameNotFoundException("User not found for email: " + email);
        } else {
            if (userRepository.findUserById(id) == null)
                throw new UsernameNotFoundException("User not found for id: " + id);
        }
    }
}
