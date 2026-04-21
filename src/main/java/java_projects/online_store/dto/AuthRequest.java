package java_projects.online_store.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}