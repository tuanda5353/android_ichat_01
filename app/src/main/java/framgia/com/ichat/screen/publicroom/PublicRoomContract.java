package framgia.com.ichat.screen.publicroom;

import framgia.com.ichat.screen.base.BasePresenter;

public class PublicRoomContract {
    interface View {

    }

    interface Presenter extends BasePresenter<View> {
        void getPublicRooms(String id);
    }
}
