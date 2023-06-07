package project.wanna_help.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.ProfileService;
import project.wanna_help.persistence.domain.AppUser;

import java.util.Optional;

@RestController
@RequestMapping("/users/profile")
public class ProfileEndpoint {
    private final ProfileService service;

    public ProfileEndpoint(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    ResponseEntity<AppUser> getAccount(@PathVariable Long id) {
        Optional<AppUser> oAppUser = service.getAccountById(id);
        return oAppUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}/volunteer")
    ResponseEntity<Volunteer> getUserProfile(@PathVariable Long id) {
        Optional<Volunteer> oVolunteer = service.getProfileById(id);
        return oVolunteer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> editProfile(@PathVariable Long id, @RequestBody UserProfileDTO updatedUser) {
        Optional<AppUser> oAppUser = service.getAccountById(id);
        if (oAppUser.isPresent()) {
            AppUser existingUser = oAppUser.get();
            AppUser savedUser = service.updateProfile(existingUser, updatedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/skills")
    public ResponseEntity<String> updateUserSkills(@PathVariable Long id, @RequestBody String skills) {
        return ResponseEntity.ok(service.saveSkills(id, skills));
    }

}
