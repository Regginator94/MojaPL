package com.mojapl.mobile_app.main.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.ExpandableListAdapter;
import com.mojapl.mobile_app.main.models.SettingElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<SettingElement>> listDataChild;

    @BindView(R.id.el)
    ExpandableListView expandableListView;


    public SettingsFragment() {
    }



    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        prepareListData();
        listAdapter  = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
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

        // Adding child data
        List<SettingElement> university = new ArrayList<>();
        university.add(new SettingElement("Wydziały", true));
        university.add(new SettingElement("Samorząd", false));


        List<SettingElement> events = new ArrayList<>();
        events.add(new SettingElement("Juwenalia", false));
        events.add(new SettingElement("Warsztaty", true));
        events.add(new SettingElement("Targi", false));


        List<SettingElement> hobby = new ArrayList<>();
        hobby.add(new SettingElement("Żak", true));
        hobby.add(new SettingElement("Futurysta", true));
        hobby.add(new SettingElement("AZS Pł", true));


        listDataChild.put(listDataHeader.get(0), university); // Header, Child data
        listDataChild.put(listDataHeader.get(1), events);
        listDataChild.put(listDataHeader.get(2), hobby);
    }

}
