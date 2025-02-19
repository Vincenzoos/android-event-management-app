package com.fit2081.viettran_33810672_fit2081_a2.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.utils.AppUtils;
import com.fit2081.viettran_33810672_fit2081_a2.view.adapter.CategoryAdapter;
import com.fit2081.viettran_33810672_fit2081_a2.utils.SMSReceiver;
import com.fit2081.viettran_33810672_fit2081_a2.viewmodel.appViewModel;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.CategoryDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.appDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class NewCategoryActivity extends AppCompatActivity {

    // Declare global variables for Category ID, Name, Count and IsActive
    EditText etCatID, etCatName, etEventCount, etCatLocation;
    Switch swCatIsActive;
    MyBroadCastReceiver myBroadCastReceiver;
    ArrayList<CategoryEntity> cateDatabase;
    Gson gson;

    private appViewModel mAppViewModel;
    private appDatabaseHelper databaseHelper;

    private CategoryDatabaseHelper categoryDatabaseHelper;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        // Assign global variables to their corresponding UI elements
        etCatID = findViewById(R.id.etCatID);
        etCatName = findViewById(R.id.etCatName);
        etEventCount = findViewById(R.id.etEventCount);
        swCatIsActive = findViewById(R.id.swCatIsActive);
        etCatLocation = findViewById(R.id.etCatLocation);

        // Set up the toolbar and back button
        Toolbar newCate_toolbar = findViewById(R.id.newCate_toolbar);
        setSupportActionBar(newCate_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Ask the app permission to handle SMS messages
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver
         * */
        myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER), RECEIVER_EXPORTED);


        // Initialize database used to store categories
        categoryDatabaseHelper = new CategoryDatabaseHelper(this);
        cateDatabase = categoryDatabaseHelper.getCategories();

        // create a new instance of Gson:
        gson = new Gson();

        databaseHelper = new appDatabaseHelper();

        // initialise ViewModel
        mAppViewModel = databaseHelper.initViewModel(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // When the NewCategory activity is stop, unregister the myBroadCastReceiver
    @Override
    protected void onStop() {
        super.onStop();
        if (myBroadCastReceiver != null) {
            unregisterReceiver(myBroadCastReceiver);
            myBroadCastReceiver = null;
        }
    }

    public void onSaveCateClick(View view){
        // Fetch values from Text_boxes: Category Name, Event count and Switch: isActive  into their corresponding variables
        String CatName =etCatName.getText().toString();
        String EventCount = etEventCount.getText().toString();
        String CatLocation = etCatLocation.getText().toString();
        boolean CatIsActive = swCatIsActive.isChecked();

        // required fields cannot be empty
        if (!AppUtils.isAlphaNumericContainsWhiteSpace(CatName) || !AppUtils.containsOnlySpace(CatName)) {
            Toast.makeText(this, "Category Name must contain alphabetical characters with or without empty spaces", Toast.LENGTH_SHORT).show();
        } else if (AppUtils.isAlphaNumericContainsWhiteSpace(CatName) && !AppUtils.containsAtLeastOneAlpha(CatName)) {
            Toast.makeText(this, "Category Name must contain alphabetical characters with or without empty spaces", Toast.LENGTH_SHORT).show();
        } else if (!EventCount.isEmpty() && !AppUtils.isPositiveInt(EventCount)) {
            Toast.makeText(this, "Event count field can only contain 0 or positive numbers", Toast.LENGTH_SHORT).show();
        } else if (CatLocation.isEmpty()) {
            Toast.makeText(this, "Please enter a location!", Toast.LENGTH_SHORT).show();
        } else {
            // random generated category ID elements
            Random r = new Random();
            char randChar1 = (char)(r.nextInt(26) + 'A');
            char randChar2 = (char)(r.nextInt(26) + 'A');
            String rand_four_digits = String.format("%04d", r.nextInt(10000));

            // Create auto-generated category ID
            String CatID = "C" + randChar1 + randChar2 + '-' + rand_four_digits;
            etCatID.setText(CatID);

            // Convert Empty Ticket Available to 0
            if (EventCount.isEmpty()){
                EventCount = "0";
            }

            // Add new category to the category database
//            cateDatabase.add(createNewCate(CatID, CatName, Integer.parseInt(EventCount), CatIsActive, CatLocation));
//
//            categoryDatabaseHelper.saveCategories(cateDatabase);
//            Log.e("cateDbStr", gson.toJson(cateDatabase));

            // insert new category to the room database
            CategoryEntity newCate = createNewCate(CatID, CatName, Integer.parseInt(EventCount), CatIsActive, CatLocation);
            mAppViewModel.insertCate(newCate);

            Toast.makeText(this, "Category saved successfully:" + CatID, Toast.LENGTH_SHORT).show();

            // Go back to DashBoard
            finish();
        }
    }

    class MyBroadCastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            // Implement MyStringTokenizer on the retrieved message
            MyStringTokenizer(msg);

        }

        public void MyStringTokenizer(String msg){

            try {
                /*
                 * String Tokenizer is used to parse the incoming message
                 * this first tokenizer split the string into two halves separate by the colon (:)
                 */

                // Verify if the message starts with "category"
                StringTokenizer sT1 = new StringTokenizer(msg, ":");
                String MessageStart = sT1.nextToken();

                if (!MessageStart.equalsIgnoreCase("Category")){
                    throw new IllegalArgumentException("INVALID MESSAGE, correct format is: category:Name;EventCount;IsActive");
                } else {
                    /*
                     * String Tokenizer is used to parse the send half from the first String tokenization implementation
                     * The protocol is to have the  categoryID, EventCount and isActive status separate by a semicolon
                     * */
                    String Message = sT1.nextToken();

                    if (AppUtils.countOccurrences(Message, ';') != 2) { // Expect exactly 2 semicolon ';'
                        throw new IllegalArgumentException("INVALID MESSAGE, correct format is: category:Name;EventCount;IsActive");
                    }
                    else {
                        StringTokenizer sT2 = new StringTokenizer(Message, ";");
                        String catName = sT2.nextToken();
                        String eventCount = sT2.nextToken();
                        String CatIsActive = sT2.nextToken().trim();

                        // Verify message input for Event Count and isActive status
                        if (!AppUtils.isPositiveInt(eventCount)){
                            throw new IllegalArgumentException("INVALID MESSAGE, Event Count must be a positive integer only");
                        } else if (!AppUtils.isBoolean(CatIsActive)) {
                            throw new IllegalArgumentException("INVALID MESSAGE, isActive must be either 'TRUE' or 'FALSE");
                        }
                        else{
                            etCatName.setText(catName);
                            etEventCount.setText(eventCount);
                            swCatIsActive.setChecked(CatIsActive.equalsIgnoreCase("TRUE"));
                        }
                    }

                }
            } catch (IllegalArgumentException e) {
                Toast.makeText(NewCategoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(NewCategoryActivity.this, "INVALID MESSAGE, correct format is: category:Name;EventCount;IsActive", Toast.LENGTH_LONG).show();
            }

        }
    }

    public CategoryEntity createNewCate(String cateID, String cateName, int eventCount, boolean cateIsActive, String cateLocation){
        CategoryEntity newCate = new CategoryEntity(cateID, cateName, eventCount, cateIsActive, cateLocation);
        return newCate;
    }

}