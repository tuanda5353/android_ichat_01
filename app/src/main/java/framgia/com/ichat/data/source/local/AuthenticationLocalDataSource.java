package framgia.com.ichat.data.source.local;

import android.content.SharedPreferences;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.AuthenticationDataSource;
import framgia.com.ichat.data.source.CallBack;
import framgia.com.ichat.screen.login.LoginKey;

public class AuthenticationLocalDataSource implements AuthenticationDataSource.Local {
    private SharedPreferences mSharedPreferences;

    public AuthenticationLocalDataSource(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    @Override
    public void saveUserInformation(String email, String password) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(LoginKey.FRE_EMAIL, email);
        editor.putString(LoginKey.FRE_PASSWORD, password);
        editor.apply();
    }

    @Override
    public void getUserInformation(CallBack<User> callBack) {
        callBack.onLoginSuccess(getUser());
    }

    @Override
    public void changeRememberStatus(boolean isRemember) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(LoginKey.FRE_IS_REMEMBER, isRemember);
        editor.apply();
    }

    @Override
    public boolean getRememberStatus() {
        return getIsRemember();
    }

    public User getUser() {
        return new User(getEmail(), getPassWord());
    }

    private boolean getIsRemember() {
        return mSharedPreferences.getBoolean(LoginKey.FRE_IS_REMEMBER, LoginKey.FRE_BOOLEAN_VALUE_DEFAULT);
    }

    private String getEmail() {
        return mSharedPreferences.getString(LoginKey.FRE_EMAIL, LoginKey.FRE_STRING_VALUE_DEFAULT);
    }

    private String getPassWord() {
        return mSharedPreferences.getString(LoginKey.FRE_PASSWORD, LoginKey.FRE_STRING_VALUE_DEFAULT);
    }
}
