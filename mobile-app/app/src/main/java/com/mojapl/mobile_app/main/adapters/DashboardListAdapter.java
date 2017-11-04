package com.mojapl.mobile_app.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.models.DashboardListItem;

import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> implements View.OnClickListener {

    private List<DashboardListItem> mItemList;

    private int expandedPosition = -1;
    private boolean isCollapsed = true;

    public void setItems(List<DashboardListItem> items) {
        this.mItemList = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_list, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(DashboardListAdapter.this);
        holder.itemView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(mItemList.get(position).getTitle());
        holder.logo.setImageResource(mItemList.get(position).getUrl());
        holder.content.setText(mItemList.get(position).getContent());

        if (position == expandedPosition && isCollapsed) {
            holder.expandedView.setVisibility(View.VISIBLE);
            isCollapsed = false;
        } else if (position == expandedPosition && !isCollapsed) {
            holder.expandedView.setVisibility(View.GONE);
            isCollapsed = true;
        } else {
            holder.expandedView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (expandedPosition >= 0) {
            int prev = expandedPosition;
            notifyItemChanged(prev);
        }
        expandedPosition = holder.getAdapterPosition();
        notifyItemChanged(expandedPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView logo;
        public LinearLayout expandedView;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_view);
            logo = (ImageView) itemView.findViewById(R.id.image_view);
            expandedView = (LinearLayout) itemView.findViewById(R.id.expandView);
            content = (TextView) itemView.findViewById(R.id.content);
        }

    }
}
