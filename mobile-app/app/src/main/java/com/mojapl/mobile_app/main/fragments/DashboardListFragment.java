package com.mojapl.mobile_app.main.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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


public class DashboardListFragment extends Fragment implements ServerRequestListener, IEventsCallback {
    Connector connectionConfig;
    private ProgressDialog dialog;
    private RecyclerView mRecyclerView;
    DashboardListAdapter adapter;
    private IEventRepository eventRepository = new EventRepository();
    private IEventRepository.onSaveEventCallback onSaveEventCallback;
    private List<Event> events;
    private SharedPreferences pref;
    int SPAN_COUNT = 1;
    View view;

    public final static String TAG = "DashboardListFragment";

    int category = -1;

    ServerRequestListener self;

    private final static String KEY = "key";

    public static DashboardListFragment newInstance(final int category) {

        Bundle args = new Bundle();
        args.putInt(KEY, category);

        DashboardListFragment fragment = new DashboardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        connectionConfig = Connector.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));

        adapter = new DashboardListAdapter(getContext(), new ArrayList<Event>(0));

        mRecyclerView.setAdapter(adapter);
        ButterKnife.bind(this, view);

//        startLoading();
//        connectionConfig.getEventsByRegex(this, pref.getString("token", ""), "blog");

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        events = null;
        startLoading();
        super.onResume();
    }

    private void startLoading() {
        if (events == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading..");
            dialog.show();

            pref = getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);

            int category = getArguments().getInt(KEY);

            if (category > 0) {
                connectionConfig.getEventsByCategory(this, pref.getString("token", ""), category);
            } else {
                connectionConfig.getEventsByOrganisation(this, pref.getString("token", ""));
            }
        } else {
            adapter.updateList(events);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeCallbacks();
        self = this;
    }



    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_with_search, menu);
        super.onCreateOptionsMenu(menu, inflater);

        try {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint("Szukaj...");

            final MenuItem itemSearch = menu.findItem(R.id.action_search);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    connectionConfig.getEventsByRegex(self, pref.getString("token", ""), s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemSearch.setVisible(false);
                }
            });

            MenuItemCompat.setOnActionExpandListener(itemSearch, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    menu.clear();
                    inflater.inflate(R.menu.menu_with_search, menu);
                    onCreateOptionsMenu(menu, inflater);
                    return true;
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceSuccess(List<Event> events) {


        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                eventRepository.addEvents(events.get(i), onSaveEventCallback);
            }
            this.events = events;

        } else {
            Event emptyEvent = new Event();
            emptyEvent.setTitle("W tej kategorii nie ma żadnych eventów");
            emptyEvent.setImageUrl("https://cdn3.iconfinder.com/data/icons/emoticon-emoji/30/emoticon-sad-7-512.png");
            this.events = new ArrayList<>();
            this.events.add(emptyEvent);

            eventRepository.addEvents(this.events.get(0), onSaveEventCallback);
        }

        adapter.updateList(this.events);
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

            }

            @Override
            public void onError(String message) {

            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        onSaveEventCallback = null;
        self = null;
    }
}
