package framgia.com.ichat.screen.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import framgia.com.ichat.R;
import framgia.com.ichat.data.repository.AuthenticationRepository;
import framgia.com.ichat.data.source.remote.AuthenticationRemoteDataSource;
import framgia.com.ichat.screen.base.BaseActivity;

public class SignUpActivity extends BaseActivity implements SignUpContract.View, View.OnClickListener {
    private EditText mEditName;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private SignUpContract.Presenter mPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initComponents() {
        mEditName = findViewById(R.id.edit_name);
        mEditEmail = findViewById(R.id.edit_email);
        mEditPassword = findViewById(R.id.edit_password);
        findViewById(R.id.button_sign_up).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        AuthenticationRepository repository = new AuthenticationRepository(
                new AuthenticationRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance()));
        mPresenter = new SingUpPresenter(repository);
        mPresenter.setView(this);
    }

    @Override
    public void showErrorEmail(String error) {
        mEditEmail.setError(error);
    }

    @Override
    public void showErrorPassword(String error) {
        mEditPassword.setError(error);

    }

    @Override
    public void onEmailEmpty() {
        mEditEmail.setError(getString(R.string.error_required));
        mEditEmail.requestFocus();
    }

    @Override
    public void onPasswordEmpty() {
        mEditPassword.setError(getString(R.string.error_required));
        mEditPassword.requestFocus();
    }

    @Override
    public void requestFocusEmail() {
        mEditEmail.requestFocus();
    }

    @Override
    public void requestFocusPassword() {
        mEditPassword.requestFocus();
    }

    @Override
    public void onCreateAccountSuccess() {
        Toast.makeText(this, R.string.msg_create_account_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateAccountFailed() {
        Toast.makeText(this, R.string.msg_create_account_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_up:
                String name = mEditName.getText().toString();
                String email = mEditEmail.getText().toString();
                String password = mEditPassword.getText().toString();
                mPresenter.createAccount(email, password);
                break;
        }
    }
}
