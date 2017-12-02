package com.mojapl.mobile_app.main.adapters;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.listeners.OnDashboardItemClickListener;
import com.mojapl.mobile_app.main.models.DashboardItem;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardItem> mItemList;
    private OnDashboardItemClickListener mOnItemClickListener;
    Activity mActivity;

    int i = 0;

    public DashboardAdapter(Activity activity) {
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
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);

        final GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        if (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.height = (parent.getMeasuredHeight() / 2) - 4;
        } else {
            if ((mActivity.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                params.height = (parent.getHeight() / 2) - 4;
            } else {
                params.height = parent.getMeasuredHeight() - 4;
            }
        }

        view.setLayoutParams(params);

        if (i == 1 || i == 2) {
            view.setBackgroundResource(R.color.colorTransparentDarkRed);
        } else {
            view.setBackgroundResource(R.color.colorTransparentRed);
        }

        i += 1;

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
