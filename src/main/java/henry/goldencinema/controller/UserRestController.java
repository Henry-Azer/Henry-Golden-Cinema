package henry.goldencinema.controller;

import henry.goldencinema.entity.User;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        Optional<Collection<User>> users = userServices.getAllUsers();

        assert users.isPresent();
        if (users.get().isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), "Empty users list", ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(), "Users list", users));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userServices.getUserByEmail(email);

        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User not found for email: " + email, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "User found for email: " + email, user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> user = userServices.getUserById(id);

        if (user.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User not found for id: " + id, ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "User found for id: " + id, user));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        Optional<User> existedUser = userServices.getUserByEmail(user.getEmail());

        if (existedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User already exist for email: " + user.getEmail(), ""));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "User created successfully", userServices.addUser(user)));
    }

    @PutMapping()
    @PreAuthorize("#user.email = authentication.principal.username")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Optional<User> existedUser = userServices.getUserById(user.getId());

        if (existedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User not found", user));

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "User updated successfully", userServices.updateUser(user)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id = userServicesImpl.getUserByEmail(authentication.principal.username).get().id")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<User> existedUser = userServices.getUserById(id);

        if (existedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(),
                    "User not found for id: " + id, ""));

        userServices.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                HttpStatus.OK.value(), LocalDateTime.now().toString(),
                "User deleted successfully", ""));
    }

}
