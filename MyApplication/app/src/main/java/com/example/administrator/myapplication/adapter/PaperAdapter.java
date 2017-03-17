package com.example.administrator.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class PaperAdapter extends FragmentPagerAdapter
{
    List<Fragment>list;
    String[] mTitles;

    public PaperAdapter(FragmentManager fm, List<Fragment> list, String[] mTitles) {
        super(fm);
        this.list = list;
        this.mTitles = mTitles;
    }

    public PaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//不销毁

    }
}
