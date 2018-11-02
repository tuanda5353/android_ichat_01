package framgia.com.ichat.screen.login;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import framgia.com.ichat.data.model.User;

public interface LoginContract {
    interface View {
        void showProgress();

        void hideProgress();

        void onErrorEmail();

        void onErrorPassword();

        void onLoginError();

        void onSystemError();

        void updateUi(String email, String password);

        void updateCheckbox();

        void navigateHome();

        void navigateSignUp();

        void saveInformation();
    }

    interface Presenter {
        void showUserInformation();

        boolean validateEmailPassword(String email, String password);

        void saveUserInformation(String email, String password);

        void setUserInformation();

        void login(String email, String password);

        void loginWithAccount(String email, String password);

        void getInformationRemember(User user);

        boolean getRememberStatus();

        void setIsRemember(boolean isRemember);

        void getSignedInAccount(int requestCode, Intent data);

        void handleSignInWithGoogle(Task<GoogleSignInAccount> task);
    }
}
