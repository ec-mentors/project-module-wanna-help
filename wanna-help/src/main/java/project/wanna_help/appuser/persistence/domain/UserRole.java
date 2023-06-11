package project.wanna_help.appuser.persistence.domain;

public enum UserRole {
    VOLUNTEER("ROLE_VOLUNTEER"),
    INDIVIDUAL("ROLE_INDIVIDUAL"),
    ORGANIZATION("ROLE_ORGANIZATION");

   private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
