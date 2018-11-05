package com.zartre.app.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SleepFormFragment extends Fragment {
    private EditText _date;
    private EditText _sleepStart;
    private EditText _sleepEnd;
    private Button addBtn;
    private TextView title;
    private SleepDB db;
    private String oldDate, oldSleepStart, oldSleepEnd, rowId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SLEEPFORM", "SleepForm onCreateView");
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("SLEEPFORM", "Enter SleepForm");
        final Bundle sleepBundle = getArguments();

        db = new SleepDB(getContext());
        _date = getView().findViewById(R.id.sleep_form_input_date);
        _sleepStart = getView().findViewById(R.id.sleep_form_input_sleep_start);
        _sleepEnd = getView().findViewById(R.id.sleep_form_input_sleep_end);
        addBtn = getView().findViewById(R.id.sleep_form_btn_add);
        title = getView().findViewById(R.id.sleep_form_title);

        if (sleepBundle != null) {
            oldDate = sleepBundle.getString("date");
            oldSleepStart = sleepBundle.getString("sleepStart");
            oldSleepEnd = sleepBundle.getString("sleepEnd");
             rowId = Integer.toString(sleepBundle.getInt("id"));
            Log.d("SLEEPFORM", "Bundle with rowId " + rowId);
            _date.setText(oldDate);
            _sleepStart.setText(oldSleepStart);
            _sleepEnd.setText(oldSleepEnd);
            addBtn.setText("Update");
            title.setText("Update sleep time");
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = _date.getText().toString();
                String sleepStart = _sleepStart.getText().toString();
                String sleepEnd = _sleepEnd.getText().toString();
                if (sleepBundle == null) {
                    Log.d("SLEEPFORM", "Inserting: " + date + " " + sleepStart + " " + sleepEnd);
                    db.createRecord(date, sleepStart, sleepEnd);
                    Log.d("SLEEPFORM", "Inserted");
                } else {
                    Log.d("SLEEPFORM", "Updating: " + rowId + " " + date + " " + sleepStart + " " + sleepEnd);
                    db.updateRecord(rowId + 1, date, sleepStart, sleepEnd);
                    Log.d("SLEEPFORM", "Updated");
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .commit();
            }
        });
    }
}
