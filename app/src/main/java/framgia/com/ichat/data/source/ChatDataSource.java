package framgia.com.ichat.data.source;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.Message;

public interface ChatDataSource {
    interface Remote {
        void getMessages(String id, ValueEventListener valueEventListener);

        void sendMessage(FirebaseUser user, String roomId, Message message);

        void addOnChildChange(String roomId, ChildEventListener childEventListener);
    }
}
