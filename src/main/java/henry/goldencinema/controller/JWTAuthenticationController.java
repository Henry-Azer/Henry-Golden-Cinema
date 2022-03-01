package henry.goldencinema.controller;

import henry.goldencinema.entity.User;
import henry.goldencinema.entity.requests.JWTRequest;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.entity.responses.JWTResponse;
import henry.goldencinema.security.jwt.JWTUtil;
import henry.goldencinema.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    public ApiResponse loginHandler(@RequestBody JWTRequest body) {
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authenticationManager.authenticate(authInputToken);
            String jwtToken = jwtUtil.generateToken(body.getEmail());

            User loggedUser = userServices.getUserByEmail(body.getEmail());
            return new ApiResponse(200, LocalDateTime.now().toString(), "Logged in successfully.",
                    new JWTResponse(jwtToken, loggedUser));

        } catch (AuthenticationException authExc) {
            return new ApiResponse(500, LocalDateTime.now().toString(), authExc.getMessage(), "");
        }
    }

}
