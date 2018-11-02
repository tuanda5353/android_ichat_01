package framgia.com.ichat.screen.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import framgia.com.ichat.R;
import framgia.com.ichat.data.repository.AuthenticationRepository;
import framgia.com.ichat.data.source.local.AuthenticationLocalDataSource;
import framgia.com.ichat.data.source.remote.AuthenticationRemoteDataSource;
import framgia.com.ichat.screen.home.HomeActivity;
import framgia.com.ichat.screen.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText mEditTextEmail, mEditTextPassword;
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressLogin;
    private CheckBox mCheckBoxRemember;
    private TextView mTextViewShowError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initVariable();
        mPresenter.showUserInformation();
    }

    @Override
    public void showProgress() {
        mProgressLogin.setMessage(getString(R.string.progress_loading));
        mProgressLogin.setCanceledOnTouchOutside(false);
        mProgressLogin.show();
    }

    @Override
    public void hideProgress() {
        mProgressLogin.dismiss();
    }

    @Override
    public void onErrorEmail() {
        mEditTextEmail.setError(getString(R.string.error_not_empty));
    }

    @Override
    public void onErrorPassword() {
        mEditTextPassword.setError(getString(R.string.error_not_empty));
    }

    @Override
    public void onLoginError() {
        hideProgress();
        mTextViewShowError.setText(getString(R.string.login_failed));
    }

    @Override
    public void onSystemError() {
        Toast.makeText(getApplicationContext(), R.string.login_error_system, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUi(String email, String password) {
        mEditTextEmail.setText(email);
        mEditTextPassword.setText(password);
        updateCheckbox();
    }

    @Override
    public void updateCheckbox() {
        mCheckBoxRemember.setChecked(mPresenter.getRememberStatus());
    }

    @Override
    public void navigateHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void navigateSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @Override
    public void saveInformation() {
        mPresenter.saveUserInformation(getText(mEditTextEmail), getText(mEditTextPassword));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_account:
                mPresenter.login(getText(mEditTextEmail), getText(mEditTextPassword));
                break;
            case R.id.button_login_google:
                showProgress();
                loginWithGoogle();
                break;
            case R.id.text_view_sign_up:
                navigateSignUp();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mPresenter.setIsRemember(isChecked);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getSignedInAccount(requestCode, data);
    }

    private void initViews() {
        mEditTextEmail = findViewById(R.id.edit_text_user_name);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mCheckBoxRemember = findViewById(R.id.check_box_remember_user);
        mProgressLogin = new ProgressDialog(this);
        mTextViewShowError = findViewById(R.id.text_view_show_error);

        findViewById(R.id.button_login_account).setOnClickListener(this);
        findViewById(R.id.button_login_google).setOnClickListener(this);
        findViewById(R.id.text_view_sign_up).setOnClickListener(this);
        mCheckBoxRemember.setOnCheckedChangeListener(this);
    }

    private void initVariable() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        AuthenticationRepository repository = new AuthenticationRepository(
                new AuthenticationRemoteDataSource(FirebaseAuth.getInstance(),
                        FirebaseDatabase.getInstance()),
                new AuthenticationLocalDataSource(sharedPreferences));
        mPresenter = new LoginPresenter(this, repository);
    }

    private String getText(EditText editText) {
        return editText.getText().toString();
    }

    public void loginWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LoginKey.RC_SIGN_IN);
    }
}
