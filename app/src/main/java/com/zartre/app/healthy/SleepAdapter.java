package com.zartre.app.healthy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<SleepRecord> {

    List<SleepRecord> sleepRecordList = new ArrayList<>();
    Context context;

    public SleepAdapter (Context context, int resource, List<SleepRecord> records) {
        super(context, resource, records);
        this.sleepRecordList = records;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View sleepCard = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_card, parent, false);
        TextView date = sleepCard.findViewById(R.id.sleep_card_date);
        TextView period = sleepCard.findViewById(R.id.sleep_card_period);
        TextView hours = sleepCard.findViewById(R.id.sleep_card_hours);

        SleepRecord row = sleepRecordList.get(position);
        date.setText(row.getDate());
        period.setText(row.getSleepStart() + "-" + row.getSleepEnd());

        return sleepCard;
    }
}
