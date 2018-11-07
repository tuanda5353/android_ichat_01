package framgia.com.ichat.data.source;

import com.google.firebase.database.ValueEventListener;

public interface ChatDataSource {
    interface Remote{
        void getListMessage(String id, ValueEventListener valueEventListener);
    }
}
