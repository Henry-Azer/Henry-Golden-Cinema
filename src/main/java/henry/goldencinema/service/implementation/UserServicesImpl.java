package henry.goldencinema.service.implementation;

import henry.goldencinema.entity.user.User;
import henry.goldencinema.entity.enums.ERole;
import henry.goldencinema.repository.RoleRepository;
import henry.goldencinema.repository.UserRepository;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Collection<User>> getAllUsers() {
        return Optional.of(userRepository.findAll());
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.of(userRepository.findUserById(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.of(userRepository.findUserByEmail(email));
    }

    @Override
    public Optional<User> addUser(User user) {
        user.getRoles().add(roleRepository.findRoleByName(ERole.ROLE_USER));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> updateUser(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
