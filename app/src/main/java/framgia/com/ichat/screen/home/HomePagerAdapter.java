package framgia.com.ichat.screen.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import framgia.com.ichat.screen.privateroom.PrivateRoomFragment;
import framgia.com.ichat.screen.publicroom.PublicRoomFragment;
import framgia.com.ichat.screen.onlineuser.OnlineUserFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private static final int PRIVATE_ROOM_FRAGMENT = 0;
    private static final int USER_ONLINE_ROOM = 1;
    private String[] mTitles;

    public HomePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case PRIVATE_ROOM_FRAGMENT:
                return PrivateRoomFragment.newInstance();
            case USER_ONLINE_ROOM:
                return OnlineUserFragment.newInstance();
            default:
                return PublicRoomFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
