package framgia.com.ichat.data.source;

import com.google.firebase.database.ValueEventListener;

public interface PrivateRoomDataSource {
    interface Remote{
        void getPrivateRooms(ValueEventListener valueEventListener);
        void createPrivateRoom(ValueEventListener valueEventListener);
    }
}
