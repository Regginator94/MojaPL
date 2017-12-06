package com.mojapl.mobile_app.main.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Event> mEventList;
    private Context mContext;
    private int expandedPosition = -1;
    private boolean isCollapsed = true;
    private boolean isEventNew = true;
    private DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());


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

        SharedPreferences preferences = mContext.getSharedPreferences("LoginData", MODE_PRIVATE);
        String lastLogin = preferences.getString("lastLogin", null);
        String createDate = mEventList.get(position).getCreateDate();

//        if(position == 1) {
//            createDate = "2017-12-05T23:28:50.000Z";
//        }

        if (mEventList.size() > 1) {
            Date lastLoginFormatted = inputFormatter.parse(lastLogin, new ParsePosition(0));
            Date createDateFormatted = inputFormatter.parse(createDate, new ParsePosition(0));

            if (lastLoginFormatted.compareTo(createDateFormatted) <= 0) {
                if (isEventNew) {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentDarkGrey));
                }
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentGrey));
            }
        }
    }

    private void setDataByOrganisation(ViewHolder holder, int position) {
        TextView myTextView = holder.getTitle();
        DateFormat outputFormatter;
        if (mEventList.get(position).isFbPost() || mEventList.get(position).isTweet()) {
            outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            myTextView.setMaxLines(2);
            String content = mEventList.get(position).getContent();
            holder.title.setText(content);
        } else {
            outputFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            myTextView.setMaxLines(3);
            holder.title.setText(mEventList.get(position).getTitle());
        }

        holder.content.setText(mEventList.get(position).getContent());

        if (mEventList.size() > 1) {
            try {
                Date formattedDate = inputFormatter.parse(mEventList.get(position).getStartDate());
                String output = outputFormatter.format(formattedDate);
                holder.date.setText(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (mEventList.get(position).getImageUrl() == null) {
            switch (mEventList.get(position).getOrganisationId()) {
                case 2:
                    holder.image.setImageResource(R.drawable.weeia);
                    break;
                case 3:
                    holder.image.setImageResource(R.drawable.mechanicnzy);
                    break;
                case 4:
                    holder.image.setImageResource(R.drawable.chemiczny);
                    break;
                case 201:
                    holder.image.setImageResource(R.drawable.futurysta);
                    break;
                case 301:
                    holder.image.setImageResource(R.drawable.radio_zak);
                    break;
                case 304:
                    holder.image.setImageResource(R.drawable.azs);
                    break;
                case 312:
                    holder.image.setImageResource(R.drawable.biblioteka);
                    break;
                case 309:
                    holder.image.setImageResource(R.drawable.biuro_karier);
                    break;
                case 307:
                    holder.image.setImageResource(R.drawable.logo_erasmus);
                    break;
                case 310:
                    holder.image.setImageResource(R.drawable.dot_net);
                    break;
                case 305:
                    holder.image.setImageResource(R.drawable.iaeste_logo);
                    break;
                case 308:
                    holder.image.setImageResource(R.drawable.samorzad);
                    break;
                case 306:
                    holder.image.setImageResource(R.drawable.spotted);
                    break;
                case 311:
                    holder.image.setImageResource(R.drawable.zatoka);
                    break;
                case 402:
                    holder.image.setImageResource(R.drawable.finestra);
                    break;
            }
        } else {
            Picasso.with(mContext).load(mEventList.get(position).getImageUrl()).into(holder.image);
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
        if (isEventNew) {
            isEventNew = false;
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentGrey));
        }

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
