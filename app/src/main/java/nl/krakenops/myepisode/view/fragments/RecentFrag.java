package nl.krakenops.myepisode.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.presenter.ShowPresenter;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;
import nl.krakenops.myepisode.view.adapters.ThumbAdapter;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class RecentFrag extends Fragment {
    private static final String PRESENTER_KEY = "ShowPresenter";
    private View mFragmentView;
    private ShowPresenter showPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//Initialize
        mFragmentView = inflater.inflate(R.layout.fragment_base, container, false);
        showPresenter = new ShowPresenterImpl(getContext());
        //Init Recyclerview with correct XML file
        RecyclerView mRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.parentLayoutWatched);
        GridLayoutManager glm = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(glm);
        ThumbAdapter thumbAdapter = new ThumbAdapter(getActivity(), showPresenter.getRecentShows());
        mRecyclerView.setAdapter(thumbAdapter);
        Log.d("RecentFrag", "Created new RecentFrag view new ThumbAdapter");

        if (showPresenter.getRecentShows().size() <= 0) {
            View viewEmpty = LayoutInflater.from(container.getContext()).
                    inflate(R.layout.layout_no_shows, container, false);
            mFragmentView = viewEmpty;
        }

        return mFragmentView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}

