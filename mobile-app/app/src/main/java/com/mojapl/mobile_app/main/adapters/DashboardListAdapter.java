package com.mojapl.mobile_app.main.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.models.Event;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Event> mEventList;
    private Context mContext;
    private int expandedPosition = -1;
    private boolean isCollapsed = true;

    public DashboardListAdapter(Context context, List<Event> events) {
        mContext = context;
        mEventList = events;
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
        String html = "Link do źródła: " + mEventList.get(position).getUrl();
        holder.href.setClickable(true);
        holder.href.setText(html);
        holder.href.setMovementMethod(LinkMovementMethod.getInstance());
        holder.organisationName.setText(mEventList.get(position).getOrganisationName());

        setDataByOrganisation(holder, position);

        if (mEventList.get(position).getContent() == null) {
            holder.expandedView.setVisibility(View.GONE);
        } else {
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

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentDarkGrey));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentGrey));
        }
    }

    public void setDataByOrganisation(ViewHolder holder, int position) {
        DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        DateFormat outputFormatter;
        TextView myTextView = holder.getTitle();
        if (mEventList.get(position).isFbPost()) {
            holder.image.setImageResource(R.drawable.fb_logo);
            outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

            myTextView.setMaxLines(2);
            String content = mEventList.get(position).getContent();
            holder.title.setText(content);

            holder.content.setText(mEventList.get(position).getContent());
        } else if (mEventList.get(position).isTweet()) {
            holder.image.setImageResource(R.drawable.tweet_logo);
            outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            myTextView.setMaxLines(2);
            String content = mEventList.get(position).getContent();
            holder.title.setText(content);

            holder.content.setText(mEventList.get(position).getContent());
        } else {
            outputFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Picasso.with(mContext).load(mEventList.get(position).getImageUrl()).into(holder.image);
            myTextView.setMaxLines(3);
            holder.title.setText(mEventList.get(position).getTitle());
            holder.content.setText(mEventList.get(position).getContent());
        }


        try {
            Date formattedDate = inputFormatter.parse(mEventList.get(position).getStartDate());
            String output = outputFormatter.format(formattedDate);
            holder.date.setText(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void updateList(List<Event> items) {
        mEventList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEventList == null ? 0 : mEventList.size();
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
        public ImageView image;
        public TextView date;
        public LinearLayout expandedView;
        public TextView content;
        public TextView href;
        public TextView organisationName;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image_view);
            expandedView = (LinearLayout) itemView.findViewById(R.id.expandView);
            content = (TextView) itemView.findViewById(R.id.content);
            date = (TextView) itemView.findViewById(R.id.startDate);
            href = (TextView) itemView.findViewById(R.id.html);
            organisationName = (TextView) itemView.findViewById(R.id.organisation);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getContent() {
            return content;
        }
    }
}
