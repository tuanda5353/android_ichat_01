package framgia.com.ichat.data.source.remote;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.AuthenticationDataSource;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public AuthenticationRemoteDataSource(FirebaseAuth auth, FirebaseDatabase firebaseDatabase) {
        mAuth = auth;
        mDatabase = firebaseDatabase;
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account,
                                 OnCompleteListener onCompleteListener,
                                 OnFailureListener onFailureListener) {
        if (account != null) {
            authenticateGoogleWithFireBase(account, onCompleteListener, onFailureListener);
        }
    }

    @Override
    public void updateUser(final FirebaseUser firebaseUser) {
        DatabaseReference databaseReference = mDatabase.getReference(User.UserKey.USER_REFERENCE);
        databaseReference.child(firebaseUser.getUid()).setValue(new User(firebaseUser));
    }

    private void authenticateGoogleWithFireBase(GoogleSignInAccount account,
                                                OnCompleteListener onCompleteListener,
                                                OnFailureListener onFailureListener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }
}
