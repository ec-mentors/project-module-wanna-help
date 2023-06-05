package project.wanna_help.persistence.domain;

public enum Status {
    PUBLISHED("Published"),
    IN_PROGRESS("In Progress");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

