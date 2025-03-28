package com.reveture.Project1.service;

import com.reveture.Project1.dto.UserProfileDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.UserProfile;
import com.reveture.Project1.repository.AccountRepository;
import com.reveture.Project1.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void testAddUserDeails_Success(){
        //Arrange preparacion de datos y mocks
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        //se crea un account y se le asigna un accountid

        //este dto representa los datos que el usuario enviara desde el front para
        //crear su perfil
        UserProfileDTO dto = new UserProfileDTO(
            accountId,
            "Kimberley",
            "Test",
            "1234567890",
            LocalDate.of(2000,1,1),
            720
        );

        //se usara como valor para el retorno
        UserProfile savedProfile = new UserProfile(
                1L,
                account,
                "Kimberley",
                "Test",
                "1234567890",
                LocalDate.of(2000, 1, 1),
                720
        );

        // Simula que el repositorio encuentra una cuenta con ese ID.
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        //Simula que no existe aún un perfil para esa cuenta (es decir, se puede crear uno nuevo).
        when(userProfileRepository.existsByAccount(account)).thenReturn(false);
        //Simula que al guardar el perfil, el repositorio devuelve el objeto guardado
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(savedProfile);

        // Se llama al método real del servicio con el DTO que armamos antes. Aquí es donde ocurre la lógica que queremos testear.
        UserProfile result = userProfileService.addUserDetails(dto);

        //Asegura que el metodo no sea null
        assertNotNull(result);

        assertEquals("Kimberley", result.getFirstName());
        assertEquals("Test", result.getLastName());
        assertEquals(720, result.getCreditScore());

        verify(accountRepository).findById(accountId);
        verify(userProfileRepository).existsByAccount(account);
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testGetUserByAccountId_Success() {
        Long accountId = 1L;
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("Chris");
        userProfile.setLastName("Tester");
        userProfile.setCreditScore(700);

        when(userProfileRepository.findByAccountId(accountId)).thenReturn(Optional.of(userProfile));

        UserProfile result = userProfileService.getUserByAccountId(accountId);

        assertNotNull(result);
        assertEquals("Chris", result.getFirstName());
        assertEquals(700, result.getCreditScore());

        verify(userProfileRepository).findByAccountId(accountId);
    }


    @Test
    void testUpdateUserProfile_Success() {
        Long profileId = 1L;

        UserProfile existingProfile = new UserProfile();
        existingProfile.setId(profileId);
        existingProfile.setFirstName("Old");
        existingProfile.setLastName("Name");
        existingProfile.setPhoneNumber("1111111111");
        existingProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existingProfile.setCreditScore(600);

        UserProfileDTO updatedDTO = new UserProfileDTO(
                null, // No se necesita el accountId para actualizar
                "NewFirst",
                "NewLast",
                "9999999999",
                LocalDate.of(1995, 5, 5),
                750
        );

        when(userProfileRepository.findById(profileId)).thenReturn(Optional.of(existingProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(inv -> inv.getArgument(0));

        UserProfile result = userProfileService.updateUserProfile(profileId, updatedDTO);

        assertNotNull(result);
        assertEquals("NewFirst", result.getFirstName());
        assertEquals("NewLast", result.getLastName());
        assertEquals("9999999999", result.getPhoneNumber());
        assertEquals(LocalDate.of(1995, 5, 5), result.getDateOfBirth());
        assertEquals(750, result.getCreditScore());

        verify(userProfileRepository).findById(profileId);
        verify(userProfileRepository).save(existingProfile);
    }


}
