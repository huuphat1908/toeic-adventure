package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        int number = i + 1;
        tvName.setText("Đề thi số "+ number);

        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        int score = FullTestList.get(i).score;
        String status = score < 0 ? "Bạn chưa học bài này" : "Điểm: " + score + "/10";
        tvStatus.setText(status);

        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ivIcon.setImageResource(score < 0 ? R.drawable.question : R.drawable.ic_menu_skill_test);

        return view;
    }
}
