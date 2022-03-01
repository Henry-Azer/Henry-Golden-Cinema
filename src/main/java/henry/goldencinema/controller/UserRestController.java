package henry.goldencinema.controller;

import henry.goldencinema.entity.User;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/all")
    public Collection<User> getAllUsers() throws Exception {
        return userServices.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) throws UsernameNotFoundException {
        return userServices.getUserByEmail(email);
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable String id) throws UsernameNotFoundException {
        return userServices.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
        User savedUser = userServices.addUser(user);

        return ResponseEntity.created(new URI("/user" + savedUser.getId())).body(savedUser);
    }

    @PutMapping()
    @PreAuthorize("#user.email = authentication.principal.username")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        User updatedUser = userServices.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id = userServicesImpl.getUserByEmail(authentication.principal.username).id")
    public ResponseEntity<User> deleteUser(@PathVariable String id) throws Exception {
        userServices.deleteUserById(id);

        return ResponseEntity.ok().build();
    }

}
