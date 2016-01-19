package nl.krakenops.myepisode.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nl.krakenops.myepisode.R;
import nl.krakenops.myepisode.adapters.ThumbAdapter;
import nl.krakenops.myepisode.adapters.Thumbnail;

/**
 * Created by Matthijs on 19/01/2016.
 */
public class AllFrag extends Fragment {
    private View mFragmentView;

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

        ThumbAdapter thumbAdapter = new ThumbAdapter(createList(15));
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
