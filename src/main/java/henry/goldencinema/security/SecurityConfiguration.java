package henry.goldencinema.security;

import henry.goldencinema.security.filter.AuthoritiesLoggingAtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().httpBasic()
                .and()
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);

        http
                .authorizeRequests()
                // Authentication matchers
                .antMatchers("/api/auth/**").permitAll().anyRequest().authenticated();
//                // Review matchers
//                .antMatchers("/api/review/**").permitAll()
//                // Movie matchers
//                .antMatchers("/api/movie/all", "/api/movie/now-play", "/api/movie/title", "/api/movie/id").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/movie").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/movie").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/movie/{id}").hasRole("ADMIN")
//                // User matchers
//                .antMatchers("/api/user/all").permitAll()
//                .antMatchers("/api/user/id/{id}").hasRole("ADMIN")
//                .antMatchers("/api/user/{email}").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
//                .antMatchers(HttpMethod.PUT, "/api/user").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/user/{id}").hasAnyRole("USER", "ADMIN")
//                // Ticket matchers
//                .antMatchers("/api/ticket/**").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/api/ticket/username/{username}").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/ticket").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.PUT, "/api/ticket").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/api/ticket/{id}").hasAnyRole("USER", "ADMIN");

//        http.cors().configurationSource(CorsConfigurationSource());
    }

/*
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
 */

//    @Bean
//    protected DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(authenticationServices);
//        auth.setPasswordEncoder(passwordEncoder());
//
//        return auth;
//    }

}