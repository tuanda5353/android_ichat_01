package framgia.com.ichat.screen.chat;

import java.util.List;

import framgia.com.ichat.data.model.Message;
import framgia.com.ichat.data.model.User;

import framgia.com.ichat.data.repository.UserRepository;
import framgia.com.ichat.screen.base.BasePresenter;

public interface ChatContract {
    interface View {
        void onGetDataSuccess(Message message);

        void onMessageNull();

        void navigateProfile(User user);
    }

    interface Presenter extends BasePresenter<View> {
        void getUser(UserRepository userRepository);

        void getUser(UserRepository userRepository, String id);

        User getUserValue();

        void sendMessage(String message);

        void addOnChildChange(String id);

        void setFlag(String flag);
    }
}
