package nl.krakenops.myepisode.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.presenter.ShowPresenter;
import nl.krakenops.myepisode.presenter.ShowPresenterAbstract;
import nl.krakenops.myepisode.presenter.ShowPresenterImpl;
import nl.krakenops.myepisode.view.adapters.ThumbAdapter;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AllFrag extends Fragment {
    private static final String PRESENTER_KEY = "ShowPresenter";
    private View mFragmentView;
    private ShowPresenter showPresenter;

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
        showPresenter = new ShowPresenterImpl(getContext());

        ThumbAdapter thumbAdapter = new ThumbAdapter(getActivity(), showPresenter.getAllShows());
        mRecyclerView.setAdapter(thumbAdapter);
        return mFragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
