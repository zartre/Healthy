package com.zartre.app.healthy;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SleepFragment extends Fragment {
    private SleepDB db;
    private ArrayList<SleepRecord> sleepRecords = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SLEEP", "Sleep onCreateView");
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sleepRecords.clear();
        Log.d("SLEEP", "Enter Sleep");

        db = new SleepDB(getContext());
        Cursor records = db.getRecords();
        /*if (records != null) {
            // this is supposed to add the first row of the query to sleepRecords
            SleepRecord s = new SleepRecord(records.getPosition(), records.getString(0), records.getString(1), records.getString(2));
            sleepRecords.add(s);
        }*/
        while (records.moveToNext()) {
            // because this one skips the first row
            SleepRecord s = new SleepRecord(records.getPosition(), records.getString(0), records.getString(1), records.getString(2));
            sleepRecords.add(s);
        }
        records.close();

        final ListView sleepList = getView().findViewById(R.id.sleep_list);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_card, sleepRecords);
        sleepList.setAdapter(sleepAdapter);

        final Button addSleepBtn = getView().findViewById(R.id.sleep_btn_add);
        addSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
