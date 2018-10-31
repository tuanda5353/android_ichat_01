package framgia.com.ichat.data.source.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.source.AuthenticationDataSource;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;

    public AuthenticationRemoteDataSource(FirebaseAuth auth) {
        mAuth = auth;
    }

    public void createAccount(String email, String password,
                              OnCompleteListener onCompleteListener,
                              OnFailureListener onFailureListener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void getLoginAccountStatus(String email, String password,
                                      OnCompleteListener onCompleteListener,
                                      OnFailureListener onFailureListener) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

}
