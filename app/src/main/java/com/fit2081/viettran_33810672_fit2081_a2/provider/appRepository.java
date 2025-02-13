package com.fit2081.viettran_33810672_fit2081_a2.provider;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class appRepository {

    private appDAO mAppDAO;
    private LiveData<List<CategoryEntity>> mAllCate;
    private LiveData<List<EventEntity>> mAllEvent;

    // appRepository constructor
    appRepository (Application application){
        appDatabase db = appDatabase.getDatabase(application);
        mAppDAO = db.appDAO();
        mAllCate = mAppDAO.getAllCate();
        mAllEvent = mAppDAO.getAllEvent();
    }


    // CRUD operations for Category Table/Entity
    LiveData<List<CategoryEntity>> getAllCate(){
        return  mAllCate;
    }
    List<CategoryEntity> getCateHasEvent(){return  mAppDAO.getCateHasEvent();}
    CategoryEntity getCateByID(String categoryID){
        return  mAppDAO.getCateByID(categoryID);
    }


    void insertCate(CategoryEntity category){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addCate(category));
    }

    void updateCateByID(int newEventCount, String categoryID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.updateCategoryByID(newEventCount, categoryID));
    }

    void deleteCateByID(String categoryID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteCateByID(categoryID));
    }

    void deleteAllCate(){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllCate());
    }


    // CRUD operations for Event Table/Entity
    LiveData<List<EventEntity>> getAllEvent(){
        return mAllEvent;
    }

    List<EventEntity> getEventByCateID(String cateID){
        return mAppDAO.getEventByCateID(cateID);
    };

    EventEntity getLastEvent(){return mAppDAO.getLastEvent();}

    void insertEvent(EventEntity event){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addEvent(event));
    }

    void deleteEventByID(String eventID){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteEventByID(eventID));
    }

    void deleteAllEvent(){
        appDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllEvent());
    }

}
