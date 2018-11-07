package framgia.com.ichat.data.repository;

import com.google.firebase.database.ValueEventListener;

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
    public void getListMessage(String id, ValueEventListener valueEventListener) {
        mRemote.getListMessage(id, valueEventListener);
    }
}
