package com.example.toeic_adventure.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.toeic_adventure.MiniTestAttributes;
import com.example.toeic_adventure.R;

import java.util.List;

public class MiniTestAdapter extends BaseAdapter {

    private List<MiniTestAttributes> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MiniTestAdapter(Context aContext,  List<MiniTestAttributes> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MiniTestAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.mini_test_item, null);
            holder = new MiniTestAdapter.ViewHolder();
            holder.nameMiniTestView = (TextView) convertView.findViewById(R.id.textView_NameTest);
            holder.stateMiniTestView = (TextView) convertView.findViewById(R.id.textView_StateText);
            convertView.setTag(holder);
        } else {
            holder = (MiniTestAdapter.ViewHolder) convertView.getTag();
        }

        MiniTestAttributes miniTestAttributes = this.listData.get(position);
        holder.nameMiniTestView.setText(miniTestAttributes.getNameMiniTest());
        holder.stateMiniTestView.setText(miniTestAttributes.getStateMiniTest());

//        int imageId = this.getMipmapResIdByName(MiniTestAttributes.getStateMiniTest());
//
//        holder.nameMiniTestView.setImageResource(imageId);

        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    static class ViewHolder {
        TextView nameMiniTestView;
        TextView stateMiniTestView;
    }
}
