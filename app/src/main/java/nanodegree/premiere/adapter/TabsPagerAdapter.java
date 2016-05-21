package nanodegree.premiere.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nanodegree.premiere.adapter.PopularFragment;
import nanodegree.premiere.adapter.TopRatedFragment;
public class TabsPagerAdapter extends FragmentPagerAdapter
{
    int mNumOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PopularFragment tab1 = new PopularFragment();
                return tab1;
            case 1:
                TopRatedFragment tab2 = new TopRatedFragment();
                return tab2;
            case 2:
                FavoritesFragment tab3 = new FavoritesFragment();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
