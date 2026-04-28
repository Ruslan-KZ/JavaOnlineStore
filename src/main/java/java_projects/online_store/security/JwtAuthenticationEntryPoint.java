package java_projects.online_store.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // String path = request.getRequestURI();

        // if (path.startsWith("/h2-console")) {
        //     response.sendRedirect("/h2-console/login.jsp");
        //     return;
        // }

        String tokenError = (String) request.getAttribute("token_error");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String message = "token_expired".equals(tokenError) ? "Token expired" : "Unauthorized";
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}