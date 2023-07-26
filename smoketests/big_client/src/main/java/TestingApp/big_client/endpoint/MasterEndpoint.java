package TestingApp.big_client.endpoint;

import TestingApp.big_client.domain.Activity;
import TestingApp.big_client.client.MasterClient;
import TestingApp.big_client.domain.AppUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MasterEndpoint {

    private final MasterClient masterClient;


    public MasterEndpoint(MasterClient masterClient) {
        this.masterClient = masterClient;
    }

    public String TestId = "";

    @GetMapping("/registervolunteer")
    AppUser AddUser(){
        return masterClient.registerVolunteer();
    }

    @GetMapping("/registerorganization")
    AppUser AddOrganization(){
        return masterClient.registerOrganization();
    }

    @GetMapping("/addactivity")
    Activity Addactivity(){
        return masterClient.createActivity();
    }

    @PutMapping("/activityapply/{id}")
    void applyActivity(@PathVariable Long id){
         masterClient.applyForActivity(id);
    }

    @GetMapping("/loginuser")
    String LoginUser(){
        return masterClient.loginUser();
    }


}
