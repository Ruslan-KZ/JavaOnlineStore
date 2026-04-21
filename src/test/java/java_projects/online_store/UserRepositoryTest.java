package java_projects.online_store;

import java_projects.online_store.entity.User;
import java_projects.online_store.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User buildUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFullName("Test User");
        user.setPasswordHash("hashed");
        user.setRegistrationDate(LocalDateTime.now());
        user.setStatus("active");
        return user;
    }

    @Test
    void save_andFindById_works() {
        User saved = userRepository.save(buildUser("a@test.com"));

        Optional<User> found = userRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("a@test.com", found.get().getEmail());
    }

    @Test
    void findByEmail_existingEmail_returnsUser() {
        userRepository.save(buildUser("b@test.com"));

        Optional<User> found = userRepository.findByEmail("b@test.com");

        assertTrue(found.isPresent());
        assertEquals("b@test.com", found.get().getEmail());
    }

    @Test
    void findByEmail_notFound_returnsEmpty() {
        Optional<User> found = userRepository.findByEmail("notexist@test.com");
        assertFalse(found.isPresent());
    }

    @Test
    void delete_removesUser() {
        User saved = userRepository.save(buildUser("c@test.com"));
        userRepository.deleteById(saved.getId());

        Optional<User> found = userRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}