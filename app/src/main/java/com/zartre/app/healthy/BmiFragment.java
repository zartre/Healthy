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
import android.widget.Toast;

public class BmiFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SCREEN", "BmiFragment onCreateView");
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("SCREEN", "BmiFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        final EditText _height = getView().findViewById(R.id.bmi_input_height);
        final EditText _weight = getView().findViewById(R.id.bmi_input_weight);
        final TextView _bmi = getView().findViewById(R.id.bmi_txt_calculated_bmi);
        final Button _calcBtn = getView().findViewById(R.id.bmi_btn_calculate);

        _calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_height.getText().toString().isEmpty() || _weight.getText().toString().isEmpty()) {
                    Log.d("BMI", "Fields height and weight are empty");
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Double heightInt = Double.parseDouble(_height.getText().toString()) / 100;
                    Double weightInt = Double.parseDouble(_weight.getText().toString());
                    Double bmi = weightInt / (heightInt * heightInt);

                    Log.d("BMI", "BMI is " + bmi);
                    _bmi.setVisibility(View.VISIBLE);
                    _bmi.setText(String.format("%.2f", bmi));
                }
            }
        });
    }
}
