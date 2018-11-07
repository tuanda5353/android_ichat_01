package framgia.com.ichat.screen.privateroom;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.data.model.PrivateRoom;
import framgia.com.ichat.data.repository.PrivateRoomRepository;

public class PrivateRoomPresenter implements PrivateRoomContract.Presenter {
    private PrivateRoomContract.View mView;
    private PrivateRoomRepository mRepository;

    public PrivateRoomPresenter(PrivateRoomRepository repository) {
        mRepository = repository;
    }

    @Override
    public void setView(PrivateRoomContract.View view) {
        mView = view;
    }

    @Override
    public void createPrivateRoom() {
        mRepository.createPrivateRoom(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getPrivateRooms() {
        mRepository.getPrivateRooms(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PrivateRoom> privateRooms = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    privateRooms.add(snapshot.getValue(PrivateRoom.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataFailed();
            }
        });
    }
}
