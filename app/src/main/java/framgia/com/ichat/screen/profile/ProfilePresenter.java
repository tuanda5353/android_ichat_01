package framgia.com.ichat.screen.profile;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

import framgia.com.ichat.data.model.User;

public class ProfilePresenter implements ProfileContract.Presenter {
    private static final String PATTERN = "dd/MM/yyyy";
    private ProfileContract.View mView;


    public ProfilePresenter(ProfileContract.View view) {
        mView = view;
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

    private boolean isSelf(FirebaseUser user, String uid) {
        return uid.equals(user.getUid());
    }

    private String formatDate(long time) {
        return new SimpleDateFormat(PATTERN).format(new Date(time));
    }
}
