package com.zartre.app.healthy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View sleepCard = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_card, parent, false);
        TextView date = sleepCard.findViewById(R.id.sleep_card_date);
        TextView period = sleepCard.findViewById(R.id.sleep_card_period);
        TextView hours = sleepCard.findViewById(R.id.sleep_card_hours);

        final SleepRecord row = sleepRecordList.get(position);
        date.setText(row.getDate());
        period.setText(row.getSleepStart() + "-" + row.getSleepEnd());

        sleepCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle sleepBundle = new Bundle();
                // is this the correct way to get the actual row id?
                sleepBundle.putInt("id", row.getRowId());
                sleepBundle.putString("date", row.getDate());
                sleepBundle.putString("sleepStart", row.getSleepStart());
                sleepBundle.putString("sleepEnd", row.getSleepEnd());

                Fragment sleepFormFragment = new SleepFormFragment();
                sleepFormFragment.setArguments(sleepBundle);
                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepFormFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return sleepCard;
    }
}
