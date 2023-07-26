package TestingApp.big_client.client;

import TestingApp.big_client.domain.Activity;
import TestingApp.big_client.domain.AppUser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class MasterClient {
    private final RestTemplate restTemplate;

    public MasterClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    public AppUser registerVolunteer() {
        return restTemplate.postForObject("http://localhost:9100/users", new AppUser("epampusie@gmail.com","xpampusik69","Kukuliexxx69", "VOLUNTEER","erik xxx havlusik")
                , AppUser.class);
    }

    public AppUser registerOrganization() {
        return restTemplate.postForObject("http://localhost:9100/users", new AppUser("organization@gmail.com","organiz992","Organization99", "ORGANIZATION","Unicef London")
                , AppUser.class);
    }

    public Activity createActivity() {
        //LocalDate startdate = LocalDate.of(2020, 1, 8);
        //LocalDate enddate = LocalDate.of(2023, 1, 8); it worked also with this just had problems to parse back

        Activity niceactivity = new Activity("Help", "Help to people", "Dancing; " ,"12.12.2023","11.12.2024");
        HttpEntity<Activity> request =
                new HttpEntity<>(niceactivity, createHeaders("organiz992", "Organization99"));
        return restTemplate.postForObject("http://localhost:9100/activities/add", request, Activity.class);
    }

    public void applyForActivity(Long Id) {
        restTemplate.put("http://localhost:9100/activities/" + Id.toString() + "/apply", new HttpEntity<>(createHeaders("xpampusik69", "Kukuliexxx69")));
    }

    public String loginUser() {
        return restTemplate.postForObject("http://localhost:9100/users/login", new HttpEntity<>(createHeaders("xpampusik69", "Kukuliexxx69"))
                , String.class);
    }
}
