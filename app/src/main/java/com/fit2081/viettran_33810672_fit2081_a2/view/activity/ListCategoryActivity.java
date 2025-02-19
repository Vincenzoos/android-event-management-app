package com.fit2081.viettran_33810672_fit2081_a2.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.view.fragment.FragmentListCategory;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.CategoryDatabaseHelper;
import com.google.gson.Gson;

public class ListCategoryActivity extends AppCompatActivity {
    Gson gson;
    Toolbar AllCateToolbar;

    private CategoryDatabaseHelper categoryDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);

        // Set up the toolbar and back button
        AllCateToolbar = findViewById(R.id.cate_toolbar);
        setSupportActionBar(AllCateToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Add the fragment to the host container
        getSupportFragmentManager().beginTransaction().replace(
                R.id.listCate_host_container, new FragmentListCategory()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}