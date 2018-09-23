package com.zartre.app.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class WeightFragment extends Fragment {
    ArrayList<WeightRecord> weightRecords = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WeightRecord w1 = new WeightRecord();
        w1.setWeight("46");
        w1.setDate("21-09-2018");
        WeightRecord w2 = new WeightRecord();
        w2.setWeight("45");
        w2.setDate("22-09-2018");
        weightRecords.add(w1);
        weightRecords.add(w2);

        ListView _weightList = getView().findViewById(R.id.weight_list);

        WeightAdapter weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_card, weightRecords);
        _weightList.setAdapter(weightAdapter);

        Button _addWeightBtn = getView().findViewById(R.id.weight_btn_add);
        _addWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
