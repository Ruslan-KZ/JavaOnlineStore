package java_projects.online_store;

import java_projects.online_store.dto.AuthRequest;
import java_projects.online_store.entity.User;
import java_projects.online_store.repository.UserRepository;
import java_projects.online_store.service.JwtService;
import java_projects.online_store.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    UserService userService;

    // --- getAll ---
    @Test
    void getAll_returnsAllUsers() {
        User u1 = new User();
        u1.setEmail("a@a.com");
        User u2 = new User();
        u2.setEmail("b@b.com");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));

        List<User> result = userService.getAll();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    // --- getById ---
    @Test
    void getById_existingId_returnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        assertEquals("test@test.com", result.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void getById_notFound_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userService.getById(99L));
    }

    // --- create ---
    @Test
    void create_encodesPassword() {
        User user = new User();
        user.setEmail("new@test.com");
        user.setPasswordHash("plainPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        userService.create(user);

        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPasswordHash());
    }

    // --- delete ---
    @Test
    void delete_callsRepository() {
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }

    // --- register ---
    @Test
    void register_savesUserAndReturnsToken() {
        AuthRequest req = new AuthRequest();
        req.setEmail("reg@test.com");
        req.setPassword("pass123");

        when(passwordEncoder.encode("pass123")).thenReturn("hashed");
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(jwtService.generateToken(any())).thenReturn("jwt-token");

        var response = userService.register(req);

        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any());
    }
}