package framgia.com.ichat.data.repository;

import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.source.PrivateRoomDataSource;

public class PrivateRoomRepository implements PrivateRoomDataSource.Remote{
    private PrivateRoomDataSource.Remote mRemote;
    public PrivateRoomRepository(PrivateRoomDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getPrivateRooms(ValueEventListener valueEventListener) {
        mRemote.getPrivateRooms(valueEventListener);
    }

    @Override
    public void createPrivateRoom(ValueEventListener valueEventListener) {
        mRemote.createPrivateRoom(valueEventListener);
    }
}
