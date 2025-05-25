package com.aitIsfoul.hotel.config;

import com.aitIsfoul.hotel.entity.Customer;
import com.aitIsfoul.hotel.entity.Permission;
import com.aitIsfoul.hotel.entity.User;
import com.aitIsfoul.hotel.repository.CustomerRepository;
import com.aitIsfoul.hotel.repository.PermissionRepository;
import com.aitIsfoul.hotel.repository.UserRepository;
import com.aitIsfoul.hotel.utils.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors().and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/refresh-token","/customers/add","/rooms/search/hotel","/rooms/room/**","/rooms/isRoomAvailable","/customers/add","/email/contact").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(seas -> seas.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider p=new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService());
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                log.info("Attempting to load user with username: {}", username);

                // Fetch the user by username
                Optional<User> userOptional = userRepository.findByEmail(username);
                Optional<Customer> customer = customerRepository.findByEmail(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    log.info("User found with username: {}", username);

                    // Get permissions for the user's role
                    List<Permission> permissions = permissionRepository.findByRoleId(user.getRole().getId());
                    log.debug("Found {} permissions for user: {}", permissions.size(), username);

                    // Convert permissions to SimpleGrantedAuthority
                    Collection<SimpleGrantedAuthority> authorities = permissions.stream()
                            .filter(permission -> "Y".equalsIgnoreCase(permission.getIsPossible()))  // Only include active permissions
                            .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName().name()))  // Use Enum's name()
                            .collect(Collectors.toList());

                    log.debug("Assigned authorities: {}", authorities);

                    // Return Spring Security's User object with username, password, and authorities
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            authorities
                    );
                } else if (customer.isPresent()) {
                    Customer cus = customer.get();
                    log.info("Customer found with username: {}", username);

                    // Return Spring Security's User object with username, password, and authorities
                    return new org.springframework.security.core.userdetails.User(
                            cus.getEmail(),
                            cus.getPassword(),
                            new ArrayList<>()
                    );
                } else {
                    log.warn("User not found with username: {}", username);
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
            }
        };
    }
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString(); // fallback
            }
        }
        return null;
    }




    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // apply to all endpoints
                        .allowedOrigins("http://188.34.188.31:8080") // your frontend URL
                        .allowedMethods("GET", "POST", "PUT","PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
