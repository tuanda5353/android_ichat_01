package framgia.com.ichat.data.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class User {
    private String mUid;
    private String mEmail;
    private String mPassword;
    private String mDisplayName;
    private String mPhotoUrl;
    private long mLastSignIn;
    private boolean mIsOnline;

    public User(FirebaseUser firebaseUser) {
        mUid = firebaseUser.getUid();
        mEmail = firebaseUser.getEmail();
        mDisplayName = firebaseUser.getDisplayName();
        mPhotoUrl = String.valueOf(firebaseUser.getPhotoUrl());
        mLastSignIn = Calendar.getInstance().getTimeInMillis();
        mIsOnline = true;
    }

    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public long getLastSignIn() {
        return mLastSignIn;
    }

    public void setLastSignIn(long lastSignIn) {
        mLastSignIn = lastSignIn;
    }

    public boolean isOnline() {
        return mIsOnline;
    }

    public void setOnline(boolean online) {
        mIsOnline = online;
    }

    public class UserKey {
        public static final String USER_REFERENCE = "user";
    }
}
