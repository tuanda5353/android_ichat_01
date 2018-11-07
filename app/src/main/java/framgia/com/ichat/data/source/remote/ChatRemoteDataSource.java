package framgia.com.ichat.data.source.remote;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.Room;
import framgia.com.ichat.data.source.ChatDataSource;

public class ChatRemoteDataSource implements ChatDataSource.Remote{
    private FirebaseDatabase mDatabase;
    private static ChatRemoteDataSource sInstance;

    public static ChatRemoteDataSource getInstance(FirebaseDatabase database) {
        if (sInstance == null) {
            synchronized (ChatRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ChatRemoteDataSource(database);
                }
            }
        }
        return sInstance;
    }

    public ChatRemoteDataSource(FirebaseDatabase database) {
        mDatabase = database;
    }

    @Override
    public void getListMessage(String id, ValueEventListener valueEventListener) {
        mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM)
                .child(id).child(Message.MessageKey.MESSAGES)
                .addValueEventListener(valueEventListener);

    }
}
