package project.wanna_help.Activity;

public enum Status {
    PUBLISHED("Published"),
    IN_PROGRESS("In Progress"),

    ARCHIVE("Archive");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

