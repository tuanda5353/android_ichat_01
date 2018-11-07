package framgia.com.ichat.data.source.remote;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.UserDataSouce;

public class UserRemoteDataSource implements UserDataSouce.Remote {
    private static final String PNG = ".png";
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private static UserRemoteDataSource sInstance;

    public static UserDataSouce.Remote getInstance(FirebaseDatabase database,
                                                   FirebaseStorage storage,
                                                   FirebaseAuth auth) {
        if (sInstance == null) {
            synchronized (UserRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new UserRemoteDataSource(database, storage, auth);
                }
            }
        }
        return sInstance;
    }

    public UserRemoteDataSource(FirebaseDatabase database,
                                FirebaseStorage storage,
                                FirebaseAuth auth) {
        mDatabase = database;
        mStorage = storage;
        mAuth = auth;
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

    @Override
    public void uploadImage(FirebaseUser user, Uri file,
                            OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        StorageReference storageReference = mStorage.getReference();
        storageReference.child(user.getUid().concat(PNG))
                .putFile(file)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void uploadImage(FirebaseUser user, ByteArrayOutputStream bytes,
                            OnCompleteListener onCompleteListener,
                            OnFailureListener onFailureListener) {
        StorageReference storageReference = mStorage.getReference();
        storageReference.child(user.getUid().concat(PNG))
                .putBytes(bytes.toByteArray())
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void updateUser(User user) {
        setUserInfo(user);
    }

    @Override
    public void getLinkImage(OnCompleteListener onCompleteListener) {
        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference storageReference = mStorage.getReference(user.getUid().concat(PNG));
        storageReference.getDownloadUrl().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void updateUserName(String id, String userName) {
        DatabaseReference reference = mDatabase.getReference(User.UserKey.USER_REFERENCE).child(id);
        reference.child(User.UserKey.DISPLAY_NAME).setValue(userName);
    }

    private void setUserInfo(User user) {
        mDatabase.getReference(User.UserKey.USER_REFERENCE)
                .child(user.getUid())
                .setValue(user);
    }
}
