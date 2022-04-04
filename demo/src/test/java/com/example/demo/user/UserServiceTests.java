package com.example.demo.user;

import com.example.demo.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepo;
    @InjectMocks
    private UserService userService;

    @Test
    void getUser() {
        final Long id = 1L;
        final String email = "test@email.com";
        final String name = "Test Name";
        final String pwd = "123";
        User user = new User(id, email, name, pwd);

        when(userRepo.findById(id)).thenReturn(Optional.of(user));
        User foundUser = userService.getById(id);

        assertThat(foundUser.getEmail()).isEqualTo(email);
        assertThat(foundUser.getName()).isEqualTo(name);
    }

    @Test
    void cannotFindUser() {
        when(userRepo.findById(1L)).thenThrow(UserNotFoundException.class);
        assertThrows(UserNotFoundException.class, () -> userService.getById(1L));
    }
}
