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

public class WeightAdapter extends ArrayAdapter<WeightRecord> {

    List<WeightRecord> weightRecordList = new ArrayList<WeightRecord>();
    Context context;

    public WeightAdapter(Context context, int resource, List<WeightRecord> weights) {
        super(context, resource, weights);
        this.weightRecordList = weights;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View weightCard = LayoutInflater.from(context)
                .inflate(R.layout.fragment_weight_card, parent, false);
        TextView date = weightCard.findViewById(R.id.weight_card_date);
        TextView weight = weightCard.findViewById(R.id.weight_card_weight);

        WeightRecord row = weightRecordList.get(position);
        date.setText(row.getDate());
        weight.setText(row.getWeight());

        return weightCard;
    }
}
