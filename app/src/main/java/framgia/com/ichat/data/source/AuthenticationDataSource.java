package framgia.com.ichat.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.model.User;

public interface AuthenticationDataSource {
     interface Remote {
        void createAccount(String email, String password,
                           OnCompleteListener onCompleteListener,
                           OnFailureListener onFailureListener);

        FirebaseUser getCurrentUser();

        void getLoginAccountStatus(String email, String password,
                                   OnCompleteListener onCompleteListener,
                                   OnFailureListener onFailureListener);
    }

    interface Local {
        void saveUserInformation(String email, String password);

        void changeRememberStatus(boolean isRemember);

        boolean getRememberStatus();

        void getUserInformation(CallBack<User> callBack);
    }
}
