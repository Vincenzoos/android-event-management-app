package com.fit2081.viettran_33810672_fit2081_a2.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fit2081.viettran_33810672_fit2081_a1.R;
import com.fit2081.viettran_33810672_fit2081_a2.appViewModel;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.CategoryDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.database.appDatabaseHelper;
import com.fit2081.viettran_33810672_fit2081_a2.model.entity.CategoryEntity;
import com.fit2081.viettran_33810672_fit2081_a2.view.adapter.CategoryAdapter;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListCategory extends Fragment {
    Gson gson;
    List<CategoryEntity> cateDatabase;
    private CategoryDatabaseHelper categoryDatabaseHelper;

    private appViewModel mAppViewModel;
    private appDatabaseHelper databaseHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
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
        View cateFragment = inflater.inflate(R.layout.fragment_list_category, container, false);

//        categoryDatabaseHelper = new CategoryDatabaseHelper(cateFragment.getContext());
//        cateDatabase = categoryDatabaseHelper.getCategories();

        gson = new Gson();

//        Log.e("cateDb", gson.toJson(cateDatabase));
        RecyclerView recyclerView = cateFragment.findViewById(R.id.rvCategory);

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new appDatabaseHelper();
        // initialise ViewModel
        mAppViewModel = databaseHelper.initViewModel(this);
        // subscribe to LiveData of type ArrayList<>,
        // any changes detected in the database will be notified to this fragment
//        mAppViewModel.getAllCate().observe(getViewLifecycleOwner(), newData -> {
//                // cast List<Item> to ArrayList<>
//            myCategoryAdapter.setCategory((ArrayList<CategoryEntity>) newData);
//            myCategoryAdapter.notifyDataSetChanged();
//        });

        databaseHelper.subscribeCateToLiveData(getViewLifecycleOwner(), mAppViewModel, categoryAdapter);

        return cateFragment;
    }
}