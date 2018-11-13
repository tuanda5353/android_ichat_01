package framgia.com.ichat.data.source.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.Room;
import framgia.com.ichat.data.source.PrivateRoomDataSource;

public class PrivateRoomRemoteDataSource implements PrivateRoomDataSource.Remote {
    private static final String PATTERN = "EEE, d MMM yyyy, HH:mm";
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private static PrivateRoomRemoteDataSource sInstance;

    public static PrivateRoomRemoteDataSource getInstance(FirebaseDatabase database, FirebaseAuth auth) {
        if (sInstance == null) {
            synchronized (PrivateRoomRemoteDataSource.class) {
                if (sInstance == null) {
                    sInstance = new PrivateRoomRemoteDataSource(database, auth);
                }
            }
        }
        return sInstance;
    }

    private PrivateRoomRemoteDataSource(FirebaseDatabase database, FirebaseAuth auth) {
        mDatabase = database;
        mAuth = auth;
    }

    @Override
    public void getRooms(ValueEventListener valueEventListener) {
        mDatabase.getReference(
                Room.PrivateRoomKey.PRIVATE_ROOM)
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void createRoom(OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        String roomId = mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM)
                .push()
                .getKey();
        String messageId = mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM).child(roomId)
                .push()
                .getKey();
        Room room = new Room();
        HashMap<String, Message> messages = new HashMap<>();
        HashMap<String, String> members = new HashMap<>();
        Message message = new Message(
                Message.MessageKey.CONTENT_DEFAULT,
                getCurrentTime(),
                Message.MessageKey.SENDER_ID_DEFAULT,
                Message.MessageKey.SENDER_NAME_DEFAULT,
                Message.MessageKey.SENDER_IMAGE_DEFAULT);
        messages.put(messageId, message);
        members.put(mAuth.getUid(), mAuth.getUid());
        room.setName(Room.PrivateRoomKey.NAME_DEFAULT);
        room.setImage(Room.PrivateRoomKey.IMAGE_DEFAULT);
        room.setMessages(messages);
        room.setMembers(members);
        mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM).child(roomId).setValue(room);

    }

    @Override
    public void deleteRoom(String id, OnCompleteListener onCompleteListener, OnFailureListener onFailureListener) {
        mDatabase.getReference(Room.PrivateRoomKey.PRIVATE_ROOM)
                .child(id)
                .removeValue()
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public String getCurrentTime() {
        return new SimpleDateFormat(PATTERN).format(Calendar.getInstance().getTime());
    }
}
