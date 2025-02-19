package com.fit2081.viettran_33810672_fit2081_a2.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;
import com.fit2081.viettran_33810672_fit2081_a2.view.activity.EventLookUpActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<EventEntity> eventDatabase;

//    public MyEventAdapter(ArrayList<EventEntity> eventDatabase) {
//        this.eventDatabase = eventDatabase;
//    }
    public EventAdapter() {}
    
    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        EventEntity event = eventDatabase.get(position);
        String EventID = "ID: " + event.getEventID();
        holder.tvCardEventID.setText(EventID);

        String EventName = "Name: " + event.getEventName();
        holder.tvCardEventName.setText(EventName);

        String EventCateID = "CategoryID: " + event.getEventCategoryID();
        holder.tvCardEventCateID.setText(EventCateID);

        String EventTickets = "Tickets: " + event.getTicketsAvl();
        holder.tvCardEventTickets.setText(EventTickets);

        String EventIsActive = "Active?: " + event.getEventIsActive();
        holder.tvCarEventIsActive.setText(EventIsActive);

        // card onclick listener
        holder.cardViewEvent.setOnClickListener(v ->{
            Context context = holder.cardViewEvent.getContext();
            Intent intent = new Intent(context, EventLookUpActivity.class);
            intent.putExtra("eventName", event.getEventName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(eventDatabase == null)
            return 0;
        else
            return eventDatabase.size();
    }

    public void setEvent(ArrayList<EventEntity> newData) {
        eventDatabase = newData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardEventID, tvCardEventName, tvCardEventCateID, tvCardEventTickets, tvCarEventIsActive;
        View cardViewEvent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewEvent = itemView;
            tvCardEventID = itemView.findViewById(R.id.tvCardEventID);
            tvCardEventName = itemView.findViewById(R.id.tvCardEventName);
            tvCardEventCateID = itemView.findViewById(R.id.tvCardEventCateID);
            tvCardEventTickets = itemView.findViewById(R.id.tvCardEventTickets);
            tvCarEventIsActive = itemView.findViewById(R.id.tvCardEventIsActive);

        }
    }
}
