package com.fit2081.viettran_33810672_fit2081_a2.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;

import java.util.List;

// Indicates that this interface is a Data Access Object (DAO),
// used for interacting with the database.
@Dao
public interface AppDAO {

    // CRUD operations for Category Table/Entity
    @Query("select * from category")
    LiveData<List<CategoryEntity>>  getAllCate();

    @Query("select * from category where categoryID=:cateID")
    CategoryEntity getCateByID(String cateID);

    @Query("select * from category where eventCount > 0")
    List<CategoryEntity> getCateHasEvent();


    @Query("update category set eventCount=:newEventCount where categoryID=:cateID")
    void updateCategoryByID(int newEventCount, String cateID);

    @Insert
    void addCate(CategoryEntity category);

    @Query("delete from category where categoryID=:cateID")
    void deleteCateByID(String cateID);

    @Query("delete from category")
    void deleteAllCate();


    // CRUD operations for Event Table/Entity
    @Query("select * from event")
    LiveData<List<EventEntity>> getAllEvent();

    @Query("select * from event where eventID=:eventId")
    EventEntity getEventByID(String eventId);

    @Query("select * from event where eventCategoryID=:cateID")
    List<EventEntity> getEventByCateID(String cateID);

    @Query("select * from event order by eventTableID desc limit 1")
    EventEntity getLastEvent();

    @Insert
    void addEvent(EventEntity event);

    @Query("delete from event where eventID=:eventId")
    void deleteEventByID(String eventId);

    @Query("delete from event")
    void deleteAllEvent();

}
