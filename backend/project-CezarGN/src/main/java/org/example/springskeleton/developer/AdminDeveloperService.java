package org.example.springskeleton.developer;

import lombok.RequiredArgsConstructor;
import org.example.springskeleton.developer.seniority.ESeniority;
import org.example.springskeleton.developer.seniority.Seniority;
import org.example.springskeleton.auth.user.AppUser;
import org.example.springskeleton.auth.user.AppUserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDeveloperService {
    private final DeveloperRepository developerRepository;
    private final SeniorityRepository seniorityRepository;
    private final DeveloperMapper developerMapper;
    private final AppUserMapper appUserMapper;

    public Page<DeveloperDto> getDevelopers(String username, String seniority, Pageable pageable) {
        ESeniority seniorityFilter = (seniority != null && !seniority.isEmpty()) ? ESeniority.valueOf(seniority) : null;
        return developerRepository.findByUsernameContainingAndSeniority(username, seniorityFilter, pageable).map(developerMapper::toDto);
    }


    public Optional<DeveloperDto> updateDeveloper(DeveloperDto developerDto, Long id) {
        if (developerRepository.existsById(id)) {
            Developer developer = getAndUpdateDeveloperEntity(developerDto, id);
            return Optional.of(developerMapper.toDto(developer));
        } else {
            return Optional.empty();
        }
    }

    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }

    public Page<DeveloperDto> getIdleDevelopers(Pageable pageable) {
        return developerRepository.getIdleDevelopers(pageable).map(developerMapper::toDto);
    }

    public Page<DeveloperDto> getDevelopersOnProjectAndIdle(Pageable pageable, Long id){
        return developerRepository.getDevelopersOnProjectAndIdle(pageable, id).map(developerMapper::toDto);
    }

    private Developer getAndUpdateDeveloperEntity(DeveloperDto developerDto, Long id) {
        Developer developer = developerRepository.findById(id).get();
        setDeveloperEntityAttributes(developerDto, developer);
        developerRepository.save(developer);
        return developer;
    }

    private void setDeveloperEntityAttributes(DeveloperDto developerDto, Developer developer) {
        setDeveloperAppUser(developerDto, developer);
        validateSeniority(developerDto, developer);
    }

    private void setDeveloperAppUser(DeveloperDto developerDto, Developer developer) {
        AppUser updatedAppUser = appUserMapper.toEntity(developerDto.getAppUser());
        updatedAppUser.setId(developer.getAppUser().getId());
        updatedAppUser.setRole(developer.getAppUser().getRole());
        updatedAppUser.setPassword(developer.getAppUser().getPassword());
        developer.setAppUser(updatedAppUser);
    }

    private void validateSeniority(DeveloperDto developerDto, Developer developer) {
        ESeniority eSeniority = ESeniority.valueOf(developerDto.getSeniority());
        Optional<Seniority> existingSeniority = seniorityRepository.findBySeniority(eSeniority);

        if (existingSeniority.isPresent()) {
            developer.setSeniority(existingSeniority.get());
        } else {
            throw new NoSuchElementException("That seniority does not exist");
        }
    }

}
