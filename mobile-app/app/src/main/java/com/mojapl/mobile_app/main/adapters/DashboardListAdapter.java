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

import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Event> mEventList;
    private Context mContext;
    private int expandedPosition = -1;
    private boolean isCollapsed = true;

    public void setItems(List<Event> eventList) {
        this.mEventList = eventList;
        notifyDataSetChanged();
    }

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
        holder.date.setText(mEventList.get(position).getStartDate());
//        String html = "<a href=\""+mEventList.get(position).getUrl()+"\">LINK</a>";
        String html = "Link do źródła: " + mEventList.get(position).getUrl();
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        holder.href.setClickable(true);
        holder.href.setText(html);
        holder.href.setMovementMethod(LinkMovementMethod.getInstance());
        holder.organisationName.setText(mEventList.get(position).getOrganisationName());

        TextView myTextView = holder.getTitle();
        if (mEventList.get(position).isFbPost()) {
            holder.image.setImageResource(R.drawable.fb_logo);

            myTextView.setMaxLines(2);
            String content = mEventList.get(position).getContent();
            holder.title.setText(content);

            holder.content.setText(mEventList.get(position).getContent());
        } else if (mEventList.get(position).isTweet()) {
            holder.image.setImageResource(R.drawable.tweet_logo);

            myTextView.setMaxLines(2);
            String content = mEventList.get(position).getContent();
            holder.title.setText(content);

            holder.content.setText(mEventList.get(position).getContent());
        } else {
            Picasso.with(mContext).load(mEventList.get(position).getImageUrl()).into(holder.image);
            myTextView.setMaxLines(3);
            holder.title.setText(mEventList.get(position).getTitle());
            holder.content.setText(mEventList.get(position).getContent());
        }


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
