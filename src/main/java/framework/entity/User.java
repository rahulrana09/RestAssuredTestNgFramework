package framework.entity;

public class User {
    public String loginId, password;

    public boolean isAdmin;

    public User(String loginId, String password, boolean isAdmin) {
        this.loginId = loginId;
        this.password = password;
        this.isAdmin = isAdmin;
    }


}
