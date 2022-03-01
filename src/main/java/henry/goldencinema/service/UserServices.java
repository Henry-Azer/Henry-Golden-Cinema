package henry.goldencinema.service;

import henry.goldencinema.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public interface UserServices {

    User addUser(User user) throws Exception;

    User updateUser(User user) throws Exception;

    void deleteUserById(String id) throws Exception;

    User getUserById(String id) throws UsernameNotFoundException;

    User getUserByEmail(String email) throws UsernameNotFoundException;

    Collection<User> getAllUsers() throws Exception;

}
