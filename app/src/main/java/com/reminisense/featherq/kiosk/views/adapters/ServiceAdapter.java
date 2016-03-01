package com.reminisense.featherq.kiosk.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.managers.CacheManager;
import com.reminisense.featherq.kiosk.models.api.businessdetail.Service;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RUFFY on 29 Feb 2016.
 */
public class ServiceAdapter
        extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>
        implements View.OnClickListener {

    private static List<Service> items;
    private Context context;

    // Allows us to remember the last item shown on screen
    private int selectedPosition = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.lblServiceName) TextView serviceName;
        @Bind(R.id.lblPeopleInLine) TextView peopleInLine;
        @Bind(R.id.imgServiceBox) ImageView serviceBox;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ServiceAdapter(List<Service> items, Context context) {
        this.items = items;
        this.context = context;
        this.selectedPosition = -1;
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        // Check for an expanded view, collapse if you find one
        if ( selectedPosition >= 0 ) {
            int prev = selectedPosition;
            notifyItemChanged(prev);
        }

        if ( selectedPosition == holder.getPosition() ) {
            int prev = selectedPosition;
            selectedPosition = -1;
            notifyItemChanged(prev);
        } else {
            // Set the current position to "expanded"
            selectedPosition = holder.getPosition();
            notifyItemChanged(selectedPosition);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_service_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        // Sets the click adapter for the entire cell
        // to the one in this class
        holder.itemView.setOnClickListener(ServiceAdapter.this);
        holder.itemView.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Service service = items.get(position);

        holder.serviceName.setText(service.getName());

        String peopleInLineLabel = "PEOPLE IN LINE: ~";
        holder.peopleInLine.setText(peopleInLineLabel + service.getPeopleInQueue());

        if ( position == selectedPosition ) {
            holder.serviceBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.service_box_default));
            holder.serviceName.setTextColor(ContextCompat.getColor(context, R.color.black));

            Service selected = items.get(selectedPosition);
            CacheManager.storeSelectedService(context, selected);
            // save selected service id to SharedPreferences
        } else {
            holder.serviceBox.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.service_box_unselected));
            holder.serviceName.setTextColor(ContextCompat.getColor(context, R.color.caption_grey));
        }
    }
}
