package com.mojapl.mobile_app.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.DashboardListAdapter;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.models.DashboardListItem;
import com.mojapl.mobile_app.main.models.Event;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class DashboardListFragment extends Fragment implements ServerRequestListener {
    Connector connectionConfig;
    private ProgressDialog dialog;
    private RecyclerView mRecyclerView;

    int SPAN_COUNT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        connectionConfig = Connector.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));

        List<DashboardListItem> items = new ArrayList<>();

        items.add(new DashboardListItem(R.drawable.image_logo_transparent, "Test", "CONTENT"));
        items.add(new DashboardListItem(R.drawable.image_logo_transparent, "Test", "CONTENT"));
        items.add(new DashboardListItem(R.drawable.image_logo_transparent, "Test", "CONTENT"));
        items.add(new DashboardListItem(R.drawable.image_logo_transparent, "Test", "CONTENT"));

        DashboardListAdapter adapter = new DashboardListAdapter();
        adapter.setItems(items);

        mRecyclerView.setAdapter(adapter);
        ButterKnife.bind(this, view);
        return view;
    }

//    @OnClick(R.id.button)
//    public void getElem(){
//        dialog = new ProgressDialog(getActivity());
//        dialog.setMessage("Loading..");
//        dialog.show();
//        connectionConfig.getEvents(this);
//
//    }

    @Override
    public void serviceSuccess(List<Event> events) {
        Log.d("SUCCESS", events.get(0).toString());
        dialog.hide();
    }

    @Override
    public void serviceFailure(Exception e) {
        Log.e("ERROR", e.toString());
        dialog.hide();
    }
}
