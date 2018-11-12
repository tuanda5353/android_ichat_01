package framgia.com.ichat.screen.publicroom;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import framgia.com.ichat.R;
import framgia.com.ichat.data.repository.PublicRoomRepository;
import framgia.com.ichat.data.source.remote.PublicRoomRemoteDataSource;
import framgia.com.ichat.screen.base.BaseFragment;

public class PublicRoomFragment extends BaseFragment implements PublicRoomContract.View {
    private PublicRoomContract.Presenter mPresenter;

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
        mPresenter = new PublicRoomPresenter(
                PublicRoomRepository.getInstance(PublicRoomRemoteDataSource.getInstance(
                        FirebaseDatabase.getInstance(), FirebaseAuth.getInstance())));
        mPresenter.setView(this);
        mPresenter.getPublicRooms(FirebaseAuth.getInstance().getUid());
    }
}
