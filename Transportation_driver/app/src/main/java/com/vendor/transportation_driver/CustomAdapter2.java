package com.vendor.transportation_driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dell on 2/4/2016.
 */
public class CustomAdapter2 extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();
    public CustomAdapter2(Context context, ArrayList<HashMap<String, String>> results) {
        this.context = context;
        this.results = results;

    }
    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.bus_row_list,parent,false);
            viewHolder.busNo = (TextView)convertView.findViewById(R.id.tvSchoolName);
           // viewHolder.itemname = (TextView)convertView.findViewById(R.id.tvItemName);
            //viewHolder.address = (TextView)convertView.findViewById(R.id.textAddress);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.busNo.setText("" + results.get(position).get("busNo"));
      //  viewHolder.itemname.setText("Item Name : "+results.get(position).get("item_name"));
       // viewHolder.address.setText("Item Price : " + results.get(position).get("item_price"));
        viewHolder.image.setImageResource(R.drawable.right_arrow123);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    public static class ViewHolder {
       // public TextView address;
        public TextView busNo;
        public ImageView image;
    }
    public void updateResults(ArrayList<HashMap<String, String>> results) {

        this.results = results;
        notifyDataSetChanged();
    }
}
