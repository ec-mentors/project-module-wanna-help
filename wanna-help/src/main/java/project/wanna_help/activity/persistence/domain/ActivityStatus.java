package project.wanna_help.activity.persistence.domain;

public enum ActivityStatus {
    PUBLISHED("Published"),
    IN_PROGRESS("In Progress"),

    ARCHIVE("Archive");

    private String value;

    ActivityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

