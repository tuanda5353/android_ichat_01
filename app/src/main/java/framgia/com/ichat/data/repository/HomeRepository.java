package framgia.com.ichat.data.repository;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.source.HomeDataSource;

public class HomeRepository implements HomeDataSource.Remote {
    private HomeDataSource.Remote mRemote;

    public HomeRepository(HomeDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public User getCurrentUser() {
        return mRemote.getCurrentUser();
    }
}
