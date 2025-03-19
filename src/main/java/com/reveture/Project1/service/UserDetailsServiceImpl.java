package com.reveture.Project1.service;

import com.reveture.Project1.entity.Account;
import com.reveture.Project1.repository.AccountRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

       // log.debug("Contrasena cifrada ******************** ", account.getPassword());

        System.out.println(" Usuario encontrado en BD: " + account.getEmail());
        System.out.println(" Contraseña cifrada en BD: " + account.getPassword());
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(account.getAccountType().getType()) // El rol debe ser un String válido
                .build();
    }
}

/*

UserDetailsServiceImpl es una clase fundamental en Spring Security que se encarga
de buscar los usuarios en la base de datos y devolver su información de autenticación.

Cuando un usuario intenta iniciar sesión, Spring Security llama automáticamente a este
servicio para validar las credenciales.

Buscar el usuario en la base de datos.
Verificar que el usuario exista.
Cargar su contraseña cifrada.
Comparar la contraseña ingresada con la guardada en la BD.
Autenticar al usuario si las credenciales son correctas.


*/