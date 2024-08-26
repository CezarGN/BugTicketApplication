package org.example.springskeleton.developer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springskeleton.auth.user.AppUserDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {
    private Long id;
    private AppUserDto appUser;
    private String seniority;
}
