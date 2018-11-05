package framgia.com.ichat.data.repository;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.AuthenticationDataSource;
import framgia.com.ichat.data.source.UserDataSouce;

public class UserRepository implements UserDataSouce.Remote {
    private UserDataSouce.Remote mRemote;
    private static UserRepository sInstance;

    public static UserRepository getInstance(UserDataSouce.Remote remote) {
        if (sInstance == null) {
            synchronized (AuthenticationRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(remote);
                }
            }
        }
        return sInstance;
    }

    public UserRepository(UserDataSouce.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getUsers(ValueEventListener valueEventListener) {
        mRemote.getUsers(valueEventListener);
    }

    @Override
    public void getUser(FirebaseUser user, ValueEventListener valueEventListener) {
        mRemote.getUser(user, valueEventListener);
    }
}
