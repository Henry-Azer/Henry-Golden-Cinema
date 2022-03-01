package henry.goldencinema.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import henry.goldencinema.entity.responses.ApiResponse;
import henry.goldencinema.service.JWTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.equals("")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                OutputStream out = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(out,
                        new ApiResponse(HttpStatus.UNAUTHORIZED.value(),
                                LocalDateTime.now().toString(), "Invalid JWT Token in Bearer Header", ""));
                out.flush();

            } else {
                try {
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email,
                                    userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException jwtVerificationException) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    OutputStream out = response.getOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(out,
                            new ApiResponse(HttpStatus.UNAUTHORIZED.value(),
                                    LocalDateTime.now().toString(), "Invalid JWT token", ""));
                    out.flush();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}