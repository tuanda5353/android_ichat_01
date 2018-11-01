package framgia.com.ichat.screen.privateroom;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import framgia.com.ichat.R;
import framgia.com.ichat.screen.base.BaseFragment;

public class PrivateRoomFragment extends BaseFragment implements View.OnClickListener {
    public static PrivateRoomFragment newInstance() {
        return new PrivateRoomFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_private_room;
    }

    @Override
    protected void initComponents() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_private_room);
        getView().findViewById(R.id.fab_private_room).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {

    }
}
