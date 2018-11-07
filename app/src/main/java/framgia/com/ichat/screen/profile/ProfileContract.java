package framgia.com.ichat.screen.profile;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.model.User;

public interface ProfileContract {
    interface View {
        void showUserInfo(String userName, String email, String pathImage, String lastSignedIn);

        void showCoverImage();

        void hideButton();

        boolean isCheckSelfPermission(String permission);

        boolean isShouldShowRequestPermission(String permission);

        void requestPermission(String permission, int requestCode);

        void onPermissionError();

        void showDialog();

        void showProgress();

        void setImageDialogFromGallery(Uri uri);

        void setImageDialogFromCamera(Bitmap bitmap);

        void chooseImageFromGallery();

        void chooseImageFromCamera();

        void onUserNameError();

        void updateProfile(Uri uri, String name);

        void updateProfile(String name);

        void onUpdateError();

        void signOut();
    }

    interface Presenter {
        void showUserInfo(FirebaseUser firebaseUser, User user);
    }
}
