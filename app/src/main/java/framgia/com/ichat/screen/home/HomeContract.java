package framgia.com.ichat.screen.home;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.screen.base.BasePresenter;

public class HomeContract {
    public interface View{
        void setImageAvatar(String url);
    }
    public interface Presenter extends BasePresenter<View> {
        User getCurrentUser();
    }
}
