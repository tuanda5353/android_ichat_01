package framgia.com.ichat.screen.profile;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import framgia.com.ichat.GlideApp;
import framgia.com.ichat.R;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.UserRepository;
import framgia.com.ichat.data.source.remote.UserRemoteDataSource;
import framgia.com.ichat.screen.base.BaseActivity;
import framgia.com.ichat.screen.login.LoginActivity;

public class ProfileActivity extends BaseActivity implements ProfileContract.View,
        View.OnClickListener {
    public static final int PICK_IMAGE_FROM_GALLERY = 0x248;
    public static final int PICK_IMAGE_FROM_CAMERA = 0x249;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL = 0x141;
    public static final int PERMISSIONS_REQUEST_CAMERA = 0x142;
    public static final String ACTION_PICK = Intent.ACTION_PICK;
    public static final String ACTION_IMAGE_CAPTURE = MediaStore.ACTION_IMAGE_CAPTURE;
    public static final Uri EXTERNAL_CONTENT_URI = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public static final double WIDTH = 0.90;
    public static final double HEIGHT = 0.70;
    private static final String EXTRA_USER = "EXTRA_USER";
    private static final String SPACE = " ";
    private TextView mTextUserName;
    private TextView mTextEmail;
    private TextView mTextLastSignIn;
    private ImageView mImageCover;
    private ImageView mImageViewAvatar;
    private Button mButtonEdit;
    private Button mButtonSignOut;
    private ProfileContract.Presenter mPresenter;
    private Dialog mDialog;
    private EditText mEditDialogUserName;
    private ImageView mDialogImage;

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
        initActionBar();
        mImageViewAvatar = findViewById(R.id.image_view_user);
        mImageCover = findViewById(R.id.image_cover_image);
        mTextUserName = findViewById(R.id.text_view_user_name);
        mTextEmail = findViewById(R.id.text_view_user_email);
        mTextLastSignIn = findViewById(R.id.text_view_user_last_sign_in);
        mButtonEdit = findViewById(R.id.button_edit_profile);
        mButtonSignOut = findViewById(R.id.button_sign_out);
        mPresenter = new ProfilePresenter(this,
                UserRepository.getInstance(UserRemoteDataSource.getInstance(
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance(),
                        FirebaseAuth.getInstance()
                )));
        mButtonEdit.setOnClickListener(this);
        mButtonSignOut.setOnClickListener(this);
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

    @Override
    public boolean isCheckSelfPermission(String permission) {
        return ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean isShouldShowRequestPermission(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission},
                requestCode);
    }

    @Override
    public void onPermissionError() {
        this.showToastShort(getString(R.string.profile_permission_error));
    }

    @Override
    public void showDialog() {
        initDialog();
        mPresenter.initVariable();
        mDialog.show();
    }

    @Override
    public void showProgress() {
        this.showProgressDialog();
    }

    @Override
    public void setImageDialogFromGallery(Uri uri) {
        setImage(mDialogImage, uri.toString());
    }

    @Override
    public void setImageDialogFromCamera(Bitmap bitmap) {
        setImage(mDialogImage, bitmap);
    }

    @Override
    public void chooseImageFromGallery() {
        startActivityForResult(new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI),
                PICK_IMAGE_FROM_GALLERY);
    }

    @Override
    public void chooseImageFromCamera() {
        startActivityForResult(new Intent(ACTION_IMAGE_CAPTURE),
                PICK_IMAGE_FROM_CAMERA);
    }

    @Override
    public void onUserNameError() {
        mEditDialogUserName.setError(getString(R.string.error_not_empty));
    }

    @Override
    public void updateProfile(Uri uri, String name) {
        this.hideProgressDialog();
        mDialog.dismiss();
        setImage(mImageViewAvatar, uri.toString());
    }

    @Override
    public void updateProfile(String name) {
        this.hideProgressDialog();
        mDialog.dismiss();
        mTextUserName.setText(name);
    }

    @Override
    public void onUpdateError() {
        this.showToastShort(getString(R.string.profile_edit_fail));
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        navigate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.setImage(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit_profile:
                showDialog();
                break;
            case R.id.button_choose_camera:
                mPresenter.requestPermission(Manifest.permission.CAMERA,
                        PERMISSIONS_REQUEST_CAMERA);
                break;
            case R.id.button_choose_gallery:
                mPresenter.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        PERMISSIONS_REQUEST_READ_EXTERNAL);
                break;
            case R.id.button_update:
                mPresenter.updateInfo(getText(mEditDialogUserName));
                break;
            case R.id.button_sign_out:
                signOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, grantResults);
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

    private void setImage(ImageView image, Bitmap bitmap) {
        GlideApp.with(this)
                .load(bitmap)
                .placeholder(R.drawable.ic_loading)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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

    private void initDialog() {
        int width = (int) (getResources().getDisplayMetrics().widthPixels * WIDTH);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * HEIGHT);
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_edit_profile);
        mEditDialogUserName = mDialog.findViewById(R.id.edit_pro_user_name);
        mEditDialogUserName.setText(mTextUserName.getText().toString());
        mDialog.getWindow().setLayout(width, height);
        mDialogImage = mDialog.findViewById(R.id.image_dialog_avatar);
        mDialog.findViewById(R.id.button_choose_camera).setOnClickListener(this);
        mDialog.findViewById(R.id.button_choose_gallery).setOnClickListener(this);
        mDialog.findViewById(R.id.button_update).setOnClickListener(this);
    }

    private String getText(EditText text) {
        return text.getText().toString();
    }

    private void navigate() {
        startActivity(LoginActivity.getIntent(this));
    }
}
