package com.example.lich96tb.chatfriend;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lich96tb on 11/27/2017.
 */

public class FragmentTrangChu extends Fragment {
    ViewPager viewPager;
    TabLayout viewTab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_trang_chu,container,false);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewTab = (TabLayout) view.findViewById(R.id.viewTab);
        android.app.FragmentManager manager = getActivity().getFragmentManager();
//        FragmentManager manager1 = getActivity().getFragmentManager();
//        FragmentAdapter adater = new FragmentAdapter(manager);
//        viewTab.setupWithViewPager(viewPager);
//        viewTab.setTabsFromPagerAdapter(adater);
        return view;
    }
}
