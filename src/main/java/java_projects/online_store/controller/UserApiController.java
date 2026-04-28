package java_projects.online_store.controller;

import java_projects.online_store.dto.UserDto;
import java_projects.online_store.entity.User;
import java_projects.online_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.getAll().stream()
            .map(u -> new UserDto(
                u.getId(), u.getFullName(), u.getEmail(),
                u.getPhone(), u.getStatus(), u.getRegistrationDate()))
            .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        User u = userService.getById(id);
        return ResponseEntity.ok(new UserDto(
            u.getId(), u.getFullName(), u.getEmail(),
            u.getPhone(), u.getStatus(), u.getRegistrationDate()));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}