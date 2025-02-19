package com.fit2081.viettran_33810672_fit2081_a2.model.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EventDatabaseHelper {

    private String eventDbStr;
    private Gson gson;
    private SharedPreferences sp_Event;
    private SharedPreferences.Editor editor_sp_Event;
    private static final String EVENT_DATABASE_KEY = "event_database_key";
    private static final String EVENT_SP_NAME = "sp_Events";

    public EventDatabaseHelper(Context context){
        sp_Event = context.getSharedPreferences(EVENT_SP_NAME, context.MODE_PRIVATE);
        editor_sp_Event = sp_Event.edit();
        gson = new Gson();
    }

    public void saveEvents(ArrayList<EventEntity> eventDatabase){
        // convert the ArrayList object/cateDatabase into a string (i.e. JSON):
        eventDbStr = gson.toJson(eventDatabase);
        editor_sp_Event.putString(EVENT_DATABASE_KEY, eventDbStr);
        editor_sp_Event.apply();
    }

    public ArrayList<EventEntity> getEvents(){
        eventDbStr = sp_Event.getString(EVENT_DATABASE_KEY, null);
        Type type = new TypeToken<ArrayList<EventEntity>>() {}.getType();
        ArrayList<EventEntity> eventDatabase = gson.fromJson(eventDbStr, type);
        if (eventDatabase != null){
            return eventDatabase;
        } else {
            return new ArrayList<>();
        }
    }

    public void deleteAllEvents(){
        if (sp_Event.contains(EVENT_DATABASE_KEY)){

            editor_sp_Event.remove(EVENT_DATABASE_KEY);
            editor_sp_Event.apply();
        } else {
            throw new IllegalArgumentException("No event exists in database");
        }
    }
}
