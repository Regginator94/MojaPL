package com.mojapl.mobile_app.main.adapters;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.fragments.DashboardFragment;
import com.mojapl.mobile_app.main.listeners.OnDashboardItemClickListener;
import com.mojapl.mobile_app.main.models.DashboardItem;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardItem> mItemList;
    private OnDashboardItemClickListener mOnItemClickListener;
    Activity mActivity;

    public DashboardAdapter (Activity activity) {
        this.mActivity = activity;
    }

    public void setItems(List<DashboardItem> items) {
        this.mItemList = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnDashboardItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);

        if(mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            params.height = (parent.getMeasuredHeight() / 2) - 8;
            view.setLayoutParams(params);
        } else {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            params.height = parent.getMeasuredHeight() - 8;
            view.setLayoutParams(params);
        }



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mItemList.get(position).getTitle());
        holder.logo.setImageResource(mItemList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView logo;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_item);
            logo = (ImageView) itemView.findViewById(R.id.image_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
