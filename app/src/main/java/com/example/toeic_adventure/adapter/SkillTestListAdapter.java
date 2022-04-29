package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.model.SkillTestItem;

import java.util.List;

public class SkillTestListAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<SkillTestItem> SkillTestList;

    public SkillTestListAdapter(Context context, int layout, List<SkillTestItem> skillTestList) {
        MyContext = context;
        MyLayout = layout;
        SkillTestList = skillTestList;
    }

    @Override
    public int getCount() {
        return SkillTestList.size();
    }

    @Override
    public Object getItem(int i) {
        return SkillTestList.get(i);
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
        tvName.setText("Tệp câu hỏi số "+ i);

        TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        int score = SkillTestList.get(i).getScore();
        String status = score == 0 ? "Bạn chưa học bài này" : "Điểm: " + score + "/10";
        tvStatus.setText(status);

        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ivIcon.setImageResource(score == 0 ? R.drawable.question : R.drawable.ic_menu_skill_test);

        return view;
    }
}
