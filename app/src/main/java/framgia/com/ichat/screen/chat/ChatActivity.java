package framgia.com.ichat.screen.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import framgia.com.ichat.R;

import framgia.com.ichat.data.repository.ChatRepository;
import framgia.com.ichat.data.repository.UserRepository;
import framgia.com.ichat.data.source.remote.ChatRemoteDataSource;
import framgia.com.ichat.data.source.remote.UserRemoteDataSource;
import framgia.com.ichat.screen.base.BaseActivity;
import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.screen.profile.ProfileActivity;

public class ChatActivity extends BaseActivity implements ChatContract.View,
        View.OnClickListener, ChatAdapter.OnMessageItemClickListener {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String NULL = null;
    private ChatPresenter mPresenter;
    private ChatAdapter mChatAdapter;
    private EditText mEditTextMessage;
    private RecyclerView mRecyclerView;
    private UserRepository mUserRepository;

    public static Intent getChatIntent(Context context, String id) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initComponents() {
        mRecyclerView = findViewById(R.id.recycle_chat_message);
        mEditTextMessage = findViewById(R.id.edit_chat_message);
        mChatAdapter = new ChatAdapter(this,
                FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
        findViewById(R.id.image_send).setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mChatAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String id = getIntent().getExtras().getString(EXTRA_ID);
        mPresenter = new ChatPresenter(ChatRepository.getInstance(
                ChatRemoteDataSource.getInstance(FirebaseDatabase.getInstance())),
                id);
        mPresenter.setView(this);
        mPresenter.addOnChildChange(id);
        mUserRepository = UserRepository.getInstance(
                UserRemoteDataSource.getInstance(
                        FirebaseDatabase.getInstance(),
                        FirebaseStorage.getInstance(),
                        FirebaseAuth.getInstance()
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setFlag(ChatPresenter.DO_NOT_NOTHING);
        mPresenter.getUser(mUserRepository);
    }

    @Override
    public void onGetDataSuccess(Message message) {
        mChatAdapter.addData(message);
        mRecyclerView.scrollToPosition(getLastPosition());
    }

    @Override
    public void onMessageNull() {
        showToastShort(getString(R.string.chat_error_message_empty));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_send:
                sendMessage();
                break;
            case R.id.image_emoji:
                break;
            case R.id.image_add_file:
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickMessageItem(String id) {
        mPresenter.setFlag(ChatPresenter.NAVIGATE_PROFILE);
        mPresenter.getUser(mUserRepository, id);
    }

    @Override
    public void navigateProfile(User user) {
        startActivity(ProfileActivity.getIntent(this, user));
    }

    private void sendMessage() {
        mPresenter.sendMessage(mEditTextMessage.getText().toString());
        mEditTextMessage.setText(NULL);
    }

    private int getLastPosition() {
        return mChatAdapter.getItemCount() == 0 ? 0 : mChatAdapter.getItemCount() - 1;
    }
}
