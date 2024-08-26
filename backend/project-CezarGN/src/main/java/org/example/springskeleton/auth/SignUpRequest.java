package org.example.springskeleton.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SignUpRequest {
    private String username;
    private String password;
    private String role;
    private String seniority;
}
