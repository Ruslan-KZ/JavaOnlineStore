package java_projects.online_store.service;

import java_projects.online_store.dto.AuthRequest;
import java_projects.online_store.dto.AuthResponse;
import java_projects.online_store.entity.User;
import java_projects.online_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User create(User user) {
        if (user.getPasswordHash() != null) {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        }
        return userRepository.save(user);
    }

    public User update(Long id, User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setFullName(updated.getFullName());
        user.setPhone(updated.getPhone());
        user.setStatus(updated.getStatus());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public AuthResponse register(AuthRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName() != null ? req.getFullName() : req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setStatus("active");
        user.setRegistrationDate(LocalDateTime.now()); // ← исправлено
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user));
    }

    public AuthResponse login(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        return new AuthResponse(jwtService.generateToken(user));
    }
}