package org.example.springskeleton.project;

import org.example.springskeleton.bug.BugStatus;
import org.example.springskeleton.bug.EStatus;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project toEntity(ProjectDto projectDto);
    ProjectDto toDto(Project project);
    List<ProjectDto> toDtos(List<Project> projects);

    default Seniority map(String value) {
        return Seniority.builder()
                .seniority(ESeniority.valueOf(value))
                .build();
    }

    default String map(Seniority value) {
        return value.getSeniority().toString();
    }

    default BugStatus bugMap(String value){
        return BugStatus.builder()
                .status(EStatus.valueOf(value))
                .build();
    }

    default String bugMap(BugStatus bugStatus){
        return bugStatus.getStatus().toString();
    }
}
