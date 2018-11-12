package framgia.com.ichat.screen.publicroom;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.data.model.Room;
import framgia.com.ichat.data.repository.PublicRoomRepository;

public class PublicRoomPresenter implements PublicRoomContract.Presenter {
    private PublicRoomContract.View mView;
    private PublicRoomRepository mRepository;

    public PublicRoomPresenter(PublicRoomRepository repository) {
        mRepository = repository;
    }

    @Override
    public void getPublicRooms(final String id) {
        mRepository.getPublicRooms(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Room> rooms = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Room room = snapshot.getValue(Room.class);
                    if (room.getMembers().keySet().contains(id)) {
                        room.setId(snapshot.getKey());
                        rooms.add(room);
                    }
                }
                mView.onGetRoomsSuccess(rooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetRoomsFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void setView(PublicRoomContract.View view) {
        mView = view;
    }
}
