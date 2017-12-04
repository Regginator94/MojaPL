package com.mojapl.mobile_app.main.realm;

import com.mojapl.mobile_app.main.models.SettingElement;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Klaudia on 21.11.2017.
 */

public interface IFiltersRepository {
    interface onSaveEventCallback {
        void onSuccess();

        void onError(String message);
    }

    interface onGetAllFiltersCallback {
        void onSuccess(RealmResults<SettingElement> events);

        void onError(String message);
    }
    interface onGetFilterCallback {
        void onSuccess(RealmResults<SettingElement> events, int organizationId);

        void onError(String message);
    }

    void updateFilter(SettingElement settingElement, onGetAllFiltersCallback callback);

    void getFilters(onGetAllFiltersCallback callback);

    void getFilterByOrganizationId(onGetFilterCallback callback, int organizatoinId);


    void saveFilters(List<SettingElement> list, onSaveEventCallback call);

    void saveFilter(SettingElement element, onSaveEventCallback call);
}
