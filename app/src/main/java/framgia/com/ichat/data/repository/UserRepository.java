package framgia.com.ichat.data.repository;

import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.source.UserDataSouce;

public class UserRepository implements UserDataSouce.Remote {
    private UserDataSouce.Remote mRemote;

    public UserRepository(UserDataSouce.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getUsers(ValueEventListener valueEventListener) {
        mRemote.getUsers(valueEventListener);
    }
}
