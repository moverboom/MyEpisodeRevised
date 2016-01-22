package nl.krakenops.myepisode.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.presenter.ThumbnailPresenter;
import nl.krakenops.myepisode.view.adapters.ThumbAdapter;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class RecentFrag extends Fragment {
    private static final String PRESENTER_KEY = "ThumbnailPresenter";
    private View mFragmentView;
    private ThumbnailPresenter thumbnailPresenter;
//
//
//    public static RecentFrag newInstance(ThumbnailPresenter thumbnailPresenter) {
//        RecentFrag recentFrag = new RecentFrag();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(PRESENTER_KEY, thumbnailPresenter);
//        recentFrag.setArguments(bundle);
//        Log.d("RecentFrag", "Created new RecentFrag instance");
//        return recentFrag;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize
        mFragmentView = inflater.inflate(R.layout.fragment_base, container, false);

        //Init Recyclerview with correct XML file
        RecyclerView mRecyclerView = (RecyclerView) mFragmentView.findViewById(R.id.parentLayoutWatched);
        GridLayoutManager glm = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        glm.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(glm);
        thumbnailPresenter = new ThumbnailPresenter(getContext());

        ThumbAdapter thumbAdapter = new ThumbAdapter(getActivity(), thumbnailPresenter.getRecentShows());
        mRecyclerView.setAdapter(thumbAdapter);
        Log.d("RecentFrag", "Created new RecentFrag view new ThumbAdapter");
        return mFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRESENTER_KEY, thumbnailPresenter);
        //Save the fragment's state here
        outState.putBundle("Bundle", bundle);
        super.onSaveInstanceState(outState);
    }

    private List<Thumbnail> createList(int size) {
        List<Thumbnail> result = new ArrayList<Thumbnail>();
        for (int i = 0; i < size; i++) {
            result.add(new Thumbnail());
        }
        return result;
    }
}

