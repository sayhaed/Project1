package com.reveture.Project1.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Configuration //clase de configuracion de spring bu
public class SecurityConfig {


    //Define las reglas de seguridad para los endpoints.
   /* public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //SecurityFilterChain filtra las solicitudes HTTP y decide si el usuario puede acceder o no
        http
                .csrf(csrf -> csrf.disable())//CSRF (Cross-Site Request Forgery) es una protección para formularios en HTML.
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/admin/**").hasRole("Manager")  // Solo los ADMIN pueden acceder
                       // .requestMatchers("/api/user/**").hasRole("Regular")  // Solo los USER pueden acceder
                        .requestMatchers("/api/auth/**", "/api/accounts/register","/api/accounts").permitAll()  // Público
                        .anyRequest().authenticated() // Cualquier otra ruta requiere estar autenticado
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized - Please login\"}");
                        })
                );

        return http.build();
    }
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/accounts/register").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .securityContext(securityContext -> securityContext.securityContextRepository(securityContextRepository())) // 🔥 Habilitar almacenamiento de sesión
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // 🔥 Configurar la URL de logout
                        .logoutSuccessUrl("/api/auth/logout-success") // Redirigir al usuario después de cerrar sesión
                        .invalidateHttpSession(true) // 🔥 Invalidar la sesión
                        .deleteCookies("JSESSIONID") // Eliminar cookies de sesión
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized - Please login\"}");
                        })
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(Collections.singletonList(authProvider));
    }

    //Crea un cifrador de contraseñas con BCrypt, el cual se usa en UserDetailsServiceImpl
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository(); // Mantiene la autenticación en la sesión
    }

}

/*

Cuando el usuario intenta iniciar sesión (POST /api/auth/login), esto sucede internamente:

    Spring Security recibe la solicitud de login y pasa los datos a AuthenticationManager.
    AuthenticationManager llama a UserDetailsService.loadUserByUsername(email).
    Spring Security obtiene la contraseña cifrada de la BD.
    Spring usa passwordEncoder.matches(inputPassword, storedPassword) para compararlas.
    Si coinciden, el usuario se autentica.
    Si no coinciden, se lanza un BadCredentialsException.




*/