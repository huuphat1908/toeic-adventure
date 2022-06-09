package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.activity.VocabularyDetailActivity;
import com.example.toeic_adventure.model.VocabularyDetail;

import java.util.List;

public class VocabularyDetailAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<VocabularyDetail> VocabularyDetailList;

    public VocabularyDetailAdapter(Context context, int layout, List<VocabularyDetail> vocabularyDetailList) {
        MyContext = context;
        MyLayout = layout;
        VocabularyDetailList = vocabularyDetailList;
    }

    @Override
    public int getCount() {
        return VocabularyDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return VocabularyDetailList.get(i);
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
        TextView testName = (TextView) view.findViewById(R.id.textViewNameDetail);
        testName.setText(VocabularyDetailList.get(i).Name);

        TextView testDesc = (TextView) view.findViewById(R.id.textViewDescDetail);
        testDesc.setText(VocabularyDetailList.get(i).Desc);

        TextView testMean = (TextView) view.findViewById(R.id.textViewMean);
        testMean.setText(VocabularyDetailList.get(i).Mean);

        TextView no = (TextView) view.findViewById(R.id.textViewNoDetail);
        no.setText(Integer.toString(VocabularyDetailList.get(i).No));

        return view;
    }
}
