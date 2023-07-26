package TestingApp.big_client.domain;

import TestingApp.big_client.domain.AppUser;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
@Document
public class Activity {

    private String id;

    private String title;

    private String description;

    private String recommendedSkills;

    private String startDate;

    private String endDate;

    public Activity(String id, String title, String description, String recommendedSkills, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Activity(String title, String description, String recommendedSkills, String startDate, String endDate) {
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendedSkills() {
        return recommendedSkills;
    }

    public void setRecommendedSkills(String recommendedSkills) {
        this.recommendedSkills = recommendedSkills;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
