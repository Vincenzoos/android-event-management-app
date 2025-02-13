package com.fit2081.viettran_33810672_fit2081_a2;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fit2081.viettran_33810672_fit2081_a2.provider.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.provider.EventEntity;
import com.fit2081.viettran_33810672_fit2081_a2.provider.appViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class appDatabaseHelper {
    CategoryEntity curCate;
    public appViewModel initViewModel(ViewModelStoreOwner viewModelStoreOwner){
        // initialise ViewModel
        return new ViewModelProvider(viewModelStoreOwner).get(appViewModel.class);
    }
    public ArrayList<CategoryEntity> Categories(LifecycleOwner lifecycleOwner, appViewModel mAppViewModel){
        ArrayList<CategoryEntity> cateDatabase = new ArrayList<>();

        LiveData<List<CategoryEntity>> liveDataCategories = mAppViewModel.getAllCate();
        liveDataCategories.observe(lifecycleOwner, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categories) {
                cateDatabase.addAll(categories);
            }
        });
        return cateDatabase;
    }

    public ArrayList<String> listCateID(LifecycleOwner lifecycleOwner, appViewModel mAppViewModel){
        ArrayList<String> listCateID = new ArrayList<>();
        LiveData<List<CategoryEntity>> liveDataCategories = mAppViewModel.getAllCate();
        liveDataCategories.observe(lifecycleOwner, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categories) {
                for (CategoryEntity c : categories) {
                    listCateID.add(c.getCateID());
                }
            }
        });
        return listCateID;
    }

//    public ArrayList<CategoryEntity> listCateHasEvent(LifecycleOwner lifecycleOwner, appViewModel mAppViewModel){
//        ArrayList<CategoryEntity> categoriesHasEvent = new ArrayList<>();
//        LiveData<List<CategoryEntity>> liveDataCategories = mAppViewModel.getCateHasEvent();
//        liveDataCategories.observe(lifecycleOwner, new Observer<List<CategoryEntity>>() {
//            @Override
//            public void onChanged(List<CategoryEntity> categories) {
//                categoriesHasEvent.addAll(categories);
//            }
//        });
//        return categoriesHasEvent;
//    }

    public ArrayList<EventEntity> Events(LifecycleOwner lifecycleOwner, appViewModel mAppViewModel){
        ArrayList<EventEntity> eventDatabase = new ArrayList<>();
        LiveData<List<EventEntity>> liveDataEvents = mAppViewModel.getAllEvent();
        liveDataEvents.observe(lifecycleOwner, new Observer<List<EventEntity>>() {
            @Override
            public void onChanged(List<EventEntity> events) {
                eventDatabase.addAll(events);
            }
        });
        return eventDatabase;
    }

    public void subscribeCateToLiveData(LifecycleOwner lifecycleOwner ,appViewModel mAppViewModel ,MyCategoryAdapter myCategoryAdapter){
        // subscribe to LiveData of type ArrayList<>,
        // any changes detected in the database will be notified to this fragment
        mAppViewModel.getAllCate().observe(lifecycleOwner, newData -> {
            // cast List<Item> to ArrayList<>
            myCategoryAdapter.setCategory((ArrayList<CategoryEntity>) newData);
            myCategoryAdapter.notifyDataSetChanged();
        });
    }

    public void subscribeEventToLiveData(LifecycleOwner lifecycleOwner ,appViewModel mAppViewModel ,MyEventAdapter myEventAdapter){
        // subscribe to LiveData of type ArrayList<>,
        // any changes detected in the database will be notified to this fragment
        // subscribe to LiveData of type ArrayList<>,
        // any changes detected in the database will be notified to this fragment
        mAppViewModel.getAllEvent().observe(lifecycleOwner, newData -> {
            // cast List<Item> to ArrayList<>
            myEventAdapter.setEvent((ArrayList<EventEntity>) newData);
            myEventAdapter.notifyDataSetChanged();
        });
    }

//    public CategoryEntity getCate(LifecycleOwner lifecycleOwner, appViewModel mAppViewModel, String categoryID){
//        LiveData<CategoryEntity> categoryLiveData = mAppViewModel.getCateByID(categoryID);
//        categoryLiveData.observe(lifecycleOwner, new Observer<CategoryEntity>() {
//            @Override
//            public void onChanged(CategoryEntity category) {
//                // Handle the categoryEntity result
//                if (category != null) {
//                    // CategoryEntity is not null, handle it
//                    curCate = category;
//                } else {
//                    // CategoryEntity is null, handle the case where the record doesn't exist
//                    curCate = null;
//                }
//            }
//        });
//        return curCate;
//    }



}
