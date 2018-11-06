package framgia.com.ichat.screen.privateroom;

import framgia.com.ichat.screen.base.BasePresenter;

public class PrivateRoomContract {
    public interface View {
        void onGetDataFailed();
    }

    public interface Presenter extends BasePresenter<View> {
        void createPrivateRoom();

        void getPrivateRooms();
    }
}
