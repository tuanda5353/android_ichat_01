package framgia.com.ichat.data.source.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.Room;
import framgia.com.ichat.data.source.PublicRoomDataSource.Remote;

public class PublicRoomRemoteDataSource implements Remote {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    public static PublicRoomRemoteDataSource sInstance;

    public static PublicRoomRemoteDataSource getInstance(FirebaseDatabase database, FirebaseAuth auth) {
        if (sInstance == null) {
            synchronized (PublicRoomRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new PublicRoomRemoteDataSource(database, auth);
                }
            }
        }
        return sInstance;
    }

    public PublicRoomRemoteDataSource(FirebaseDatabase database, FirebaseAuth auth) {
        mDatabase = database;
        mAuth = auth;
    }

    @Override
    public void getPublicRooms(ValueEventListener valueEventListener) {
        mDatabase.getReference(
                Room.PublicRoomKey.PUBLIC_ROOM)
                .addValueEventListener(valueEventListener);
    }
}
