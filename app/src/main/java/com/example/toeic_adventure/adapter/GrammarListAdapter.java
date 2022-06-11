package com.example.toeic_adventure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toeic_adventure.R;
import com.example.toeic_adventure.api.Url;
import com.example.toeic_adventure.model.FullTestCollection;
import com.example.toeic_adventure.model.GrammarList;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class GrammarListAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<GrammarList> GrammarList;

    public GrammarListAdapter(Context context, int layout, List<GrammarList> grammarList) {
        MyContext = context;
        MyLayout = layout;
        GrammarList = grammarList;
    }

    @Override
    public int getCount() {
        return GrammarList.size();
    }

    @Override
    public Object getItem(int i) {
        return GrammarList.get(i);
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

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(GrammarList.get(i).name);

        return view;
    }
}
