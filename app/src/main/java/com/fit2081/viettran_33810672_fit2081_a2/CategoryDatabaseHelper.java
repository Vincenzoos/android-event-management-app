package com.fit2081.viettran_33810672_fit2081_a2;

import android.content.Context;
import android.content.SharedPreferences;

import com.fit2081.viettran_33810672_fit2081_a2.provider.CategoryEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryDatabaseHelper {
    private SharedPreferences sp_Cate;
    private SharedPreferences.Editor editor_sp_Cate;
    private Gson gson;
    private String cateDbStr;
    private static final String CATE_DATABASE_KEY = "category_database_key";
    private static final String CATE_SP_NAME = "sp_Categories";

    public CategoryDatabaseHelper(Context context){
        sp_Cate = context.getSharedPreferences(CATE_SP_NAME, context.MODE_PRIVATE);
        gson = new Gson();
        editor_sp_Cate = sp_Cate.edit();
    }

    public void saveCategories(ArrayList<CategoryEntity> cateDatabase){
        // convert the ArrayList object/cateDatabase into a string (i.e. JSON):
        cateDbStr = gson.toJson(cateDatabase);
        editor_sp_Cate.putString(CATE_DATABASE_KEY, cateDbStr);
        editor_sp_Cate.apply();

    }

    public ArrayList<CategoryEntity> getCategories(){
        cateDbStr = sp_Cate.getString(CATE_DATABASE_KEY, null);
        Type type = new TypeToken<ArrayList<CategoryEntity>>() {}.getType();
        ArrayList<CategoryEntity> cateDatabase = gson.fromJson(cateDbStr, type);
        if (cateDatabase != null){
            return cateDatabase;
        }else {
            return new ArrayList<>();
        }
    }

    public void deleteAllCategories(){
        if (sp_Cate.contains(CATE_DATABASE_KEY)){
            editor_sp_Cate.remove(CATE_DATABASE_KEY);
            editor_sp_Cate.apply();
        }
        else {
            throw new IllegalArgumentException("No category exists in database");
        }
    }
}
