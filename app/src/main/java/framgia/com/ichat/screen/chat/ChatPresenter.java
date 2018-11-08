package framgia.com.ichat.screen.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.ChatRepository;
import framgia.com.ichat.data.repository.UserRepository;
import framgia.com.ichat.data.source.remote.UserRemoteDataSource;

public class ChatPresenter implements ChatContract.Presenter, ValueEventListener {
    public static final String DO_NOT_NOTHING = "DO_NOT_NOTHING";
    public static final String NAVIGATE_PROFILE = "NAVIGATE_PROFILE";
    private static final String PATTERN = "EEE, d MMM yyyy, HH:mm";
    private ChatContract.View mView;
    private ChatRepository mRepository;
    private User mUser;
    private String mRoomId;
    private String mFlag;

    public ChatPresenter(ChatRepository repository, String roomId) {
        mRepository = repository;
        mRoomId = roomId;
    }

    @Override
    public void setView(ChatContract.View view) {
        mView = view;
    }

    @Override
    public void getUser(UserRepository userRepository) {
        userRepository.getUser(FirebaseAuth.getInstance().getCurrentUser(), this);
    }

    @Override
    public void getUser(UserRepository userRepository, String id) {
        userRepository.getUser(id, this);
    }

    @Override
    public User getUserValue() {
        return mUser;
    }

    @Override
    public void sendMessage(String message) {
        if (isEmpty(message)) {
            mView.onMessageNull();
            return;
        }
        send(message);
    }

    @Override
    public void addOnChildChange(String id) {
        mRepository.addOnChildChange(id, new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mView.onGetDataSuccess(dataSnapshot.getValue(Message.class));
            }

            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setFlag(String flag) {
        mFlag = flag;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = dataSnapshot.getValue(User.class);
        switch (mFlag) {
            case NAVIGATE_PROFILE:
                mView.navigateProfile(mUser);
                break;
            case DO_NOT_NOTHING:
                break;
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private boolean isEmpty(String s) {
        return s.isEmpty();
    }

    private void send(String message) {
        mRepository.sendMessage(FirebaseAuth.getInstance().getCurrentUser(), mRoomId,
                initMessage(message));
    }

    private Message initMessage(String message) {
        return new Message(
                message,
                getCurrentTime(),
                mUser.getUid(),
                mUser.getDisplayName(),
                mUser.getPhotoUrl()
        );
    }

    public String getCurrentTime() {
        return new SimpleDateFormat(PATTERN).format(Calendar.getInstance().getTime());
    }
}
