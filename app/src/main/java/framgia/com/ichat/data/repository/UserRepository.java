package framgia.com.ichat.data.repository;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

import framgia.com.ichat.data.model.User;
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

    @Override
    public void uploadImage(FirebaseUser user, Uri file,
                            OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        mRemote.uploadImage(user, file, onCompleteListener, onFailureListener);
    }

    @Override
    public void uploadImage(FirebaseUser user, ByteArrayOutputStream bytes,
                            OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        mRemote.uploadImage(user, bytes, onCompleteListener, onFailureListener);
    }

    @Override
    public void updateUser(User user) {
        mRemote.updateUser(user);
    }

    @Override
    public void getLinkImage(OnCompleteListener onCompleteListener) {
        mRemote.getLinkImage(onCompleteListener);
    }

    @Override
    public void updateUserName(String id, String userName) {
        mRemote.updateUserName(id, userName);
    }
}
