package framgia.com.ichat.data.source;

import com.google.firebase.database.ValueEventListener;

public interface PublicRoomDataSource {
    interface Remote {
        void getPublicRooms(ValueEventListener valueEventListener);
    }
}
