package java_projects.online_store.security;

import java_projects.online_store.dto.AuthRequest;
import java_projects.online_store.dto.AuthResponse;
import java_projects.online_store.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request,
                                            HttpServletResponse response) {
        AuthResponse auth = userService.login(request);
        
        Cookie cookie = new Cookie("jwt", auth.getToken());
        cookie.setHttpOnly(true);  // JS не видит
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 час
        response.addCookie(cookie);
        
        return ResponseEntity.ok(auth);
    }

}