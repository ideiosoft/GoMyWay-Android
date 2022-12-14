package ssc.snow.app.gomyways.views.home.controler.search.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.views.home.controler.search.adapter.AdapterSearchAll;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAll extends Fragment {


    @InjectView(R.id.recycle_all)
    RecyclerView recycleAll;

    private LinearLayoutManager mLinearLayoutManager;

    public FragmentAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.inject(this, v);
        init();
        return v;
    }

    private void init() {

        // To prevent layout from going top
        recycleAll.setFocusable(false);
        recycleAll.requestFocus();
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recycleAll.setLayoutManager(mLinearLayoutManager);

        // set adapter
        AdapterSearchAll mAdapter = new AdapterSearchAll(getActivity());
        recycleAll.setAdapter(mAdapter);


    }

}
