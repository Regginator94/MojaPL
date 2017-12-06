package com.mojapl.mobile_app.main.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.ExpandableListAdapter;
import com.mojapl.mobile_app.main.connection.Connector;
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
import butterknife.OnClick;
import io.realm.RealmResults;

public class SettingsFragment extends Fragment implements SettingsChangeRequestListener, IEventsCallback {

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    List<SettingElement> university = new ArrayList<>();
    List<SettingElement> events = new ArrayList<>();
    List<SettingElement> hobby = new ArrayList<>();
    List<SettingElement> rabat = new ArrayList<>();
    List<SettingElement> updateData = new ArrayList<>();
    List<SettingElement> toUpdate = new ArrayList<>();
    Connector connector;
    private boolean saveData = false;
    private SharedPreferences pref;
    SettingsChangeRequestListener serverRequestListener;
    HashMap<String, List<SettingElement>> listDataChild;
    private IFiltersRepository repository = new FilterRepository();
    private IFiltersRepository.onSaveEventCallback onSaveEventCallback;
    private IFiltersRepository.onGetAllFiltersCallback onGetAllFiltersCallback;
    private IFiltersRepository.onGetFilterCallback onGetFilterCallback;

    @BindView(R.id.el)
    ExpandableListView expandableListView;

    @OnClick(R.id.confirm_button)
    void onClickConfirmButton() {
        updateData = new ArrayList<>();
        for (int i = 0; i < university.size(); i++) {
            if (university.get(i).getSelected()) {
                updateData.add(university.get(i));
            }
        }
        for (int i = 0; i < rabat.size(); i++) {
            if (rabat.get(i).getSelected()) {
                updateData.add(rabat.get(i));
            }
        }
        for (int i = 0; i < hobby.size(); i++) {
            if (hobby.get(i).getSelected()) {
                updateData.add(hobby.get(i));
            }
        }
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getSelected()) {
                updateData.add(events.get(i));
            }
        }
        if (updateData.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < updateData.size(); i++) {
                sb.append(String.valueOf(updateData.get(i).getOrganizationID()));
                int j = i+1;
                if(j < updateData.size()){
                    sb.append(",");
                }
            }
            Log.d("Confirmed", sb.toString());
            saveData = true;
            connector.updateFilters(serverRequestListener, pref.getString("token", ""), sb.toString());

        }
    }


    public SettingsFragment() {
        connector = Connector.getInstance();
        serverRequestListener = new SettingsChangeRequestListener() {


            @Override
            public void serviceSuccess(FiltersUpdateResponse response) {
                Toast.makeText(getActivity(), "Zapisano ustawienia.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void serviceFailure(Exception e) {
                Toast.makeText(getActivity(), "Ustawienia niezapisane!", Toast.LENGTH_SHORT).show();

            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        subscribeCallbacks();
        prepareListData();
        pref = getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        // setting list adapter
        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SettingElement settingElement = (SettingElement) listAdapter.getChild(groupPosition, childPosition);

                settingElement.getRealm().beginTransaction();
                settingElement.setSelected(!settingElement.getSelected());
                settingElement.getRealm().commitTransaction();
                toUpdate.add(settingElement);
                listAdapter.getChildView(groupPosition, childPosition, true, v,parent);
                return false;
            }
        });
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

                setListElement();
                saveFiltersToDB();
                listDataChild.put(listDataHeader.get(0), university); // Header, Child data
                listDataChild.put(listDataHeader.get(1), events);
                listDataChild.put(listDataHeader.get(2), hobby);
                listDataChild.put(listDataHeader.get(3), rabat);
            }
        };
    }

    private void saveFiltersToDB() {
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
    }

    private void setListElement() {
        university.add(new SettingElement("WEEIA", true, 2, 1));
        university.add(new SettingElement("Mechaniczny", false, 3, 1));
        university.add(new SettingElement("Chemiczny", false, 4, 1));


        events.add(new SettingElement("Klub Futurysta", false, 201, 2));


        hobby.add(new SettingElement("Żak", true, 301, 3));
        hobby.add(new SettingElement("AZS Politechnika Lodzka", true, 304, 3));
        hobby.add(new SettingElement("Biblioteka PL", true, 312, 3));
        hobby.add(new SettingElement("Biuro Karier PL", true, 309, 3));
        hobby.add(new SettingElement("Erasmus PL", true, 307, 3));
        hobby.add(new SettingElement("Grupa .NET PL WEEIA", true, 310, 3));
        hobby.add(new SettingElement("IAESTE PL", true, 305, 3));
        hobby.add(new SettingElement("Samorząd PL", true, 308, 3));
        hobby.add(new SettingElement("Spotted: PL", true, 306, 3));
        hobby.add(new SettingElement("Zatoka Sportu", true, 311, 3));


        rabat.add(new SettingElement("Pizzeria Finestra", true, 402, 4));
    }

    @Override
    public void serviceSuccess(FiltersUpdateResponse response) {
        Toast.makeText(getActivity(), R.string.message_settings_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void serviceFailure(Exception e) {

    }

    @Override
    public void onStop() {
        cancelTransaction();
        super.onStop();
    }


    void cancelTransaction(){
        if(!saveData){
            for(int i = 0; i<toUpdate.size(); i++){
                SettingElement settingElement = toUpdate.get(i);
                settingElement.getRealm().beginTransaction();
                settingElement.setSelected(!settingElement.getSelected());
                settingElement.getRealm().commitTransaction();
            }
        }
    }
}
