package framgia.com.ichat.screen.profile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.UserRepository;

public class ProfilePresenter implements ProfileContract.Presenter,
        OnCompleteListener, OnFailureListener, ValueEventListener {
    private static final String PATTERN = "dd/MM/yyyy";
    private static final String DATA = "data";
    private static final int QUALITY = 100;
    private ProfileContract.View mView;
    private Uri mFilepath;
    private String mName;
    private UserRepository mRepository;
    private ByteArrayOutputStream mBytes;

    ProfilePresenter(ProfileContract.View view, UserRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void showUserInfo(FirebaseUser firebaseUser, User user) {
        mView.showCoverImage();
        if (!isSelf(firebaseUser, user.getUid())) {
            mView.hideButton();
        }
        mView.showUserInfo(user.getDisplayName(),
                user.getEmail(),
                user.getPhotoUrl(),
                formatDate(user.getLastSignIn()));
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        if (!mView.isCheckSelfPermission(permission)) {
            pickImage(requestCode);
            return;
        }
        if (!mView.isShouldShowRequestPermission(permission)) {
            mView.requestPermission(permission, requestCode);
            return;
        }
        mView.requestPermission(permission, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (grantResults.length > 0 && isGranted(grantResults)) {
            if (requestCode == ProfileActivity.PERMISSIONS_REQUEST_CAMERA) {
                mView.chooseImageFromCamera();
                return;
            } else if (requestCode == ProfileActivity.PERMISSIONS_REQUEST_READ_EXTERNAL) {
                mView.chooseImageFromGallery();
                return;
            }
        }
        mView.onPermissionError();
    }

    @Override
    public void setImage(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == ProfileActivity.PICK_IMAGE_FROM_GALLERY) {
                pickImageFromGallery(data);
            } else if (requestCode == ProfileActivity.PICK_IMAGE_FROM_CAMERA) {
                pickImageFromCamera((Bitmap) data.getExtras().get(DATA));
            }
        }
    }

    @Override
    public void update() {
        if (mFilepath != null) {
            mRepository.uploadImage(FirebaseAuth.getInstance().getCurrentUser()
                    , mFilepath, this, this);
        } else if (mBytes != null) {
            mRepository.uploadImage(FirebaseAuth.getInstance().getCurrentUser()
                    , mBytes, this, this);
        } else {
            mRepository.updateUserName(FirebaseAuth.getInstance().getCurrentUser().getUid(), mName);
            mView.updateProfile(mName);
        }
    }

    @Override
    public void updateInfo(String userName) {
        if (isUserNameEmpty(userName)) {
            mView.onUserNameError();
            return;
        }
        mName = userName;
        mView.showProgress();
        update();
    }

    @Override
    public void initVariable() {
        mName = null;
        mFilepath = null;
    }

    @Override
    public void onComplete(@NonNull Task task) {
        checkTask(task);
        mRepository.getLinkImage(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                checkTask(task);
                mRepository.updateUser(initUser(task.getResult().toString()));
                mView.updateProfile(Uri.parse(task.getResult().toString()), mName);
            }
        });
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        mView.onUpdateError();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        user.setDisplayName(mName);
        mRepository.updateUser(user);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        mView.onUpdateError();
    }

    private boolean isSelf(FirebaseUser user, String uid) {
        return uid.equals(user.getUid());
    }

    private String formatDate(long time) {
        return new SimpleDateFormat(PATTERN).format(new Date(time));
    }

    private User initUser(String path) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return new User(user.getUid(),
                user.getEmail(),
                mName,
                path,
                user.getMetadata().getLastSignInTimestamp(),
                true);
    }

    private boolean isUserNameEmpty(String userName) {
        return userName.isEmpty();
    }

    private boolean isGranted(int[] grantResults) {
        for (int grant : grantResults) {
            if (grant != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void checkTask(Task task) {
        if (!task.isSuccessful()) {
            return;
        }
    }

    private void pickImageFromGallery(Intent data) {
        mFilepath = data.getData();
        mView.setImageDialogFromGallery(data.getData());
    }

    private void pickImageFromCamera(Bitmap bitmap) {
        mView.setImageDialogFromCamera(bitmap);
        mBytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, mBytes);
    }

    private void pickImage(int requestCode) {
        if (requestCode == ProfileActivity.PERMISSIONS_REQUEST_CAMERA) {
            mView.chooseImageFromCamera();
        } else if (requestCode == ProfileActivity.PERMISSIONS_REQUEST_READ_EXTERNAL) {
            mView.chooseImageFromGallery();
        }
    }
}
