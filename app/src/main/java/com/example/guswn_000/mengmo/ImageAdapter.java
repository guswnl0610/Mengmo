package com.example.guswn_000.mengmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guswn_000 on 2017-06-11.
 */

public class ImageAdapter extends BaseAdapter
{
    Context context;
    ArrayList<MyImage> datalist = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<MyImage> datalist)
    {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount()
    {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.imglist,null);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);
        TextView txt = (TextView)convertView.findViewById(R.id.imgtitle);
        txt.setText(datalist.get(position).getTitle());

        return convertView;
    }


}
