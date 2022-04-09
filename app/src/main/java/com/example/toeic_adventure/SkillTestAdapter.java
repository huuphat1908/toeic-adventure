package com.example.toeic_adventure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SkillTestAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<SkillTest> SkillTestList;

    public SkillTestAdapter(Context context, int layout, List<SkillTest> skillTestList) {
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
        return null;
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
        testName.setText(SkillTestList.get(i).Name);

        TextView testDesc = (TextView) view.findViewById(R.id.textViewDesc);
        testDesc.setText(SkillTestList.get(i).Desc);

        TextView numberOfTest = (TextView) view.findViewById(R.id.textViewNumberOfTests);
        numberOfTest.setText(SkillTestList.get(i).NumberOfTest + " bài");

        String type = SkillTestList.get(i).Type.toString();
        TextView skillType = (TextView) view.findViewById(R.id.textViewSkillType);
        if (TestType.LISTENING.toString().equals(type)) {
            skillType.setText("Bài nghe");
        } else {
            skillType.setText("Bài đọc");
        }

        ImageView testThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        testThumbnail.setImageResource(SkillTestList.get(i).Thumbnail);

        return view;
    }
}