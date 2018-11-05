package framgia.com.ichat.screen.privateroom;

import java.util.List;

import framgia.com.ichat.data.model.PrivateRoom;
import framgia.com.ichat.screen.base.BasePresenter;

public class PrivateRoomContract {
    public interface View{
        void onGetDataFailed();
        void updateRecyclerView(List<PrivateRoom> privateRooms);
    }
    public interface Presenter extends BasePresenter<View> {
        void createPrivateRoom();
        void getPrivateRooms();
    }
}
