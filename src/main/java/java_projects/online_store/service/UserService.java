package java_projects.online_store.service;

import java_projects.online_store.entity.User;
import java_projects.online_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User updated) {
        User user = getById(id);
        user.setFullName(updated.getFullName());
        user.setPhone(updated.getPhone());
        user.setEmail(updated.getEmail());
        user.setStatus(updated.getStatus());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}