package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.model.Vocabulary;

import java.util.List;

public class VocabularyAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<Vocabulary> VocabularyList;

    public VocabularyAdapter(Context context, int layout, List<Vocabulary> vocabularyList) {
        MyContext = context;
        MyLayout = layout;
        VocabularyList = vocabularyList;
    }

    @Override
    public int getCount() {
        return VocabularyList.size();
    }

    @Override
    public Object getItem(int i) {
        return VocabularyList.get(i);
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
        testName.setText(VocabularyList.get(i).Name);

        TextView testDesc = (TextView) view.findViewById(R.id.textViewDesc);
        testDesc.setText(VocabularyList.get(i).Desc);

        TextView no = (TextView) view.findViewById(R.id.textViewNo);
        no.setText(Integer.toString(VocabularyList.get(i).No));

        return view;
    }
}
