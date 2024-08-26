package org.example.springskeleton.bug;

import org.example.springskeleton.bug.BugDto;
import org.example.springskeleton.bug.Bug;
import org.example.springskeleton.bug.BugStatus;
import org.example.springskeleton.bug.EStatus;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BugMapper {
    Bug toEntity(BugDto bugDto);
    BugDto toDto(Bug bug);
    List<BugDto> toDtos(List<Bug> bugs);

    default Seniority seniorityMap(String value) {
        return Seniority.builder()
                .seniority(ESeniority.valueOf(value))
                .build();
    }

    default String map(Seniority value) {
        return value.getSeniority().toString();
    }

    default BugStatus bugStatusMap(String value) {
        return BugStatus.builder()
                .status(EStatus.valueOf(value))
                .build();
    }

    default String map(BugStatus value) {
        return value.getStatus().toString();
    }
}
