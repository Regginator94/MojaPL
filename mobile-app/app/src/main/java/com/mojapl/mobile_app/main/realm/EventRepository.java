package com.mojapl.mobile_app.main.realm;

import com.mojapl.mobile_app.main.models.Event;

import io.realm.Realm;
import io.realm.RealmResults;


public class EventRepository implements IEventRepository {
    @Override
    public void getAllEvents(onGetAllEventsCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Event> results = realm.where(Event.class).findAll();

        if (callback != null)
            callback.onSuccess(results);

    }

    @Override
    public void addEvents(Event event, onSaveEventCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Event realmObject = realm.createObject(Event.class);
        realmObject.setCategoryId(event.getCategoryId());
        realmObject.setStartDate(event.getStartDate());
        realmObject.setFbPost(event.isFbPost());
        realmObject.setId(event.getId());
        realmObject.setImageUrl(event.getImageUrl());
        realmObject.setContent(event.getContent());
        realmObject.setUrl(event.getUrl());
        realmObject.setOrganisationId(event.getOrganisationId());
        realm.commitTransaction();
        callback.onSuccess();
    }


}
