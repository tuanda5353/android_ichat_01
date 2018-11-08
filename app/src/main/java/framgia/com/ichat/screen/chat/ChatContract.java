package framgia.com.ichat.screen.chat;

import framgia.com.ichat.screen.base.BasePresenter;

public class ChatContract {
    interface View {

    }

    interface Presenter extends BasePresenter<View> {
        void getListMessage(String id);
    }
}
