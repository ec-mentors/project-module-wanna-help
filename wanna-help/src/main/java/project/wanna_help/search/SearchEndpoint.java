package project.wanna_help.search;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.ExperienceLevel;
import project.wanna_help.profile.persistence.domain.Volunteer;


import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchEndpoint {

    private final SearchService searchService;


    public SearchEndpoint(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/showAvailable")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<VolunteerDTO> showAllVolunteers() {
        return searchService.showAvailableVolunteers();
    }

    @PostMapping("/showSearched")
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    List<VolunteerDTO> showSearchedVolunteers(@RequestBody String searchedWord) {
        return searchService.showSearchedVolunteers(searchedWord);
    }
    @Secured({"ROLE_ORGANIZATION", "ROLE_INDIVIDUAL"})
    @GetMapping("/{experienceLevel}")
    List<Volunteer> getByExperience(@PathVariable ExperienceLevel experienceLevel) {
        return searchService.searchByExperienceLevel(experienceLevel);
    }
}