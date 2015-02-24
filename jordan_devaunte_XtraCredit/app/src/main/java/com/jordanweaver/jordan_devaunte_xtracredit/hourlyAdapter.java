package com.jordanweaver.jordan_devaunte_xtracredit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by devaunteledee on 2/23/15.
 */


public class hourlyAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<weatherClass> hourlyWeather;
    private static final int ID_CONSTANT = 0x01000000;

    public hourlyAdapter(Context mContext, ArrayList<weatherClass> hourlyWeather) {
        this.mContext = mContext;
        this.hourlyWeather = hourlyWeather;
    }

    @Override
    public int getCount() {
        if(hourlyWeather != null){
            return hourlyWeather.size();
        }else{
            return 0;
        }


    }

    @Override
    public Object getItem(int position) {
        if (hourlyWeather != null && position < hourlyWeather.size() && position >=0){
            return hourlyWeather.get(position);
        }else{
            Log.e("MYADAPTER", "Get item returned Null bcause of no collection");
            return null;
        }


    }

    @Override
    public long getItemId(int position) {
        if (hourlyWeather != null ){
            return  ID_CONSTANT + position;
        }else {
            Log.e("MYADAPTER","Get ID returned 0 bcause of no collection");
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.hourlylayout,parent,false);
        }

        weatherClass item = (weatherClass) getItem(position);

        Log.e("getView", Integer.toString(position));
        Log.e("getView", item.toString());
        ((TextView)convertView.findViewById(R.id.dayLabel)).setText(item.getTime());
        ((TextView)convertView.findViewById(R.id.conditionLabel)).setText(item.getCondition());
        ((TextView)convertView.findViewById(R.id.weatherLabel)).setText(item.getHigh());
//        ((TextView)convertView.findViewById(R.id.IdTextView)).setText(item.toString());

        return convertView;
    }
    }



