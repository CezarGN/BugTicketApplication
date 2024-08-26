package org.example.springskeleton.auth.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser toEntity(AppUserDto appUserDto);
    AppUserDto toDto(AppUser appUser);

    List<AppUserDto> toDtos(List<AppUser> appUsers);
}
