package framgia.com.ichat.screen.useronline;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import framgia.com.ichat.R;
import framgia.com.ichat.screen.base.BaseFragment;

public class UserOnlineFragment extends BaseFragment {
    public static UserOnlineFragment newInstance() {
        return new UserOnlineFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_online_room;
    }

    @Override
    protected void initComponents() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_online_room);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
