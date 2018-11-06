package framgia.com.ichat.screen.home;

import com.google.firebase.auth.FirebaseUser;

import framgia.com.ichat.data.model.User;

public interface HomeContract {
    interface View {
        void showImage(String uri);
    }

    interface Presenter {
        void getUser(FirebaseUser user);

        User getUSer();
    }
}
