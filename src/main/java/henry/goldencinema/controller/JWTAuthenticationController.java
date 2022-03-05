package henry.goldencinema.controller;

import henry.goldencinema.entity.User;
import henry.goldencinema.entity.requests.JWTRequest;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.entity.responses.JWTResponse;
import henry.goldencinema.security.jwt.JWTUtil;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class JWTAuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@RequestBody JWTRequest body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authenticationManager.authenticate(authInputToken);
            String jwtToken = jwtUtil.generateToken(body.getEmail());

            Optional<User> loggedUser = userServices.getUserByEmail(body.getEmail());
            assert loggedUser.isPresent();

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.OK.value(), LocalDateTime.now().toString(),
                    "User logged in successfully", new JWTResponse(jwtToken, loggedUser.get())));

        } catch (AuthenticationException authExc) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(
                    HttpStatus.NOT_FOUND.value(), LocalDateTime.now().toString(), authExc.getMessage(), authExc));
        }
    }

}
