package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.CategoryDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.EventDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.appDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.EventEntity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class DashBoard extends AppCompatActivity {

    // Declare global variables
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar myToolBar;
    EditText etEventID, etEventName, etEventCatID, etTicketsAvl;
    Switch swEventIsActive;
    MyBroadCastReceiver myBroadCastReceiver;
    ArrayList<EventEntity>eventDatabase = new ArrayList<>();
    ArrayList<CategoryEntity>cateDatabase = new ArrayList<>();
    ArrayList<String> listCategoryID = new ArrayList<>();
    Gson gson;
    private EventDatabaseHelper eventDatabaseHelper;
    private CategoryDatabaseHelper categoryDatabaseHelper;

    public static final String EVENT_DATABASE_KEY = "event_database_key";
    public static final String CATE_DATABASE_KEY = "category_database_key";

    private appViewModel mAppViewModel;
    private appDatabaseHelper databaseHelper;
    private View touchpadView, dashboardLayout;
    private TextView tvGestureType;
    private GestureDetector mDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Set navigationView to nav_view in the drawer_layout
        navigationView = findViewById(R.id.nav_view);
        // set navigation listener to catch any mouse clicked
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        // Initialize the drawer layout to the id of
        drawerLayout = findViewById(R.id.drawerlayout);

        // Initialize toolbar
        myToolBar = findViewById(R.id.app_toolbar);

        // Set toolbar to act as an action bar for this Activity window (DashBoard)
        setSupportActionBar(myToolBar);

        // Customize toolbar attributes and handle actions as needed
        getSupportActionBar().setTitle("Assignment 3");



        // Hook the Drawer and the Toolbar
        // sets up a toggle button in the ActionBar/Toolbar of an activity to control the opening and closing of a navigation drawer.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, myToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Assign global variables to their corresponding UI elements
        etEventID = findViewById(R.id.etEventID);
        etEventName = findViewById(R.id.etEventName);
        etEventCatID = findViewById(R.id.etEventCatID);
        etTicketsAvl = findViewById(R.id.etTicketsAvl);
        swEventIsActive = findViewById(R.id.swEventIsActive);

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

        // Initialize database used to store events and categories
//        eventDatabaseHelper = new EventDatabaseHelper(this);
//        eventDatabase = eventDatabaseHelper.getEvents();
//
//        categoryDatabaseHelper = new CategoryDatabaseHelper(this);
//        cateDatabase = categoryDatabaseHelper.getCategories();
//        for (CategoryEntity category : cateDatabase) {
//            listCategoryID.add(category.getCateID());
//        }



        // create a new instance of Gson:
        gson = new Gson();

        // Add the fragment to the host container
        getSupportFragmentManager().beginTransaction().replace(
                R.id.dashboard_host_container, new FragmentListCategory()).commit();

        databaseHelper = new appDatabaseHelper();
        // initialise ViewModel
        mAppViewModel = databaseHelper.initViewModel(this);

        cateDatabase = databaseHelper.Categories(DashBoard.this, mAppViewModel);
        eventDatabase = databaseHelper.Events(DashBoard.this, mAppViewModel);
        Log.e("cateDbStr",gson.toJson(cateDatabase));
        Log.e("eventDbStr",gson.toJson(eventDatabase));

        // Initialize touch pad (of type view)
        touchpadView = findViewById(R.id.touchpadView);

        dashboardLayout = findViewById(R.id.dashboardLayout);

        // Initialize text view to display gesture type on the touch pad
        tvGestureType = findViewById(R.id.tvGestureType);

        // Initialize
        MyGestureListener myGestureListener = new MyGestureListener();
        mDetector = new GestureDetector(this, myGestureListener);
        touchpadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

    }

    // Gesture Listener
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            onClearEventClick();
            tvGestureType.setText("OnLongPress!");
        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            onSaveEventClick(dashboardLayout);
            tvGestureType.setText("OnDoubleTap!");
            return true;
        }
    }

    // Navigation view listener
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.drawer_ViewAllEvents) {
                onViewAllEventsClick();
            } else if (id == R.id.drawer_ViewAllCate) {
                onViewAllCategoryClick();
            } else if (id == R.id.drawer_AddCate) {
                onNewCategoryClick();
            }else if (id == R.id.drawer_logout) {
                onLogOutClick();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
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

    public void onSaveEventClick(View view){
        // Fetch values from UI elements into their corresponding variables
        String EventName = etEventName.getText().toString();
        String EventCatID = etEventCatID.getText().toString();
        String TicketsAvl = etTicketsAvl.getText().toString();
        boolean EventIsActive = swEventIsActive.isChecked();
        listCategoryID = databaseHelper.listCateID(this, mAppViewModel);
        Log.e("listCateID",gson.toJson(listCategoryID));

        // input fields validation
        if (EventName.isEmpty() || EventCatID.isEmpty()){
            Toast.makeText(this, "Missing required fields (Event Name and/or Category ID)", Toast.LENGTH_LONG).show();
        } else if (!listCategoryID.contains(EventCatID)){
            Toast.makeText(this, "Category/Category ID does not exist", Toast.LENGTH_LONG).show();
        } else if (!MyHelper.isAlphaNumericContainsWhiteSpace(EventName) || !MyHelper.containsOnlySpace(EventName)) {
            Toast.makeText(this, "Event Name must contain alphabetical characters with or without empty spaces", Toast.LENGTH_SHORT).show();
        } else if (MyHelper.isAlphaNumericContainsWhiteSpace(EventName) && !MyHelper.containsAtLeastOneAlpha(EventName)) {
            Toast.makeText(this, "Event Name must contain alphabetical characters with or without empty spaces", Toast.LENGTH_SHORT).show();
        }else if (!TicketsAvl.isEmpty() && !MyHelper.isPositiveInt(TicketsAvl)) {
            Toast.makeText(this, "Tickets field can only contain 0 or positive numbers", Toast.LENGTH_LONG).show();
        }else {
            // random generated category ID elements
            Random r = new Random();
            char randChar1 = (char)(r.nextInt(26) + 'A');
            char randChar2 = (char)(r.nextInt(26) + 'A');
            String rand_five_digits = String.format("%04d", r.nextInt(100000));

            // Create auto-generated Event ID
            String EventID = "E" + randChar1 + randChar2 + "-" + rand_five_digits;
            etEventID.setText(EventID);

            // Convert Empty Ticket Available to 0
            if (TicketsAvl.isEmpty()){
                TicketsAvl = "0";
            }

             // Add the new event to the room database
             EventEntity newEvent = createNewEvent(EventID, EventCatID, EventName, Integer.parseInt(TicketsAvl), EventIsActive);
             mAppViewModel.insertEvent(newEvent);

             // Update Category event count in room database
            CategoryEntity curCate = mAppViewModel.getCateByID(EventCatID);
            Log.e("curcate",gson.toJson(curCate));
            int newEventCount = curCate.getEventCount() + 1;
            Log.e("newEventCount",String.valueOf(newEventCount));
            mAppViewModel.updateCateByID(newEventCount, EventCatID);
            Snackbar mySnackbar = Snackbar.make(view, "Event saved: " + EventID + " to " + EventCatID, Snackbar.LENGTH_LONG);
            mySnackbar.setAction("Undo", new MyUndoListener()).show();
        }

    }
    class MyBroadCastReceiver extends BroadcastReceiver {

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
            try{
                // Verify if message starts with "event"
                StringTokenizer sT1 = new StringTokenizer(msg, ":");
                String MessageStart = sT1.nextToken();
                if (!MessageStart.equalsIgnoreCase("Event")){
                    throw new IllegalArgumentException("INVALID MESSAGE, correct format: event:Name;CategoryId;Tickets;IsActive");
                }else {
                    /*
                     * String Tokenizer is used to parse the send half from the first String tokenization implementation
                     * The protocol is to have the  EventName, categoryID, Tickets Available and isActive status separate by a semicolon
                     * */
                    String Message = sT1.nextToken();
                    if (MyHelper.countOccurrences(Message, ';') != 3) { // Expect exactly 3 semicolon ';'
                        throw new IllegalArgumentException("INVALID MESSAGE, correct format is: event:Name;CategoryId;Tickets;IsActive");
                    }
                    else{
                        StringTokenizer sT2 = new StringTokenizer(Message, ";");
                        String eventName = sT2.nextToken();
                        String eventCatID = sT2.nextToken();
                        String ticketsAvl = sT2.nextToken();
                        String eventIsActive = sT2.nextToken().trim();

                        // Verify message inputs
                        if (!MyHelper.isPositiveInt(ticketsAvl)){
                            throw new IllegalArgumentException("INVALID MESSAGE, Tickets Available must be positive integer or 0");
                        } else if (!MyHelper.isBoolean(eventIsActive)) {
                            throw new IllegalArgumentException("INVALID MESSAGE, Is Active status must be either 'TRUE' or 'FALSE'");
                        }
                        else {
                            etEventName.setText(eventName);
                            etEventCatID.setText(eventCatID);
                            etTicketsAvl.setText(ticketsAvl);
                            swEventIsActive.setChecked(eventIsActive.equalsIgnoreCase("TRUE"));
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                Toast.makeText(DashBoard.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e){
                Toast.makeText(DashBoard.this, "INVALID MESSAGE, correct format: event:Name;CategoryId;Tickets;IsActive", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 1st param: reference to the menu resource file in use
        // 2nd param: the menu that will be inflated by the resource file
        getMenuInflater().inflate(R.menu.options_menu, menu);

        // Inform Android that this Activity code is implementing option menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // get the id of the selected item
        int opt_item_id = item.getItemId();
        if (opt_item_id == R.id.toolbar_Refresh){
            Toast.makeText(this, "Page refresh", Toast.LENGTH_SHORT).show();
            onRefreshClick();
        } else if (opt_item_id == R.id.toolbar_ClrEventFrm) {
            onClearEventClick();
            Toast.makeText(this, "All input fields cleared", Toast.LENGTH_SHORT).show();
        } else if (opt_item_id == R.id.toolbar_DelAllCate) {
            onDelAllCateClick();
        } else if (opt_item_id == R.id.toolbar_DelAllEvents) {
            onDelAllEventClick();
        }

        // Tell the OS if any item selected
        return true;
    }

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Code to undo the user's last action.
            // Remove the last event from the database and
            EventEntity lastEvent = mAppViewModel.getLastEvent();
            mAppViewModel.deleteEventByID(lastEvent.getEventID());
            eventDatabase = databaseHelper.Events(DashBoard.this, mAppViewModel);
            Log.e("eventDbStr", String.valueOf(eventDatabase.size()));

            // Update the associated category event count
            String curCateID = lastEvent.getEventCategoryID();
            CategoryEntity curCate = mAppViewModel.getCateByID(curCateID);
            int newEventCount = curCate.getEventCount() - 1;
            mAppViewModel.updateCateByID(newEventCount, curCateID);


        }
    }

    public EventEntity createNewEvent(String eventID, String categoryID, String eventName, int ticketsAvl, boolean eventIsActive){
        EventEntity newEvent = new EventEntity(eventID, categoryID, eventName, ticketsAvl, eventIsActive);
        return newEvent;
    }

    public void onRefreshClick(){
        finish();
        startActivity(getIntent());
    }

    public void onClearEventClick(){
        etEventID.setText("");
        etEventName.setText("");
        etEventCatID.setText("");
        etTicketsAvl.setText("");
        swEventIsActive.setChecked(false);
    }
    public void onNewCategoryClick(){
        Intent NewCate = new Intent(this, NewCategory.class);
        startActivity(NewCate);
    }

    public void onViewAllCategoryClick(){
        Intent AllCate = new Intent(this,ListCategoryActivity.class);
        startActivity(AllCate);
    }

    public void onViewAllEventsClick(){
        Intent AllEvent = new Intent(this,ListEventActivity.class);
        startActivity(AllEvent);
    }

    public void onLogOutClick(){
        finish();
    }

    public void onDelAllEventClick(){

        List<CategoryEntity> listCateHasEvent = mAppViewModel.getCateHasEvent();
        Log.e("listCateHasEvent",gson.toJson(listCateHasEvent));
        try {
            for (CategoryEntity category: listCateHasEvent){
                int eventCountCategory = category.getEventCount();
                if (eventCountCategory > 0){
                    List<EventEntity> listEventWithCate = mAppViewModel.getEventByCateID(category.getCateID());
                    int newEventCount = (eventCountCategory - listEventWithCate.size());

                    // Prevent negative event count caused by
                    // case where event with current category id accidentally existed in the database system
                    // (for example the category id is reused and event of the old version has not been cleared from the database)
                    if (newEventCount < 0) {
                        newEventCount = 0;
                    }
                    mAppViewModel.updateCateByID(newEventCount, category.getCateID());
                }
            }
            mAppViewModel.deleteAllEvent();
            Toast.makeText(this, "All saved events deleted", Toast.LENGTH_SHORT).show();
            onRefreshClick();
        } catch (Exception e){
            Toast.makeText(this, "No event exists in database", Toast.LENGTH_SHORT).show();
        }
    }
    public void onDelAllCateClick(){
        try{
            cateDatabase.clear();
            mAppViewModel.deleteAllCate();
            Toast.makeText(this, "All saved categories deleted", Toast.LENGTH_SHORT).show();
            onRefreshClick();
        } catch (Exception e){
            Toast.makeText(this, "No category exists in database", Toast.LENGTH_SHORT).show();
        }

    }
}