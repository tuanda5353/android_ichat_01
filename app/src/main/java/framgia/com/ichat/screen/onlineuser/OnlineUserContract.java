package framgia.com.ichat.screen.onlineuser;

import java.util.List;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.screen.base.BasePresenter;

public class OnlineUserContract {
    interface View {
        void updateDataRecyclerView(List<User> users);
    }

    public interface Presenter extends BasePresenter<View> {
        void getUsers();
    }
}
