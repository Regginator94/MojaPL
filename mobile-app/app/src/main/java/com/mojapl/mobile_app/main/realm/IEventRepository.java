package com.mojapl.mobile_app.main.realm;

import com.mojapl.mobile_app.main.models.Event;

import io.realm.RealmResults;


public interface IEventRepository {

    interface onSaveEventCallback {
        void onSuccess();

        void onError(String message);
    }
    interface onGetAllEventsCallback {
        void onSuccess(RealmResults<Event> events);
        void onError(String message);
    }

    void getAllEvents(onGetAllEventsCallback callback);
    void addEvents(Event event, onSaveEventCallback callback);


}
