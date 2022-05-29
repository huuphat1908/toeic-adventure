package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.model.SkillTestCollection;

import java.util.List;

public class SkillTestCollectionAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<SkillTestCollection> skillTestCollectionList;

    public SkillTestCollectionAdapter(Context context, int layout, List<SkillTestCollection> skillTestCollectionList) {
        MyContext = context;
        MyLayout = layout;
        this.skillTestCollectionList = skillTestCollectionList;
    }

    @Override
    public int getCount() {
        return skillTestCollectionList.size();
    }

    @Override
    public Object getItem(int i) {
        return skillTestCollectionList.get(i);
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
        TextView testName = (TextView) view.findViewById(R.id.textViewName);
        testName.setText(skillTestCollectionList.get(i).Name);

        TextView testDesc = (TextView) view.findViewById(R.id.textViewDesc);
        testDesc.setText(skillTestCollectionList.get(i).Desc);

        TextView numberOfTest = (TextView) view.findViewById(R.id.textViewNumberOfTests);
        numberOfTest.setText(skillTestCollectionList.get(i).NumberOfTest + " bài");

        String type = skillTestCollectionList.get(i).Type;
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        TextView skillType = (TextView) view.findViewById(R.id.textViewSkillType);
        if (type.equals("Bài nghe")) {
            ivIcon.setImageResource(R.drawable.skill_test_listening_icon);
            skillType.setText("Bài nghe");
        } else {
            ivIcon.setImageResource(R.drawable.ic_menu_skill_test);
            skillType.setText("Bài đọc");
        }

        ImageView testThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        testThumbnail.setImageResource(skillTestCollectionList.get(i).Thumbnail);

        return view;
    }
}
