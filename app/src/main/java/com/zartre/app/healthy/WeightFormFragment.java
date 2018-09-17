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
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFormFragment extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Button _addBtn = getView().findViewById(R.id.weight_form_btn_add);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _date = getView().findViewById(R.id.weight_form_input_date);
                EditText _weight = getView().findViewById(R.id.weight_form_input_weight);
                String _dateStr = _date.getText().toString();
                String _weightStr = _date.getText().toString();

                WeightRecord _record = new WeightRecord();
                _record.setDate(_dateStr);
                _record.setWeight(_weightStr);

                firestore.collection("myfitness")
                        .document(auth.getCurrentUser().getUid())
                        .collection("weight")
                        .document(_dateStr)
                        .set(_record)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                getFragmentManager()
                                        .beginTransaction()
                                        // TODO: Change WeightFormFragment to WeightFragment
                                        .replace(R.id.main_view, new WeightFormFragment())
                                        .commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Couldn't log your weight", Toast.LENGTH_SHORT).show();
                                Log.d("WEIGHT", e.getMessage());
                            }
                        });
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }
}
