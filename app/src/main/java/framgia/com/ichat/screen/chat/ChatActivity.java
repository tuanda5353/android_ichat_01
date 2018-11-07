package framgia.com.ichat.screen.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import framgia.com.ichat.R;
import framgia.com.ichat.screen.base.BaseActivity;

public class ChatActivity extends BaseActivity {
    public static final String EXTRA_ID = "EXTRA_ID";
    private String mId;

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mId = getIntent().getExtras().getString(EXTRA_ID);
    }
}
