package com.mojapl.mobile_app.main.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.listeners.OnFilterItemClickListener;
import com.mojapl.mobile_app.main.models.SettingElement;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listHeaders;
    private HashMap<String, List<SettingElement>> listChild;
    private OnFilterItemClickListener listener;

    public ExpandableListAdapter(Context context, List<String> listHeaders, HashMap<String, List<SettingElement>> listChild) {
        this.context = context;
        this.listHeaders = listHeaders;
        this.listChild = listChild;
    }
    public ExpandableListAdapter(Context context, List<String> listHeaders, HashMap<String, List<SettingElement>> listChild, OnFilterItemClickListener listener) {
        this.context = context;
        this.listHeaders = listHeaders;
        this.listChild = listChild;
        this.listener = listener;
    }



    @Override
    public int getGroupCount() {
        return this.listHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChild.get(this.listHeaders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChild.get(this.listHeaders.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final SettingElement childElement = (SettingElement) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.selected);

      
        txtListChild.setText(childElement.getName());
        checkBox.setChecked(childElement.getSelected());

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
}
