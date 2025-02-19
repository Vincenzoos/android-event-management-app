package com.fit2081.viettran_33810672_fit2081_a2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.repository.appRepository;

import java.util.List;


public class appViewModel extends AndroidViewModel {

    private appRepository mAppRepository;
    private LiveData<List<CategoryEntity>> mAllCate;
    private LiveData<List<EventEntity>> mAllEvent;
    public appViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new appRepository(application);
        mAllCate = mAppRepository.getAllCate();
        mAllEvent = mAppRepository.getAllEvent();
    }

    // CRUD operations for Category Table/Entity
    public LiveData<List<CategoryEntity>> getAllCate(){
        return mAllCate;
    }

    public List<CategoryEntity> getCateHasEvent() {return mAppRepository.getCateHasEvent();}

    public CategoryEntity getCateByID(String categoryID){
        return mAppRepository.getCateByID(categoryID);
    }

    public void insertCate(CategoryEntity category){
        mAppRepository.insertCate(category);
    }

    public void updateCateByID(int newEventCount, String categoryID){
        mAppRepository.updateCateByID(newEventCount, categoryID);
    }

    public void deleteAllCate(){
        mAppRepository.deleteAllCate();
    }

    public void deleteCateByID(String categoryID){
        mAppRepository.deleteCateByID(categoryID);
    }


    // CRUD operations for Event Table/Entity
    public LiveData<List<EventEntity>> getAllEvent(){
        return mAllEvent;
    }

    public List<EventEntity> getEventByCateID(String cateID){
        return mAppRepository.getEventByCateID(cateID);
    };

    public EventEntity getLastEvent(){
        return mAppRepository.getLastEvent();
    }

    public void insertEvent(EventEntity event){
        mAppRepository.insertEvent(event);
    }

    public void deleteAllEvent(){
        mAppRepository.deleteAllEvent();
    }

    public void deleteEventByID(String eventID){
        mAppRepository.deleteEventByID(eventID);
    }
}
