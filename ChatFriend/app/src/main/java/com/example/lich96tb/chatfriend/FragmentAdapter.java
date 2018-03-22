package com.example.lich96tb.chatfriend;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = null;
        switch (position) {
            case 0:
                fr = new FragmentListPriend();
                break;
            case 1:
                fr = new FragmentChatRoom();

                break;
            case 2:
                fr = new FragmentDocBao();
                break;
            case 3:
                fr=new FragmentTinNhan();
                break;
        }
        return fr;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "chat friend";
                break;
            case 1:
                title = "chat room";

                break;
            case 2:
                title = "Đọc Báo";
                break;
            case 3:
                title="Tin Nhắn";
                break;
        }

        return title;
    }
}
