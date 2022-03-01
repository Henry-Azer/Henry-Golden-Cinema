package henry.goldencinema.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTUserDetailsService {

    UserDetails loadUserByUsername(String email);

    String getCurrentLoggedUser();

    boolean isUserAuthenticated();
}
