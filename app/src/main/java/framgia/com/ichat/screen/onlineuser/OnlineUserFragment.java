package framgia.com.ichat.screen.onlineuser;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.R;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.UserRepository;
import framgia.com.ichat.data.source.remote.UserRemoteDataSource;
import framgia.com.ichat.screen.base.BaseFragment;
import framgia.com.ichat.screen.profile.ProfileActivity;

public class OnlineUserFragment extends BaseFragment implements OnlineUserContract.View, OnlineUserAdapter.OnUserItemClickListener {
    private OnlineUserContract.Presenter mPresenter;
    private OnlineUserAdapter mAdapter;

    public static OnlineUserFragment newInstance() {
        return new OnlineUserFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_online_room;
    }

    @Override
    protected void initComponents() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_online_room);
        List<User> users = new ArrayList<>();
        mAdapter = new OnlineUserAdapter(getActivity(), users, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        UserRepository repository = UserRepository.getInstance(UserRemoteDataSource.getInstance(
                FirebaseDatabase.getInstance(),
                FirebaseStorage.getInstance(),
                FirebaseAuth.getInstance()
        ));
        mPresenter = new OnlineUserPresenter(repository);
        mPresenter.setView(this);
        mPresenter.getUsers();
    }

    @Override
    public void updateDataRecyclerView(List<User> users) {
        mAdapter.addData(users);
    }

    @Override
    public void onClickUserItem(User user) {
        startActivity(ProfileActivity.getIntent(getActivity(), user));
    }
}
