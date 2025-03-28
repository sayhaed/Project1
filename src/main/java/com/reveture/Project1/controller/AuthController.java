package com.reveture.Project1.controller;

import com.reveture.Project1.dto.LoginRequestDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 🔥 Almacenar autenticación en SecurityContextHolder y en la sesión
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return ResponseEntity.ok("Login successful");
    }


    @GetMapping("/check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        System.out.println("🔥 Estado de la sesión: " + session.getId());
//        System.out.println("🔍 Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("No active session. Please log in.");
        }

        return ResponseEntity.ok("User is logged in: " + authentication.getName());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false); // 🔥 Obtener la sesión si existe
        if (session != null) {
            session.invalidate(); // 🔥 Invalidar la sesión
        }

        SecurityContextHolder.clearContext(); // 🔥 Limpiar la autenticación en SecurityContext

        // Eliminar cookie de sesión (opcional si se usa JSESSIONID)
       /*
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        */
        return ResponseEntity.ok("You have been logged out successfully.");
    }

    @GetMapping("/session-expired")
    public ResponseEntity<String> sessionExpired() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session expired, please login again.");
    }
}
