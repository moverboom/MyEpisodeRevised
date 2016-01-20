package nl.krakenops.myepisode.view.adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nl.krakenops.myepisode.controller.ThumbnailController;
import nl.krakenops.myepisode.view.fragments.AllFrag;
import nl.krakenops.myepisode.view.fragments.FavFrag;
import nl.krakenops.myepisode.view.fragments.RecentFrag;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Recent", "Favorite", "All" };
    private Context context;
    private ThumbnailController thumbnailController;

    public ViewPagerAdapter(FragmentManager fm, Context context, ThumbnailController thumbnailController) {
        super(fm);
        this.context = context;
        this.thumbnailController = thumbnailController;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecentFrag recentFrag = new RecentFrag().newInstance(thumbnailController.getRecentShows());
                return recentFrag;
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
