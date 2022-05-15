package com.example.toeic_adventure.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.adapter.CustomListAdapter;
import com.example.toeic_adventure.model.FullTest;
import com.example.toeic_adventure.model.FullTest;

import java.util.ArrayList;
import java.util.List;

public class GrammarActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GrammarActivity() {
        // Required empty public constructor
    }

    public static GrammarActivity newInstance(String param1, String param2) {
        GrammarActivity fragment = new GrammarActivity();
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
        View rootView = inflater.inflate(R.layout.fragment_grammar, container, false);
        List<FullTest> image_details = getListData();
        final ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(getContext(), image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                FullTest fullTest = (FullTest) o;
                Toast.makeText(getContext(), "Selected :" + " " + fullTest, Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    private List<FullTest> getListData() {
        List<FullTest> list = new ArrayList<FullTest>();
        FullTest exam1 = new FullTest("Ngữ pháp cơ bản", "part1_thumbnail", 98000000);
        FullTest exam2 = new FullTest("Mẹo thi TOEIC Part 1", "part2_thumbnail", 320000000);
        FullTest exam3 = new FullTest("Mẹo thi TOEIC Part 2", "part3_thumbnail", 142000000);
        FullTest exam4 = new FullTest("Mẹo thi TOEIC Part 3", "part4_thumbnail", 142000000);
        FullTest exam5 = new FullTest("Mẹo thi TOEIC Part 4", "part5_thumbnail", 142000000);
        FullTest exam6 = new FullTest("Mẹo thi TOEIC Part 5", "part6_thumbnail", 142000000);
        FullTest exam7 = new FullTest("Mẹo thi TOEIC Part 6", "part7_thumbnail", 142000000);
        FullTest exam8 = new FullTest("Mẹo thi TOEIC Part 7", "part1_thumbnail", 142000000);


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