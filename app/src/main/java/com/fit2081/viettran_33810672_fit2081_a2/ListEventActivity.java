package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.google.gson.Gson;


public class ListEventActivity extends AppCompatActivity {
    Gson gson;
    Toolbar AllEventToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        // Set up the toolbar and back button
        AllEventToolbar = findViewById(R.id.event_toolbar);
        setSupportActionBar(AllEventToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Add the fragment to the host container
        getSupportFragmentManager().beginTransaction().replace(
                R.id.listEvent_host_container, new FragmentListEvent()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}