package framgia.com.ichat.data.source.remote;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.UserDataSouce;

public class UserRemoteDataSource implements UserDataSouce.Remote {

    private FirebaseDatabase mDatabase;

    public UserRemoteDataSource(FirebaseDatabase database) {
        mDatabase = database;
    }

    @Override
    public void getUsers(ValueEventListener valueEventListener) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void getUser(FirebaseUser user, ValueEventListener valueEventListener) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .child(user.getUid())
                .addValueEventListener(valueEventListener);
    }
}
