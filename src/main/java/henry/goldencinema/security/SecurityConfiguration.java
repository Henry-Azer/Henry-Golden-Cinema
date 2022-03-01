package henry.goldencinema.security;

import henry.goldencinema.security.jwt.JWTAuthenticationEntryPoint;
import henry.goldencinema.security.jwt.JWTFilter;
import henry.goldencinema.service.implementation.JWTUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private JWTUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private JWTAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().httpBasic().disable()
                .cors().configurationSource(CorsConfigurationSource());

        http
                .userDetailsService(jwtUserDetailsService)
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()
                // Authentication matchers
                .antMatchers("/api/auth/**").permitAll()
                // Review matchers
                .antMatchers("/api/review/**").permitAll()
                // Movie matchers
                .antMatchers("/api/movie/all", "/api/movie/now-play", "/api/movie/title", "/api/movie/id").permitAll()
                .antMatchers(HttpMethod.POST, "/api/movie").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/movie").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/movie/{id}").hasRole("ADMIN")
                // User matchers
                .antMatchers("/api/user/all").permitAll()
                .antMatchers("/api/user/id/{id}").hasRole("ADMIN")
                .antMatchers("/api/user/{email}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/user").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/user/{id}").hasAnyRole("USER", "ADMIN")
                // Ticket matchers
                .antMatchers("/api/ticket/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/ticket/username/{username}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/ticket").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/ticket").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/ticket/{id}").hasAnyRole("USER", "ADMIN");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected CorsConfigurationSource CorsConfigurationSource() {
        return request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();

            corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            corsConfig.setExposedHeaders(Collections.singletonList("Authorization"));
            corsConfig.setAllowedHeaders(Collections.singletonList("*"));
            corsConfig.setAllowedMethods(Collections.singletonList("*"));
            corsConfig.setAllowCredentials(true);
            corsConfig.setMaxAge(3600L);

            return corsConfig;
        };
    }
}