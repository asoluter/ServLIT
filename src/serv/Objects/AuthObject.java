package serv.Objects;

public class AuthObject {
    private String userName;
    private String userPassword;

    public AuthObject(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
