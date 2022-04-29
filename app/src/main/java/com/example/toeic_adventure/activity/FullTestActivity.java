package com.example.toeic_adventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.toeic_adventure.FullTestDetail;
import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.CustomListAdapter;
import com.example.toeic_adventure.model.Country;

import java.util.ArrayList;
import java.util.List;

public class FullTestActivity extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FullTestActivity() {
        // Required empty public constructor
    }

    public static FullTestActivity newInstance(String param1, String param2) {
        FullTestActivity fragment = new FullTestActivity();
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
        View rootView = inflater.inflate(R.layout.fragment_full_test, container, false);
        List<Country> image_details = getListData();
        final ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(getContext(), image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Country country = (Country) o;
                Intent intent = new Intent(v.getContext(), FullTestDetail.class);
                intent.putExtra("nameFullTestDetail", ((Country) o).getCountryName());
                startActivity(intent);
//                Toast.makeText(getContext(), "Selected :" + " " + country, Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    private List<Country> getListData() {
        List<Country> list = new ArrayList<Country>();
        Country exam1 = new Country("Exam 20222", "part1_thumbnail", 98000000);
        Country exam2 = new Country("Exam 2021", "part2_thumbnail", 320000000);
        Country exam3 = new Country("Big Step toeic", "part3_thumbnail", 142000000);
        Country exam4 = new Country("Hacker New Toeic", "part4_thumbnail", 142000000);
        Country exam5 = new Country("Big Step toeic", "part5_thumbnail", 142000000);
        Country exam6 = new Country("Hacker New Toeic", "part6_thumbnail", 142000000);
        Country exam7 = new Country("Big Step toeic", "part7_thumbnail", 142000000);
        Country exam8 = new Country("Hacker New Toeic", "part1_thumbnail", 142000000);


        list.add(exam1);
        list.add(exam2);
        list.add(exam3);
        list.add(exam4);
        list.add(exam5);
        list.add(exam6);
        list.add(exam7);
        list.add(exam8);

        return list;
    }
}