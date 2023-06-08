package project.wanna_help.Activity;

public class ApplicationDto {
    private Long volunteerId;
    private Long helpSeekerId;

    public ApplicationDto(Long volunteerId, Long helpSeekerId) {
        this.volunteerId = volunteerId;
        this.helpSeekerId = helpSeekerId;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public Long getHelpSeekerId() {
        return helpSeekerId;
    }
}
