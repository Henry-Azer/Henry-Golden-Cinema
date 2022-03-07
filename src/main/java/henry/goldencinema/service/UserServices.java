package henry.goldencinema.service;

import henry.goldencinema.entity.user.User;

import java.util.Collection;
import java.util.Optional;

public interface UserServices {

    Optional<Collection<User>> getAllUsers();

    Optional<User> getUserById(String id);

    Optional<User> getUserByEmail(String email);

    Optional<User> addUser(User user);

    Optional<User> updateUser(User user);

    void deleteUserById(String id);

}
