package framgia.com.ichat.screen.privateroom;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.R;
import framgia.com.ichat.data.model.PrivateRoom;
import framgia.com.ichat.data.repository.PrivateRoomRepository;
import framgia.com.ichat.data.source.remote.PrivateRoomRemoteDatasource;
import framgia.com.ichat.screen.base.BaseFragment;

public class PrivateRoomFragment extends BaseFragment implements View.OnClickListener, PrivateRoomContract.View {
    private PrivateRoomPresenter mPresenter;
    private List<PrivateRoom> mPrivateRooms;

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
        mPrivateRooms = new ArrayList<>();
        getView().findViewById(R.id.fab_private_room).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new PrivateRoomPresenter(PrivateRoomRepository.getInstance
                (PrivateRoomRemoteDatasource.getInstance(FirebaseDatabase.getInstance())));
        mPresenter.setView(this);
        mPresenter.getPrivateRooms();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetDataFailed() {
        Toast.makeText(getActivity(),
                getResources().getString(R.string.msg_get_data_failed), Toast.LENGTH_SHORT).show();
    }
}
