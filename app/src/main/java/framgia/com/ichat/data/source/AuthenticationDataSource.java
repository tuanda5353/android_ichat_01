package framgia.com.ichat.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationDataSource {
    public interface Remote {
        void createAccount(String email, String password,
                           OnCompleteListener onCompleteListener,
                           OnFailureListener onFailureListener);

        FirebaseUser getCurrentUser();
    }
}
