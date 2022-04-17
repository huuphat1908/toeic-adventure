package com.example.toeic_adventure;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FullTestDetailAdapter  extends BaseAdapter {
    private List<FullTestDetailAtrributes> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public FullTestDetailAdapter(Context aContext,  List<FullTestDetailAtrributes> listData) {
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
        FullTestDetailAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.full_test_detail_item, null);
            holder = new FullTestDetailAdapter.ViewHolder();
            holder.nameMiniTestView = (TextView) convertView.findViewById(R.id.textView_NameTest);
            holder.stateMiniTestView = (TextView) convertView.findViewById(R.id.textView_StateText);
            convertView.setTag(holder);
        } else {
            holder = (FullTestDetailAdapter.ViewHolder) convertView.getTag();
        }

        FullTestDetailAtrributes FullTestDetailAtrributes = this.listData.get(position);
        holder.nameMiniTestView.setText(FullTestDetailAtrributes.getNameMiniTest());
        holder.stateMiniTestView.setText(FullTestDetailAtrributes.getStateMiniTest());

//        int imageId = this.getMipmapResIdByName(FullTestDetailAtrributes.getStateMiniTest());
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
