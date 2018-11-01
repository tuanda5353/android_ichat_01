package framgia.com.ichat.screen.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.AuthenticationRepository;
import framgia.com.ichat.data.source.CallBack;

public class LoginPresenter implements LoginContract.Presenter, OnCompleteListener, OnFailureListener {
    private boolean mIsRemember;
    private LoginContract.View mLoginView;
    private AuthenticationRepository mRepository;

    public LoginPresenter(LoginContract.View view, AuthenticationRepository repository) {
        this.mLoginView = view;
        this.mRepository = repository;
    }

    @Override
    public void showUserInformation() {
        if (getRememberStatus()) setUserInformation();
    }

    @Override
    public boolean validateEmailPassword(String email, String password) {
        boolean result = false;
        if (email.isEmpty()) {
            mLoginView.onErrorEmail();
            result = true;
        }
        if (password.isEmpty()) {
            mLoginView.onErrorPassword();
            result = true;
        }
        return result;
    }

    @Override
    public void saveUserInformation(String email, String password) {
        mRepository.saveUserInformation(email, password);
    }

    @Override
    public void setUserInformation() {
        mRepository.getUserInformation(new CallBack<User>() {
            @Override
            public void onLoginSuccess(User data) {
                getInformationRemember(data);
            }

            @Override
            public void onLoginFailed(Exception e) {

            }
        });
    }

    @Override
    public void login(String email, String password) {
        if (!validateEmailPassword(email, password)) {
            mLoginView.showProgress();
            loginWithAccount(email, password);
        }
    }

    @Override
    public void loginWithAccount(String email, String password) {
        mRepository.getLoginAccountStatus(email, password, this, this);
    }

    @Override
    public void getInformationRemember(User user) {
        mLoginView.updateUi(user.getmEmail(), user.getmPassword());
    }

    @Override
    public boolean getRememberStatus() {
        return mRepository.getRememberStatus();
    }

    @Override
    public void setIsRemember(boolean isRemember) {
        mIsRemember = isRemember;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mLoginView.hideProgress();
        if (!task.isSuccessful()) {
            return;
        }
        if (mIsRemember) {
            mLoginView.saveInformation();
            mRepository.changeRememberStatus(mIsRemember);
        } else {
            mRepository.changeRememberStatus(mIsRemember);
        }
        mLoginView.navigateHome();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        mLoginView.onLoginError();
    }
}
