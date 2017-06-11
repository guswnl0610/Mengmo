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
 * Created by guswn_000 on 2017-06-10.
 */

public class RecordAdapter extends BaseAdapter
{
    Context context;
    ArrayList<MyRecord> datalist = new ArrayList<>();

    public RecordAdapter(Context context, ArrayList<MyRecord> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position)
    {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.reclist,null);
        }
        ImageView img = (ImageView)convertView.findViewById(R.id.imageView);
        TextView txt = (TextView)convertView.findViewById(R.id.titletv);
        txt.setText(datalist.get(position).getTitle());

        return convertView;
    }

    Comparator<MyRecord> nameAsc = new Comparator<MyRecord>() {
        @Override
        public int compare(MyRecord o1, MyRecord o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };

    public void sortRecordAsc()
    {
        Collections.sort(datalist, nameAsc);
        this.notifyDataSetChanged();
    }
}
