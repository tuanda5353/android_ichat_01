package framgia.com.ichat.data.source.remote;

import com.google.firebase.auth.FirebaseAuth;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.HomeDataSource;

public class HomeRemoteDataSource implements HomeDataSource.Remote {
    private FirebaseAuth mAuth;

    public HomeRemoteDataSource(FirebaseAuth auth) {
        mAuth = auth;
    }

    @Override
    public User getCurrentUser() {
        return new User(mAuth.getCurrentUser());
    }
}
