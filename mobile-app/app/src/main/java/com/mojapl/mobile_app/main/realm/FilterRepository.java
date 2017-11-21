package com.mojapl.mobile_app.main.realm;

import com.mojapl.mobile_app.main.models.SettingElement;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Klaudia on 21.11.2017.
 */

public class FilterRepository implements IFiltersRepository {
    public FilterRepository() {

    }

    @Override
    public void updateFilter(SettingElement settingElement, onGetAllFiltersCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        SettingElement settingElement1 = realm.where(SettingElement.class).contains("organizationID", String.valueOf(settingElement.getOrganizationID())).findFirst();
        realm.beginTransaction();
        settingElement1.setSelected((settingElement.getSelected()));
        realm.commitTransaction();

    }

    @Override
    public void getFilters(onGetAllFiltersCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<SettingElement> list = realm.where(SettingElement.class).findAll();
        if (list != null && list.size() != 0) {
            callback.onSuccess(list);
        } else {
            callback.onError("nie znaleziono");
        }


    }

    @Override
    public void getFilterByOrganizationId(onGetFilterCallback callback, int organizatoinId) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<SettingElement> list = realm.where(SettingElement.class).contains("organizationID", String.valueOf(organizatoinId)).findAll();
        if (list != null && list.size() != 0) {
            callback.onSuccess(list, organizatoinId);
        } else {
            callback.onError("nie znaleziono");
        }

    }




    @Override
    public void saveFilters(List<SettingElement> list, onSaveEventCallback call) {
        Realm realm = Realm.getDefaultInstance();


    }

    @Override
    public void saveFilter(SettingElement element, onSaveEventCallback call) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(element);
        realm.commitTransaction();
        call.onSuccess();
    }
}
