package framgia.com.ichat.screen.signup;

import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.screen.base.BasePresenter;

public class SignUpContract {
    public interface View {
        void showProgressDialog();

        void hideProgressDialog();

        void showErrorEmail(String error);

        void showErrorPassword(String error);

        void onEmailEmpty();

        void onPasswordEmpty();

        void requestFocusEmail();

        void requestFocusPassword();

        void changeActivity(Class activity);

        void onCreateAccountSuccess();

        void onCreateAccountFailed();

        void showToastShort(String message);
    }

    public interface Presenter extends BasePresenter<View> {
        void createAccount(String email, String password);

        FirebaseUser getCurrentUser();

        boolean isValidate(String email, String password);
    }
}
