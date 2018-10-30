package framgia.com.ichat.data.repository;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.source.AuthenticationDataSource;

public class AuthenticationRepository implements AuthenticationDataSource.Remote {
    private AuthenticationDataSource.Remote mRemote;

    public AuthenticationRepository(AuthenticationDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void createAccount(String email, String password,
                              OnCompleteListener onCompleteListener,
                              OnFailureListener onFailureListener) {
        mRemote.createAccount(email, password, onCompleteListener, onFailureListener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mRemote.getCurrentUser();
    }

}
