package framgia.com.ichat.data.source.remote;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.PrivateRoom;
import framgia.com.ichat.data.source.PrivateRoomDataSource;

public class PrivateRoomRemoteDatasource implements PrivateRoomDataSource.Remote {
    private FirebaseDatabase mDatabase;

    public PrivateRoomRemoteDatasource(FirebaseDatabase database) {
        mDatabase = database;
    }

    @Override
    public void getPrivateRooms(ValueEventListener valueEventListener) {
        mDatabase.getReference(PrivateRoom.PrivateRoomKey.PRIVATE_ROOM).addValueEventListener(valueEventListener);
    }

    @Override
    public void createPrivateRoom(ValueEventListener valueEventListener) {

    }
}
