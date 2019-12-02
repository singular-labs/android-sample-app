package net.singular.singularsampleapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.singular.singularsampleapp.fragments.CustomFragment;
import net.singular.singularsampleapp.fragments.DeeplinkFragment;
import net.singular.singularsampleapp.fragments.IdentityFragment;
import net.singular.singularsampleapp.fragments.RevenueFragment;

public class TabAdapter extends FragmentPagerAdapter {

    private int tabsCount;

    public TabAdapter(FragmentManager fragmentManager, int tabsCount) {
        super(fragmentManager);
        this.tabsCount = tabsCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CustomFragment customFragment = new CustomFragment();
                return customFragment;
            case 1:
                RevenueFragment revenueFragment = new RevenueFragment();
                return revenueFragment;
            case 2:
                IdentityFragment identityFragment = new IdentityFragment();
                return identityFragment;
            case 3:
                DeeplinkFragment deeplinkFragment = new DeeplinkFragment();
                return deeplinkFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}
