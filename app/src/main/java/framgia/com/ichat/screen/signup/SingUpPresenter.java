package framgia.com.ichat.screen.signup;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.repository.AuthenticationRepository;
import framgia.com.ichat.screen.home.HomeActivity;

public class SingUpPresenter implements SignUpContract.Presenter, OnCompleteListener, OnFailureListener {
    private SignUpContract.View mView;
    private AuthenticationRepository mRepository;

    public SingUpPresenter(AuthenticationRepository repository) {
        mRepository = repository;
    }

    @Override
    public void createAccount(String name, String email, String password) {
        if (!isValidate(name,email, password)) {
            return;
        }
        mView.showProgressDialog();
        mRepository.createAccount(name, email, password, this, this);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mRepository.getCurrentUser();
    }

    @Override
    public boolean isValidate(String name, String email, String password) {
        boolean valid = true;
        if (TextUtils.isEmpty(name)){
            mView.onNameEmpty();
            valid = false;
        }
        if (TextUtils.isEmpty(email)) {
            mView.onEmailEmpty();
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.onPasswordEmpty();
            valid = false;
        }
        return valid;
    }

    @Override
    public void setView(SignUpContract.View view) {
        this.mView = view;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        mView.hideProgressDialog();
        if (!task.isSuccessful()) {
            mView.onCreateAccountFailed();
            return;
        }
        FirebaseUser user = mRepository.getCurrentUser();
        mRepository.setInformationOfUser(user);
        mView.onCreateAccountSuccess();
        mView.changeActivity(HomeActivity.class);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            if (e instanceof FirebaseAuthWeakPasswordException) {
                mView.showErrorPassword(e.getMessage());
                mView.requestFocusPassword();
                return;
            }
            mView.showErrorEmail(e.getMessage());
            mView.requestFocusEmail();
            return;
        }
        if (e instanceof FirebaseAuthUserCollisionException) {
            mView.showErrorEmail(e.getMessage());
            mView.requestFocusEmail();
        }
    }
}
