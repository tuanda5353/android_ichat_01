package framgia.com.ichat.screen.home;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.UserRepository;

public class HomePresenter implements HomeContract.Presenter, ValueEventListener {
    private UserRepository mRepository;
    private HomeContract.View mView;
    private User mUser;

    public HomePresenter(HomeContract.View view, UserRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void getUser(FirebaseUser user) {
        mRepository.getUser(user, this);
    }

    @Override
    public User getUSer() {
        return mUser;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = dataSnapshot.getValue(User.class);
        mView.showImage(mUser.getPhotoUrl());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
