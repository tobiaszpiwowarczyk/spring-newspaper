package pl.toby.user.role;


public enum UserRole {

    LOCKED(new String[] {"ROLE_LOCKED"}),
    USER(new String[] {"ROLE_USER"}),
    JOURNALIST(new String[] {"ROLE_USER", "ROLE_JOURNALIST"}),
    ADMIN(new String[] {"ROLE_USER", "ROLE_JOURNALIST", "ROLE_ADMIN"});

    private String[] roles;

    UserRole(String[] roles) {
        this.roles = roles;
    }

    public String[] getRoles() {
        return roles;
    }

}
