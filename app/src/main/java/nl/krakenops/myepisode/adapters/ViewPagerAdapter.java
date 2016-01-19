package nl.krakenops.myepisode.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nl.krakenops.myepisode.fragments.AllFrag;
import nl.krakenops.myepisode.fragments.FavFrag;
import nl.krakenops.myepisode.fragments.RecentFrag;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Recent", "Favorite", "All" };
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecentFrag();
            case 1:
                return new FavFrag();
            case 2:
                return new AllFrag();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
