package project.wanna_help.persistence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "title is mandatory")
    @Size(max = 40, message = "title must have a maximum of 40 characters")
    private String title;

    @NotBlank(message = "description is mandatory")
    private String description;

    @Pattern(regexp = ";", message = "skills should be divided whit ;")
    private String recommendedSkills;

    @NotBlank(message = "start date is mandatory")
    private LocalDate startDate;

    @NotBlank(message = "end date is mandatory")
    private LocalDate endDate;

    public Activity() {
    }

    public Activity(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Activity(String title, String description, String recommendedSkills, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.recommendedSkills = recommendedSkills;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

