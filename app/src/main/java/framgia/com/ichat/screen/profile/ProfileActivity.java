package framgia.com.ichat.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import framgia.com.ichat.GlideApp;
import framgia.com.ichat.R;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.screen.base.BaseActivity;

public class ProfileActivity extends BaseActivity implements ProfileContract.View {
    private static final String EXTRA_USER = "EXTRA_USER";
    private static final String SPACE = " ";
    private TextView mTextUserName, mTextEmail, mTextLastSignIn;
    private ImageView mImageCover, mImageViewAvatar;
    private Button mButtonEdit, mButtonSignOut;
    private ProfileContract.Presenter mPresenter;

    public static Intent getIntent(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initComponents() {
        mImageViewAvatar = findViewById(R.id.image_view_user);
        mImageCover = findViewById(R.id.image_cover_image);
        mTextUserName = findViewById(R.id.text_view_user_name);
        mTextEmail = findViewById(R.id.text_view_user_email);
        mTextLastSignIn = findViewById(R.id.text_view_user_last_sign_in);
        mButtonEdit = findViewById(R.id.button_edit_profile);
        mButtonSignOut = findViewById(R.id.button_sign_out);
        mPresenter = new ProfilePresenter(this);
        initActionBar();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.showUserInfo(FirebaseAuth.getInstance().getCurrentUser(), getUser());
    }

    @Override
    public void showUserInfo(String userName, String email, String pathImage, String lastSignedIn) {
        setImage(mImageViewAvatar, pathImage);
        mTextUserName.setText(userName);
        mTextEmail.setText(email);
        mTextLastSignIn.setText(getString(R.string.profile_text_sign_in_title)
                .concat(SPACE)
                .concat(lastSignedIn));
    }

    @Override
    public void showCoverImage() {
        setImage();
    }

    @Override
    public void hideButton() {
        mButtonEdit.setVisibility(View.INVISIBLE);
        mButtonSignOut.setVisibility(View.INVISIBLE);
    }

    private User getUser() {
        return getIntent().getParcelableExtra(EXTRA_USER);
    }

    private void setImage(ImageView image, String pathImage) {
        GlideApp.with(this)
                .load(pathImage)
                .circleCrop()
                .into(image);
    }

    private void setImage() {
        GlideApp.with(this)
                .load(R.drawable.ic_image_cover)
                .into(mImageCover);
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
