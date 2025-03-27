package com.reveture.Project1.service;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.AccountType;
import com.reveture.Project1.repository.AccountRepository;
import com.reveture.Project1.repository.AccountTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterAccount_Success(){
        //preparams los datos y los mocks
        AccountDTO dto = new AccountDTO("test@mail.com", "12345678", 1L);
        AccountType userType = new AccountType(1L, "User");

        when(accountRepository.existsByEmail("test@mail.com")).thenReturn(false);
        when(accountTypeRepository.findById(1L)).thenReturn(Optional.of(userType));
        when(passwordEncoder.encode("12345678")).thenReturn("hashedPass");

        Account expected = new Account(1L, "test@mail.com", "hashedPass", userType);
        when(accountRepository.save(any(Account.class))).thenReturn(expected);

        //Act llamamos al metodo real
        Account result = accountService.registerAccount(dto);

        // Assert se verifica el resltado
        assertNotNull(result);
        assertEquals("test@mail.com", result.getEmail());
        assertEquals("User", result.getAccountType().getType());

    }


    @Test
    void testFindAccountById_ShouldReturnAccount() {
        Long accountId = 1L;
        Account mockAccount = new Account();
        mockAccount.setId(accountId);
        mockAccount.setEmail("test@example.com");
        //Creamos una cuenta falsa (mockAccount) Esto simula una cuenta existente en la base de datos.
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        /*
        * “Si alguien llama a findById(1L), no vayas a buscar a la base de
        *  datos real. En vez de eso, devuelve este Optional con mi mockAccount”.
        * */
        Account result = accountService.getAccountById(accountId);
        //Llamamos al método real del servicio con el accountId que preparamos. Como está mockeado, nos va a devolver el mockAccount.
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(accountRepository).findById(accountId);
    }



    @Test
    void testUpdateAccount_Success() {
        Long accountId = 1L;

        Account existingAccount = new Account();
        existingAccount.setId(accountId);
        existingAccount.setEmail("old@example.com");
        existingAccount.setPassword("oldPassword");

        AccountDTO updatedDTO = new AccountDTO();
        updatedDTO.setEmail("new@example.com");
        updatedDTO.setPassword("newPassword");

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode("newPassword")).thenReturn("hashedNewPassword");

        Account result = accountService.updateAccount(accountId, updatedDTO);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        assertEquals("hashedNewPassword", result.getPassword());

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(existingAccount);
    }


}


/**
 *

 Se llama así porque está dividido en tres partes:
 Fase	        Qué hace	                                    Equivalente en código
 Arrange	    Preparar todo lo necesario (datos, mocks)	    Crear DTOs, entidades, when(...)
 Act	        Ejecutar la acción a probar	                    Llamar al método real del servicio
 Assert	        Verificar que el resultado sea el esperado	    assertEquals(...), verify(...)


 1. 🧰 Arrange (Preparación)

 Aquí defines y configuras todo lo que necesitas para simular el entorno:

 Instancias de datos (DTO, Entity, etc.).

 Comportamiento de los mocks (when(...).thenReturn(...)).

 Simulación de respuestas del repositorio o servicios externos.

 2. ⚙️ Act (Acción)

 Llamas al método real que quieres probar. Este es el corazón del test.

 3. ✅ Assert (Verificación)

 Aquí revisas si el resultado fue lo que esperabas. Puedes:

 Comparar valores (assertEquals, assertTrue, etc.).

 Verificar llamadas a métodos (verify(...)).
 * **/