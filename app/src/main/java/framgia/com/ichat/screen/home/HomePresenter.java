package framgia.com.ichat.screen.home;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.HomeRepository;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private HomeRepository mRepository;

    public HomePresenter(HomeRepository repository) {
        mRepository = repository;
    }

    @Override
    public User getCurrentUser() {
        return mRepository.getCurrentUser();

    }

    @Override
    public void setView(HomeContract.View view) {
        this.mView = view;
    }
}
