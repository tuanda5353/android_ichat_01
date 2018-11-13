package framgia.com.ichat.data.source;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

import framgia.com.ichat.data.model.User;

public interface UserDataSouce {
    interface Remote {
        void getUsers(ValueEventListener valueEventListener);

        void getUser(FirebaseUser user, ValueEventListener valueEventListener);

        void getUser(String id, ValueEventListener valueEventListener);

        void uploadImage(FirebaseUser user, Uri file,
                         OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener);

        void uploadImage(FirebaseUser user, ByteArrayOutputStream bytes,
                         OnCompleteListener onCompleteListener,
                         OnFailureListener onFailureListener);

        void updateUser(User user);

        void getLinkImage(OnCompleteListener onCompleteListener);

        void updateUserName(String id, String userName);
    }
}
