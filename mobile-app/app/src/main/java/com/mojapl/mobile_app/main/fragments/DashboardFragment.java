package com.mojapl.mobile_app.main.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.DashboardCategoryActivity;
import com.mojapl.mobile_app.main.adapters.DashboardAdapter;
import com.mojapl.mobile_app.main.listeners.OnDashboardItemClickListener;
import com.mojapl.mobile_app.main.models.DashboardItem;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment implements OnDashboardItemClickListener {

    public static final int SPAN_COUNT = 2;
    private RecyclerView mRecyclerView;
    public static int clickPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));

        List<DashboardItem> items = new ArrayList<>();
        items.add(new DashboardItem(R.drawable.icon_university, "UCZELNIA"));
        items.add(new DashboardItem(R.drawable.icon_events, "EVENTY"));
        items.add(new DashboardItem(R.drawable.icon_hobby, "HOBBY"));
        items.add(new DashboardItem(R.drawable.icon_discount, "RABATY"));

        DashboardAdapter adapter = new DashboardAdapter();
        adapter.setItems(items);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onItemClick(int position) {
        clickPosition = position + 1;
        Intent intent = new Intent(getContext(), DashboardCategoryActivity.class);
        startActivity(intent);
    }
}
