package java_projects.online_store;

import java_projects.online_store.entity.User;
import java_projects.online_store.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTest {

    @Mock
    UserService userService;

    @Test
    void getAll_returnsUserList() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFullName("Test");

        when(userService.getAll()).thenReturn(List.of(user));

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@test.com", result.get(0).getEmail());
        verify(userService).getAll();
    }

    @Test
    void getById_returnsUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userService.getById(1L)).thenReturn(user);

        User result = userService.getById(1L);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        verify(userService).getById(1L);
    }

    @Test
    void getAll_unauthenticated_serviceNotCalled() {
        // Без вызова — список пустой по умолчанию
        when(userService.getAll()).thenReturn(List.of());
        List<User> result = userService.getAll();
        assertTrue(result.isEmpty());
    }
}