package henry.goldencinema.service;

import henry.goldencinema.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserServices {

    void deleteUserById(String id);

    Optional<User> addUser(User user);

    Optional<User> updateUser(User user);

    Optional<Collection<User>> getAllUsers();

    Optional<User> getUserById(String id);

    Optional<User> getUserByEmail(String email);

}
