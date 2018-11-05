package framgia.com.ichat.screen.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import framgia.com.ichat.GlideApp;
import framgia.com.ichat.R;
import framgia.com.ichat.data.model.User;
import framgia.com.ichat.data.repository.HomeRepository;
import framgia.com.ichat.data.source.remote.HomeRemoteDataSource;
import framgia.com.ichat.screen.base.BaseActivity;
import framgia.com.ichat.utils.Constants;

public class HomeActivity extends BaseActivity implements SearchView.OnQueryTextListener,HomeContract.View {
    private HomePresenter mPresenter;
    private ImageView mImageAvartar;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initComponents() {
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TabLayout tabLayout = findViewById(R.id.tab_home);
        mImageAvartar = toolbar.findViewById(R.id.image_avatar);
        ViewPager viewPager = findViewById(R.id.viewpager_home);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),
                getResources().getStringArray(R.array.title_tab_home)));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter = new HomePresenter(new HomeRepository(
                new HomeRemoteDataSource(FirebaseAuth.getInstance())));
        User user = mPresenter.getCurrentUser();
        setImageAvatar(user.getPhotoUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home, menu);
        MenuItem searchItem = menu.findItem(R.id.search_home);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void setImageAvatar(String url) {
        if (url!=null){
            GlideApp.with(this).load(url).circleCrop().into(mImageAvartar);
            return;
        }
        GlideApp.with(this).load(Constants.UserProfile.DEFAULT_PROFILE_URL).circleCrop().into(mImageAvartar);
    }
}
