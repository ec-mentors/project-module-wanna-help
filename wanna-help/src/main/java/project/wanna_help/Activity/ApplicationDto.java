package project.wanna_help.Activity;

public class ApplicationDto {
    private Long volunteerId;
    private Long helpSeekerId;

    private String comment;

    public ApplicationDto(Long volunteerId, Long helpSeekerId) {
        this.volunteerId = volunteerId;
        this.helpSeekerId = helpSeekerId;
    }

    public ApplicationDto(Long volunteerId, Long helpSeekerId, String comment) {
        this.volunteerId = volunteerId;
        this.helpSeekerId = helpSeekerId;
        this.comment = comment;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public Long getHelpSeekerId() {
        return helpSeekerId;
    }

    public String getComment() {
        return comment;
    }
}