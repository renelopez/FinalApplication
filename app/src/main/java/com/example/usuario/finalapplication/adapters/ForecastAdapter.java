package com.example.usuario.finalapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.usuario.finalapplication.R;
import com.example.usuario.finalapplication.model.*;

/**
 * Created by Usuario on 18/10/2014.
 */
public class ForecastAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    public Forecast[] forecasts;

    public ForecastAdapter(Context context,Forecast[] forecasts){
        inflater = LayoutInflater.from(context);
        this.forecasts=forecasts;
    }

    private void initUI(ViewHolder holder,View convertView) {
        holder.text_code=(TextView) convertView.findViewById(R.id.text_code);
        holder.text_date=(TextView) convertView.findViewById(R.id.text_date);
        holder.text_day=(TextView) convertView.findViewById(R.id.text_day);
        holder.text_high=(TextView) convertView.findViewById(R.id.text_high);
        holder.text_low=(TextView) convertView.findViewById(R.id.text_low);
        holder.text_cost=(TextView) convertView.findViewById(R.id.text_cost);
    }


    @Override
    public int getCount() {
        return forecasts.length;
    }

    @Override
    public Object getItem(int position) {
        return forecasts[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.forecast_drawer, null);
            holder = new ViewHolder();
            initUI(holder,convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        setText(holder, position);
        return convertView;
    }

    private void setText(ViewHolder holder, int position) {
        Forecast forecast=forecasts[position];
        holder.text_code.setText(forecast.getCode());
        holder.text_date.setText(forecast.getDate());
        holder.text_day.setText(forecast.getDay());
        holder.text_high.setText(forecast.getHigh());
        holder.text_low.setText(forecast.getLow());
        holder.text_cost.setText(forecast.getCost());
    }

    private class ViewHolder{
        TextView text_code;
        TextView text_date;
        TextView text_day;
        TextView text_high;
        TextView text_low;
        TextView text_cost;
    }
}
