package nl.krakenops.myepisode.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.presenter.ThumbnailPresenter;
import nl.krakenops.myepisode.view.adapters.ThumbAdapter;
import nl.krakenops.myepisode.model.Thumbnail;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AllFrag extends Fragment {
    private static final String ARRAY_KEY = "ArrayList<Thumbnail>";
    private static final String PRESENTER_KEY = "ThumbnailPresenter";
    private ArrayList<Thumbnail> mList;
    private View mFragmentView;
    private ThumbnailPresenter thumbnailPresenter;


    public static AllFrag newInstance(ThumbnailPresenter thumbnailPresenter) {
        AllFrag allFrag = new AllFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRESENTER_KEY, thumbnailPresenter);
        allFrag.setArguments(bundle);
        return allFrag;
    }

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

        mList = (ArrayList<Thumbnail>) getArguments().getSerializable(ARRAY_KEY);
        thumbnailPresenter = (ThumbnailPresenter) getArguments().getSerializable(PRESENTER_KEY);

        ThumbAdapter thumbAdapter = new ThumbAdapter(thumbnailPresenter.getAllShows(), getActivity(), thumbnailPresenter);
        mRecyclerView.setAdapter(thumbAdapter);
        return mFragmentView;
    }

    private List<Thumbnail> createList(int size) {
        List<Thumbnail> result = new ArrayList<Thumbnail>();
        for (int i = 0; i < size; i++) {
            result.add(new Thumbnail());
        }
        return result;
    }
}
