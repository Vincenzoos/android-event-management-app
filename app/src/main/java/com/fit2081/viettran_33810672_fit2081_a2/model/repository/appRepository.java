package com.fit2081.viettran_33810672_fit2081_a2.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.viettran_33810672_fit2081_a2.model.database.appDAO;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.appDatabase;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;

import java.util.List;

public class appRepository {

    private appDAO mAppDAO;
    private LiveData<List<CategoryEntity>> mAllCate;
    private LiveData<List<EventEntity>> mAllEvent;

    // appRepository constructor
    public appRepository(Application application){
        appDatabase db = appDatabase.getDatabase(application);
        mAppDAO = db.appDAO();
        mAllCate = mAppDAO.getAllCate();
        mAllEvent = mAppDAO.getAllEvent();
    }


    // CRUD operations for Category Table/Entity
    public LiveData<List<CategoryEntity>> getAllCate(){
        return  mAllCate;
    }
    public List<CategoryEntity> getCateHasEvent(){return  mAppDAO.getCateHasEvent();}
    public CategoryEntity getCateByID(String categoryID){
        return  mAppDAO.getCateByID(categoryID);
    }


    public void insertCate(CategoryEntity category){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addCate(category));
    }

    public void updateCateByID(int newEventCount, String categoryID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.updateCategoryByID(newEventCount, categoryID));
    }

    public void deleteCateByID(String categoryID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteCateByID(categoryID));
    }

    public void deleteAllCate(){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllCate());
    }


    // CRUD operations for Event Table/Entity
    public LiveData<List<EventEntity>> getAllEvent(){
        return mAllEvent;
    }

    public List<EventEntity> getEventByCateID(String cateID){
        return mAppDAO.getEventByCateID(cateID);
    };

    public EventEntity getLastEvent(){return mAppDAO.getLastEvent();}

    public void insertEvent(EventEntity event){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addEvent(event));
    }

    public void deleteEventByID(String eventID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteEventByID(eventID));
    }

    public void deleteAllEvent(){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllEvent());
    }

}
