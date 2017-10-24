package com.mojapl.mobile_app.main.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.models.Event;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class DashboardListFragment extends Fragment implements ServerRequestListener {
    Connector connectionConfig;
    private ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_list, container, false);
        connectionConfig = Connector.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button)
    public void getElem(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();
        connectionConfig.getEvents(this);

    }

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
