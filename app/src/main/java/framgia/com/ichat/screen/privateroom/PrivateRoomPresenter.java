package framgia.com.ichat.screen.privateroom;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import framgia.com.ichat.data.model.Room;
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
        mRepository.createPrivateRoom(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {q
                    mView.onCreatePrivateRoomSuccess();
                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onCreatePrivateRoomFailed();
            }
        });
    }

    @Override
    public void getPrivateRooms() {
        mRepository.getPrivateRooms(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Room> listRoom = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Room room = snapshot.getValue(Room.class);
                    room.setId(snapshot.getKey());
                    listRoom.add(room);
                }
                mView.onGetListPrivateRoomSuccess(listRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onGetDataFailed();
            }
        });
    }

    @Override
    public void deletePrivateRoom(String id) {
        mRepository.deletePrivateRoom(id,
                new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mView.onDeletePrivateRoomSuccess();
                        }
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mView.onDeletePrivateRoomFailed();
                    }
                }
        );
    }
}
