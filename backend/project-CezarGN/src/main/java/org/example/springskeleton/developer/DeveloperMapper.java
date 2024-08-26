package org.example.springskeleton.developer;

import org.example.springskeleton.developer.DeveloperDto;
import org.example.springskeleton.developer.Developer;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    Developer toEntity(DeveloperDto developerDto);
    DeveloperDto toDto(Developer developer);

    List<DeveloperDto> toDtos(List<Developer> developers);

    default Seniority map(String value) {
        return Seniority.builder()
                .seniority(ESeniority.valueOf(value))
                .build();
    }

    default String map(Seniority value) {
        return value.getSeniority().toString();
    }
}
