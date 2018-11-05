package framgia.com.ichat.screen.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import framgia.com.ichat.R;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.screen.base.BaseActivity;

public class ProfileActivity extends BaseActivity {
    private static final String EXTRA_USER = "EXTRA_USER";

    public static Intent getIntent(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initComponents() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
