package com.example.toeic_adventure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

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
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            return rootView;
        }
    }
//    PlaceholderFragment placeholderFragment = new PlaceholderFragment();
//    placeholderFragment.onCreateView();
    FullTestDetail.PlaceholderFragment placeholderFragment = new FullTestDetail.PlaceholderFragment();


}
