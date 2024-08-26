package org.example.springskeleton.bug;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springskeleton.developer.DeveloperDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugDto {
    private Long id;
    private String name;
    private String description;

    private DeveloperDto developer;

    private String status;

}
