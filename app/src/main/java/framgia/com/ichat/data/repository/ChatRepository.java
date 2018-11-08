package framgia.com.ichat.data.repository;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.source.ChatDataSource;

public class ChatRepository implements ChatDataSource.Remote {
    private ChatDataSource.Remote mRemote;
    private static ChatRepository sInstance;

    public static ChatRepository getInstance(ChatDataSource.Remote remote) {
        if (sInstance == null) {
            synchronized (ChatRepository.class) {
                if (sInstance == null) {
                    sInstance = new ChatRepository(remote);
                }
            }
        }
        return sInstance;
    }

    private ChatRepository(ChatDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getMessages(String id, ValueEventListener valueEventListener) {
        mRemote.getMessages(id, valueEventListener);
    }

    @Override
    public void sendMessage(FirebaseUser user, String roomId, Message message) {
        mRemote.sendMessage(user, roomId, message);
    }

    @Override
    public void addOnChildChange(String roomId, ChildEventListener childEventListener) {
        mRemote.addOnChildChange(roomId, childEventListener);
    }
}
