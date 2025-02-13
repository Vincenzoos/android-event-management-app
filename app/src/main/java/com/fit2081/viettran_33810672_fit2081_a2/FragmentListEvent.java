package com.fit2081.viettran_33810672_fit2081_a2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.provider.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.provider.EventEntity;
import com.fit2081.viettran_33810672_fit2081_a2.provider.appViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListEvent extends Fragment {

    Gson gson;
    ArrayList<EventEntity> eventDatabase;
    private EventDatabaseHelper eventDatabaseHelper;
    private appDatabaseHelper databaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListEvent newInstance(String param1, String param2) {
        FragmentListEvent fragment = new FragmentListEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View eventFragment = inflater.inflate(R.layout.fragment_list_event, container, false);

//        eventDatabaseHelper = new EventDatabaseHelper(eventFragment.getContext());
//        eventDatabase = eventDatabaseHelper.getEvents();
        gson = new Gson();

        Log.e("eventDb", gson.toJson(eventDatabase));
        RecyclerView recyclerView = eventFragment.findViewById(R.id.rvEvent);

//        MyEventAdapter myEventAdapter = new MyEventAdapter(eventDatabase);
        MyEventAdapter myEventAdapter = new MyEventAdapter();
        recyclerView.setAdapter(myEventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new appDatabaseHelper();
        // initialise ViewModel
        appViewModel mAppViewModel = databaseHelper.initViewModel(this);
        databaseHelper.subscribeEventToLiveData(getViewLifecycleOwner(), mAppViewModel, myEventAdapter);

        return eventFragment;
    }
}