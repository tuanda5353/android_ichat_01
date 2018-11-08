package framgia.com.ichat.data.source.remote;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.Room;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.ChatDataSource;

public class ChatRemoteDataSource implements ChatDataSource.Remote {
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
    public void getMessages(String id, ValueEventListener valueEventListener) {
        mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM)
                .child(id).child(Message.MessageKey.MESSAGES)
                .addValueEventListener(valueEventListener);

    }

    @Override
    public void sendMessage(FirebaseUser user, String roomId, Message message) {
        String id = mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM).push().getKey();
        DatabaseReference reference = mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM);
        reference.child(roomId)
                .child(Message.MessageKey.MESSAGES)
                .child(id)
                .setValue(message);
    }

    @Override
    public void addOnChildChange(String roomId, ChildEventListener childEventListener) {
        DatabaseReference reference = mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM);
        reference.child(roomId)
                .child(Message.MessageKey.MESSAGES)
                .addChildEventListener(childEventListener);
    }
}
