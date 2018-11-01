package framgia.com.ichat.data.model;

public class User {
    private String mEmail;
    private String mPassword;

    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }
}
