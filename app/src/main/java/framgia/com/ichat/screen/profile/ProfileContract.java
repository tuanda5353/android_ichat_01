package framgia.com.ichat.screen.profile;

import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.model.User;

public interface ProfileContract {
    interface View {
        void showUserInfo(String userName, String email, String pathImage, String lastSignedIn);

        void showCoverImage();

        void hideButton();
    }

    interface Presenter {
        void showUserInfo(FirebaseUser firebaseUser, User user);
    }
}
