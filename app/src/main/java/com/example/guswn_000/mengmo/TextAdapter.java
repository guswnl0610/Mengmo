package com.example.guswn_000.mengmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by guswn_000 on 2017-06-11.
 */

public class TextAdapter extends BaseAdapter
{
    Context context;
    ArrayList<MyText> datalist = new ArrayList<>();

    public TextAdapter(Context context, ArrayList<MyText> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.txtlist,null);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.txtiv);
        TextView txt = (TextView)convertView.findViewById(R.id.txttitle);
        txt.setText(datalist.get(position).getTitle());
        return convertView;
    }

    Comparator<MyText> dateDsc = new Comparator<MyText>() {
        @Override
        public int compare(MyText o1, MyText o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    };
    public void sortTextDsc()
    {
        Collections.sort(datalist, dateDsc);
        this.notifyDataSetChanged();
    }


}
