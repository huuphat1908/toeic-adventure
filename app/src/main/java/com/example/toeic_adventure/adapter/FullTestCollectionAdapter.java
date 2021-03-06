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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class FullTestCollectionAdapter extends BaseAdapter {
    Context MyContext;
    int MyLayout;
    List<FullTestCollection> FullTestCollectionList;
    ImageLoaderConfiguration config;
    ImageLoader imageLoader;

    public FullTestCollectionAdapter(Context context, int layout, List<FullTestCollection> fullTestCollectionList) {
        MyContext = context;
        MyLayout = layout;
        FullTestCollectionList = fullTestCollectionList;
        config = new ImageLoaderConfiguration.Builder(MyContext.getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return FullTestCollectionList.size();
    }

    @Override
    public Object getItem(int i) {
        return FullTestCollectionList.get(i);
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

        TextView tvFullTestCollection = (TextView) view.findViewById(R.id.tvFullTestCollection);
        tvFullTestCollection.setText(FullTestCollectionList.get(i).name);

        ImageView ivFullTestCollection = (ImageView) view.findViewById(R.id.ivFullTestCollection);
        imageLoader.displayImage(Url.baseUrl + FullTestCollectionList.get(i).imageUrl, ivFullTestCollection);

        return view;
    }
}
