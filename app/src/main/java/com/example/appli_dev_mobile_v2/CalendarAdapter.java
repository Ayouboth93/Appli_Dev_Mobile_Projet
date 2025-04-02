package com.example.appli_dev_mobile_v2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<String> days;
    private HashMap<String, String> colorMap;
    private Calendar currentCalendar; // Ajout de la variable

    public CalendarAdapter(Context context, List<String> days, HashMap<String, String> colorMap, Calendar calendar) {
        this.context = context;
        this.days = days;
        this.colorMap = colorMap;
        this.currentCalendar = calendar;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_day, parent, false);

        }

        TextView textView = convertView.findViewById(R.id.textViewDay);

        String day = days.get(position);
        textView.setText(day.isEmpty() ? "" : day);

        // Vérifier si c'est un jour valide
        if (!day.isEmpty()) {
            String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d",
                    currentCalendar.get(Calendar.YEAR),
                    currentCalendar.get(Calendar.MONTH) + 1,
                    Integer.parseInt(day));

            if (colorMap.containsKey(formattedDate)) {
                String color = colorMap.get(formattedDate);
                switch (color) {
                    case "green":
                        textView.setBackgroundColor(Color.GREEN);
                        break;
                    case "orange":
                        textView.setBackgroundColor(Color.rgb(255, 165, 0));
                        break;
                    case "red":
                        textView.setBackgroundColor(Color.RED);
                        break;
                    default:
                        textView.setBackgroundColor(Color.WHITE);
                        break;
                }
            } else {
                textView.setBackgroundColor(Color.WHITE);
            }
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        return convertView;
    }
}
