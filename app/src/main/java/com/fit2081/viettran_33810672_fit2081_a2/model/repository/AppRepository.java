package com.fit2081.viettran_33810672_fit2081_a2.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.viettran_33810672_fit2081_a2.model.database.AppDAO;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.AppDatabase;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;

import java.util.List;

public class AppRepository {

    private AppDAO mAppDAO;
    private LiveData<List<CategoryEntity>> mAllCate;
    private LiveData<List<EventEntity>> mAllEvent;

    // appRepository constructor
    public AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
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
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addCate(category));
    }

    public void updateCateByID(int newEventCount, String categoryID){
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.updateCategoryByID(newEventCount, categoryID));
    }

    public void deleteCateByID(String categoryID){
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteCateByID(categoryID));
    }

    public void deleteAllCate(){
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllCate());
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
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.addEvent(event));
    }

    public void deleteEventByID(String eventID){
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteEventByID(eventID));
    }

    public void deleteAllEvent(){
        AppDatabase.databaseWriteExecutor.execute(() -> mAppDAO.deleteAllEvent());
    }

}
