package framgia.com.ichat.data.source.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.AuthenticationDataSource;
import framgia.com.ichat.utils.Constants;

public class AuthenticationRemoteDataSource implements AuthenticationDataSource.Remote {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String mName;

    public AuthenticationRemoteDataSource(FirebaseAuth auth, FirebaseDatabase firebaseDatabase) {
        mAuth = auth;
        mDatabase = firebaseDatabase;
    }

    public void createAccount(String name, String email, String password,
                              OnCompleteListener onCompleteListener,
                              OnFailureListener onFailureListener) {
        mName = name;
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

    private void authenticateGoogleWithFireBase(GoogleSignInAccount account,
                                                OnCompleteListener onCompleteListener,
                                                OnFailureListener onFailureListener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void saveUserToDatabase(FirebaseUser firebaseUser) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .child(firebaseUser.getUid())
                .setValue(getInformationOfUser(firebaseUser));
    }

    @Override
    public User getInformationOfUser(FirebaseUser firebaseUser) {
        return new User(firebaseUser, true);
    }

    @Override
    public void setInformationOfUser(FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .setPhotoUri(Uri.parse(Constants.UserProfile.DEFAULT_PROFILE_URL))
                .build();
        mAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    saveUserToDatabase(mAuth.getCurrentUser());
                }
            }
        });
    }
}
