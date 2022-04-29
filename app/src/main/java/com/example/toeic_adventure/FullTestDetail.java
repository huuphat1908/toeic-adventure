package com.example.toeic_adventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.toeic_adventure.adapter.MiniTestAdapter;

import java.util.ArrayList;
import java.util.List;

//public class FullTestDetail extends AppCompatActivity {

public class FullTestDetail extends AppCompatActivity {
    Toolbar myToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_test_detail);
        myToolbar = findViewById(R.id.full_test_detail_bar_back);
        setActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.back_icon);

        Intent intent = getIntent();
        String title = intent.getStringExtra("nameFullTestDetail");
        myToolbar.setTitle(title);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
//    PlaceholderFragment placeholderFragment = new PlaceholderFragment();
//    placeholderFragment.onCreateView();
    FullTestDetail.PlaceholderFragment placeholderFragment = new FullTestDetail.PlaceholderFragment();


}
