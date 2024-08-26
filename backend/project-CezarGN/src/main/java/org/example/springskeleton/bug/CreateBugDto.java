package org.example.springskeleton.bug;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBugDto {

    private String name;

    private String description;

    private Long developer;

    private String status;
}
