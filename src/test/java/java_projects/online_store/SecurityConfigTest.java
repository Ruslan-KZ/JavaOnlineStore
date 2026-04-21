package java_projects.online_store;

import java_projects.online_store.controller.UserApiController;
import java_projects.online_store.security.JwtAuthFilter;
import java_projects.online_store.security.JwtAuthenticationEntryPoint;
import java_projects.online_store.service.JwtService;
import java_projects.online_store.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    JwtAuthFilter jwtAuthFilter;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // Без токена — должен вернуть 401
    @Test
    void protectedEndpoint_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized());
    }

    // С пользователем — должен пройти
    @Test
    @WithMockUser(roles = "USER")
    void protectedEndpoint_withMockUser_returns200() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    // Auth endpoints открыты без токена
    @Test
    void authEndpoint_withoutToken_returns200orOther_notUnauthorized() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content("{\"email\":\"t@t.com\",\"password\":\"123\"}"))
                .andExpect(status().isNot(401));
    }
}