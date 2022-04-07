package com.example.toeic_adventure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullTest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FullTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FullTest.
     */
    // TODO: Rename and change types and number of parameters
    public static FullTest newInstance(String param1, String param2) {
        FullTest fragment = new FullTest();
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
                Toast.makeText(getContext(), "Selected :" + " " + country, Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    private List<Country> getListData() {
        List<Country> list = new ArrayList<Country>();
        Country exam1 = new Country("Exam 20222", "us", 98000000);
        Country exam2 = new Country("Exam 2021", "us", 320000000);
        Country exam3 = new Country("Big Step toeic", "us", 142000000);
        Country exam4 = new Country("Hacker New Toeic", "us", 142000000);
        Country exam5 = new Country("Big Step toeic", "us", 142000000);
        Country exam6 = new Country("Hacker New Toeic", "us", 142000000);
        Country exam7 = new Country("Big Step toeic", "us", 142000000);
        Country exam8 = new Country("Hacker New Toeic", "us", 142000000);


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