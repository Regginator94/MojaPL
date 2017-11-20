package com.mojapl.mobile_app.main.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.mojapl.mobile_app.main.adapters.DashboardListAdapter;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.models.Event;
import com.mojapl.mobile_app.main.realm.EventRepository;
import com.mojapl.mobile_app.main.realm.IEventRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class DashboardListFragment extends Fragment implements ServerRequestListener, IEventsCallbeck {
    Connector connectionConfig;
    private ProgressDialog dialog;
    private RecyclerView mRecyclerView;
    DashboardListAdapter adapter;
    private IEventRepository eventRepository = new EventRepository();
    private IEventRepository.onSaveEventCallback onSaveEventCallback;
    private List<Event> events;
    private SharedPreferences pref;
    int SPAN_COUNT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        connectionConfig = Connector.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));

        adapter = new DashboardListAdapter(getContext(), new ArrayList<Event>(0));

        mRecyclerView.setAdapter(adapter);
        ButterKnife.bind(this, view);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();
        subscribeCallbacks();

        pref = getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);

        if (DashboardFragment.clickPosition > 0) {
            connectionConfig.getEventsByCategory(this, pref.getString("token", ""), DashboardFragment.clickPosition);
        } else {
            connectionConfig.getEventsByOrganisation(this, pref.getString("token", ""));
        }

        return view;
    }

    @Override
    public void serviceSuccess(List<Event> events) {
        this.events = events;
        adapter.updateList(this.events);
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                eventRepository.addEvents(events.get(i), onSaveEventCallback);
            }
        } else {
            Toast.makeText(getContext(), "No events in this category :(", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }


        dialog.hide();
    }

    @Override
    public void serviceFailure(Exception e) {
        Log.e("ERROR", e.toString());
        dialog.hide();
    }

    @Override
    public void subscribeCallbacks() {
        onSaveEventCallback = new IEventRepository.onSaveEventCallback() {
            @Override
            public void onSuccess() {
                Log.i("REALM", "success adding new events");
            }

            @Override
            public void onError(String message) {
                Log.i("REALM", "error adding new events");
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }
}
