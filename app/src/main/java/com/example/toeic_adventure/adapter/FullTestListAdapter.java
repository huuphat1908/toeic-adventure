package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.model.SkillTestList;

import java.util.List;

public class FullTestListAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<com.example.toeic_adventure.model.FullTestList> FullTestList;

    public FullTestListAdapter(Context context, int layout, List<com.example.toeic_adventure.model.FullTestList> fullTestList) {
        MyContext = context;
        MyLayout = layout;
        FullTestList = fullTestList;
    }

    @Override
    public int getCount() {
        return FullTestList.size();
    }

    @Override
    public Object getItem(int i) {
        return FullTestList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Init view from layout
        LayoutInflater inflater = (LayoutInflater) MyContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(MyLayout, null);

        // Set test information to layout
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        LinearLayout llListening = (LinearLayout) view.findViewById(R.id.llListening);
        LinearLayout llReading = (LinearLayout) view.findViewById(R.id.llReading);
        TextView tvListeningScore = (TextView) view.findViewById(R.id.tvListeningScore);
        TextView tvReadingScore = (TextView) view.findViewById(R.id.tvReadingScore);

        int number = i + 1;
        tvName.setText("Đề thi số "+ number);
        int score = FullTestList.get(i).score;
        String status = score < 0 ? "Bạn chưa học bài này" : "Điểm: " + score + "/990";
        tvStatus.setText(status);
        ivIcon.setImageResource(score < 0 ? R.drawable.question : R.drawable.skill_test_secondary_icon);
        if (score < 0) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0
            );
            llListening.setLayoutParams(param);
            llReading.setLayoutParams(param);
        } else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            param.setMargins(0, 18, 0, 0);
            llListening.setLayoutParams(param);
            llReading.setLayoutParams(param);
            tvListeningScore.setText("Listening: " + String.valueOf(FullTestList.get(i).listeningScore));
            tvReadingScore.setText("Reading: " + String.valueOf(FullTestList.get(i).readingScore));
        }

        return view;
    }
}
