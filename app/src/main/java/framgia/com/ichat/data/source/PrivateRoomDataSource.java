package framgia.com.ichat.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

public interface PrivateRoomDataSource {
    interface Remote {
        void getRooms(ValueEventListener valueEventListener);

        void createRoom(OnCompleteListener onCompleteListener,
                        OnFailureListener onFailureListener);

        void deleteRoom(String id,
                        OnCompleteListener onCompleteListener,
                        OnFailureListener onFailureListener);
    }
}
