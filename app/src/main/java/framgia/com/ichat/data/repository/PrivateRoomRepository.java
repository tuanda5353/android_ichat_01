package framgia.com.ichat.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.source.PrivateRoomDataSource;

public class PrivateRoomRepository implements PrivateRoomDataSource.Remote {
    private PrivateRoomDataSource.Remote mRemote;
    private static PrivateRoomRepository sInstance;

    public static PrivateRoomRepository getInstance(PrivateRoomDataSource.Remote remote) {
        if (sInstance == null) {
            synchronized (PrivateRoomRepository.class) {
                if (sInstance == null) {
                    sInstance = new PrivateRoomRepository(remote);
                }
            }
        }
        return sInstance;
    }

    private PrivateRoomRepository(PrivateRoomDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getRooms(ValueEventListener valueEventListener) {
        mRemote.getRooms(valueEventListener);
    }

    @Override
    public void createRoom(OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        mRemote.createRoom(onCompleteListener, onFailureListener);
    }

    @Override
    public void deleteRoom(String id, OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        mRemote.deleteRoom(id, onCompleteListener, onFailureListener);
    }
}
