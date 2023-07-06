package project.wanna_help.search;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.wanna_help.appuser.persistence.domain.AppUser;
import project.wanna_help.appuser.persistence.domain.UserRole;
import project.wanna_help.profile.communication.dto.VolunteerDTO;
import project.wanna_help.profile.persistence.domain.VisibilityStatus;
import project.wanna_help.profile.persistence.domain.Volunteer;
import project.wanna_help.profile.persistence.repository.VolunteerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @MockBean
    VolunteerRepository volunteerRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    SecurityFilterChain securityFilterChain;


    @Test
    void showAvailableVolunteers() {
        Volunteer volunteer1 = new Volunteer(new AppUser("eodod@fm.com", "Bobiuu23", "ddweewewew3", UserRole.VOLUNTEER, "Roli Teron"));
        Volunteer volunteer2 = new Volunteer(new AppUser("eodooood@fm.com", "Bobooiuu23", "ddweewooewew3", UserRole.VOLUNTEER, "Rolioo Teron"));
        List<Volunteer> volunteerList = new ArrayList<>();
        volunteerList.add(volunteer1);
        volunteerList.add(volunteer2);

        Mockito.when(volunteerRepository.findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE)).thenReturn(volunteerList);

        searchService.showAvailableVolunteers();
        Mockito.verify(volunteerRepository).findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE);

    }

    @Test
    void showSearchedVolunteers() {

        Volunteer volunteer1 = new Volunteer(new AppUser("eodod@fm.com", "Bobiuu23", "ddweewewew3", UserRole.VOLUNTEER, "Roli Teron"));
        Volunteer volunteer2 = new Volunteer(new AppUser("eodooood@fm.com", "Bobooiuu23", "ddweewooewew33", UserRole.VOLUNTEER, "Rolioo Teron"));
        Volunteer volunteer3 = new Volunteer(new AppUser("eodoiiid@fm.com", "Boxxxuu99", "xxxxxxxxx3", UserRole.VOLUNTEER, "Roli Teron"), "running; singing; skiing;", null, null, null);
        Volunteer volunteer4 = new Volunteer(new AppUser("eodooxxxood@fm.com", "Bobxxxuu12", "yyyyyyyy3", UserRole.VOLUNTEER, "Rolioo Teron"), "fighting; flying; boxing;", null, null, null);
        List<Volunteer> volunteerList = new ArrayList<>();
        volunteerList.add(volunteer1);
        volunteerList.add(volunteer2);
        volunteerList.add(volunteer3);
        volunteerList.add(volunteer4);
        Mockito.when(volunteerRepository.findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE)).thenReturn(volunteerList);
        List<VolunteerDTO> result = searchService.showSearchedVolunteers("boxi");
        Mockito.verify(volunteerRepository).findAllByVisibilityStatusOrderByAppUser_Username(VisibilityStatus.VISIBLE);
        var actual = result.size();
        var expected = 1;
        assertEquals(actual, expected);
    }
}

