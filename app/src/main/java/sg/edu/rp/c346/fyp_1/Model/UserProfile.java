package sg.edu.rp.c346.fyp_1.Model;

public class UserProfile {

    private String userEmail;
    private String userName;
    private String userPassword;
    private String userSecureCode;


    public UserProfile(){

    }


    public UserProfile(String userEmail, String userName, String userPassword, String userSecureCode) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userSecureCode = userSecureCode;
    }

    public String getUserSecureCode() {
        return userSecureCode;
    }

    public void setUserSecureCode(String userSecureCode) {
        this.userSecureCode = userSecureCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
