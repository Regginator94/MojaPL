package com.mojapl.mobile_app.main.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.ExpandableListAdapter;
import com.mojapl.mobile_app.main.listeners.SettingsChangeRequestListener;
import com.mojapl.mobile_app.main.models.FiltersUpdateResponse;
import com.mojapl.mobile_app.main.models.SettingElement;
import com.mojapl.mobile_app.main.realm.FilterRepository;
import com.mojapl.mobile_app.main.realm.IFiltersRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class SettingsFragment extends Fragment implements SettingsChangeRequestListener, IEventsCallbeck {

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    List<SettingElement> university = new ArrayList<>();
    List<SettingElement> events = new ArrayList<>();
    List<SettingElement> hobby = new ArrayList<>();
    List<SettingElement> rabat = new ArrayList<>();
    HashMap<String, List<SettingElement>> listDataChild;
    private IFiltersRepository repository = new FilterRepository();
    private IFiltersRepository.onSaveEventCallback onSaveEventCallback;
    private IFiltersRepository.onGetAllFiltersCallback onGetAllFiltersCallback;
    private IFiltersRepository.onGetFilterCallback onGetFilterCallback;

    @BindView(R.id.el)
    ExpandableListView expandableListView;


    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        subscribeCallbacks();
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        // setting list adapter
        expandableListView.setAdapter(listAdapter);
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Uczelnia");
        listDataHeader.add("Wydarzenia");
        listDataHeader.add("Hobby");
        listDataHeader.add("Rabaty");

        // Adding child data
        repository.getFilters(onGetAllFiltersCallback);
        listDataChild.put(listDataHeader.get(0), university); // Header, Child data
        listDataChild.put(listDataHeader.get(1), events);
        listDataChild.put(listDataHeader.get(2), hobby);
        listDataChild.put(listDataHeader.get(3), rabat);
    }

    @Override
    public void subscribeCallbacks() {

        onGetFilterCallback = new IFiltersRepository.onGetFilterCallback() {
            @Override
            public void onSuccess(RealmResults<SettingElement> _events, int organizationId) {

            }

            @Override
            public void onError(String message) {

            }
        };
        onSaveEventCallback = new IFiltersRepository.onSaveEventCallback() {
            @Override
            public void onSuccess() {
                Log.i("onSaveEventCallback", "success");
            }

            @Override
            public void onError(String message) {
                Log.i("onSaveEventCallback", "error");
            }
        };
        onGetAllFiltersCallback = new IFiltersRepository.onGetAllFiltersCallback() {
            @Override
            public void onSuccess(RealmResults<SettingElement> _events) {
                for (int i = 0; i < _events.size(); i++) {
                    int organizationId = _events.get(i).getType();
                    if (organizationId == 1) {
                        university.add(_events.get(i));
                    } else if (organizationId == 2) {
                        events.add(_events.get(i));
                    } else if (organizationId == 3) {
                        hobby.add(_events.get(i));
                    } else if (organizationId == 4) {
                        rabat.add(_events.get(i));
                    }

                }

            }

            @Override
            public void onError(String message) {

                university.add(new SettingElement("WEEIA", true, 2, 1));
                university.add(new SettingElement("Mechaniczny", false, 3, 1));
                university.add(new SettingElement("Chemiczny", false, 4, 1));
                university.add(new SettingElement("WTMIWT", false, 5, 1));
                university.add(new SettingElement("BINOŻ", false, 6, 1));
                university.add(new SettingElement("BAI", false, 7, 1));
                university.add(new SettingElement("FTIMS", false, 8, 1));
                university.add(new SettingElement("WIPOS", false, 9, 1));


                events.add(new SettingElement("Klub Futurysta", false, 201, 2));


                hobby.add(new SettingElement("Żak", true, 301, 3));


                rabat.add(new SettingElement("Pizzeria Finestra", true, 402, 4));


                for (int i = 0; i < university.size(); i++) {
                    repository.saveFilter(university.get(i), onSaveEventCallback);
                }
                for (int i = 0; i < events.size(); i++) {
                    repository.saveFilter(events.get(i), onSaveEventCallback);
                }
                for (int i = 0; i < hobby.size(); i++) {
                    repository.saveFilter(hobby.get(i), onSaveEventCallback);
                }
                for (int i = 0; i < rabat.size(); i++) {
                    repository.saveFilter(rabat.get(i), onSaveEventCallback);
                }
                listDataChild.put(listDataHeader.get(0), university); // Header, Child data
                listDataChild.put(listDataHeader.get(1), events);
                listDataChild.put(listDataHeader.get(2), hobby);
                listDataChild.put(listDataHeader.get(3), rabat);
            }
        };
    }

    @Override
    public void serviceSuccess(FiltersUpdateResponse response) {
        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serviceFailure(Exception e) {

    }
}
