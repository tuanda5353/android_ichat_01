package framgia.com.ichat.screen.publicroom;

import android.os.Bundle;

import framgia.com.ichat.R;
import framgia.com.ichat.screen.base.BaseFragment;

public class PublicRoomFragment extends BaseFragment {
    public static PublicRoomFragment newInstance() {
        return new PublicRoomFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_public_room;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
