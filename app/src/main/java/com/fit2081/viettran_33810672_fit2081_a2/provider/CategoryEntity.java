package com.fit2081.viettran_33810672_fit2081_a2.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "category")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "categoryTableID")
    private int ID;
    @ColumnInfo(name = "categoryID")
    private String CateID;

    @ColumnInfo(name = "categoryName")
    private String CateName;
    @ColumnInfo(name = "eventCount")
    private int EventCount;
    @ColumnInfo(name = "categoryActiveStatus")
    private boolean CateIsActive;

    @ColumnInfo(name = "categoryLocation")
    private String CategoryLocation;

    public CategoryEntity(String CateID, String CateName, int EventCount, boolean CateIsActive, String CategoryLocation) {
        this.CateID = CateID;
        this.CateName  = CateName;
        this.EventCount = EventCount;
        this.CateIsActive = CateIsActive;
        this.CategoryLocation = CategoryLocation;
    }

    public void setEventCount(int eventCount) {
        EventCount = eventCount;
    }

    public void setID(@NonNull int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getCateID() {
        return CateID;
    }

    public String getCateName() {
        return CateName;
    }

    public int getEventCount() {
        return EventCount;
    }

    public boolean getCateIsActive() {
        return CateIsActive;
    }

    public String getCategoryLocation() {
        return CategoryLocation;
    }
}
