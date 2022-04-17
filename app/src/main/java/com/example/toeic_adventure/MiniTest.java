package com.example.toeic_adventure;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiniTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiniTest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MiniTest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiniTest.
     */
    // TODO: Rename and change types and number of parameters
    public static MiniTest newInstance(String param1, String param2) {
        MiniTest fragment = new MiniTest();
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
        View rootView = inflater.inflate(R.layout.fragment_mini_test, container, false);
        List<MiniTestAttributes> image_details = getListData();


        final ListView listView = rootView.findViewById(R.id.listViewMiniTest);
        listView.setAdapter(new MiniTestAdapter(getContext(), image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                MiniTestAttributes niniTestAttributes = (MiniTestAttributes) o;
                Toast.makeText(getContext(), "Selected :" + " " + niniTestAttributes, Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }

    private List<MiniTestAttributes> getListData() {
        List<MiniTestAttributes> list = new ArrayList<MiniTestAttributes>();
        MiniTestAttributes exam1 = new MiniTestAttributes("Tạo bài thi ngẫu nhiên", "Bạn chưa học bài này");
        MiniTestAttributes exam2 = new MiniTestAttributes("Mini Test 1", "Bạn chưa học bài này");
        MiniTestAttributes exam3 = new MiniTestAttributes("Mini Test 2", "Bạn chưa học bài này");
        MiniTestAttributes exam4 = new MiniTestAttributes("Mini Test 3", "Bạn chưa học bài này");
        MiniTestAttributes exam5 = new MiniTestAttributes("Mini Test 4", "Bạn chưa học bài này");
        MiniTestAttributes exam6 = new MiniTestAttributes("Mini Test 5", "Bạn chưa học bài này");
        MiniTestAttributes exam7 = new MiniTestAttributes("Mini Test 6", "Bạn chưa học bài này");
        MiniTestAttributes exam8 = new MiniTestAttributes("Mini Test 7", "Bạn chưa học bài này");
        MiniTestAttributes exam9 = new MiniTestAttributes("Mini Test 8", "Bạn chưa học bài này");
        MiniTestAttributes exam10 = new MiniTestAttributes("Mini Test 9", "Bạn chưa học bài này");
        MiniTestAttributes exam11 = new MiniTestAttributes("Mini Test 10", "Bạn chưa học bài này");
        MiniTestAttributes exam12 = new MiniTestAttributes("Mini Test 11", "Bạn chưa học bài này");
        MiniTestAttributes exam13 = new MiniTestAttributes("Mini Test 12", "Bạn chưa học bài này");


        list.add(exam1);
        list.add(exam2);
        list.add(exam3);
        list.add(exam4);
        list.add(exam5);
        list.add(exam6);
        list.add(exam7);
        list.add(exam9);
        list.add(exam10);
        list.add(exam11);
        list.add(exam12);
        list.add(exam13);


        return list;
    }
}