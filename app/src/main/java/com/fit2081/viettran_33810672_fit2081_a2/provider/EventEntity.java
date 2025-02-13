package com.fit2081.viettran_33810672_fit2081_a2.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "event")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "eventTableID")
    private int ID;
    @ColumnInfo(name = "eventID")
    private String EventID;
    @ColumnInfo(name = "eventCategoryID")
    private String EventCategoryID;
    @ColumnInfo(name = "eventName")
    private String EventName;
    @ColumnInfo(name = "ticketsAvailable")
    private int TicketsAvl;
    @ColumnInfo(name = "eventActiveStatus")
    private boolean EventIsActive;

    public EventEntity(String EventID, String EventCategoryID, String EventName, int TicketsAvl, boolean EventIsActive) {
        this.EventID = EventID;
        this.EventCategoryID = EventCategoryID;
        this.EventName = EventName;
        this.TicketsAvl = TicketsAvl;
        this.EventIsActive = EventIsActive;
    }

    public void setID(@NonNull int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
    public String getEventID() {
        return EventID;
    }

    public String getEventCategoryID() {
        return EventCategoryID;
    }

    public String getEventName() {
        return EventName;
    }

    public int getTicketsAvl() {
        return TicketsAvl;
    }

    public boolean getEventIsActive() {
        return EventIsActive;
    }
}
