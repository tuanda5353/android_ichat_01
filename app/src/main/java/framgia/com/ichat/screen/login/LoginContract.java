package framgia.com.ichat.screen.login;

import framgia.com.ichat.data.model.User;

public interface LoginContract {
    interface View {
        void showProgress();

        void hideProgress();

        void onErrorEmail();

        void onErrorPassword();

        void onLoginError();

        void updateUi(String email, String password);

        void updateCheckbox();

        void navigateHome();

        void navigateLogin();

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
    }
}
