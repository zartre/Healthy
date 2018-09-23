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
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class WeightFragment extends Fragment {
    ArrayList<WeightRecord> weightRecords = new ArrayList<>();
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weightRecords.clear();
        final ListView _weightList = getView().findViewById(R.id.weight_list);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firestore.collection("myfitness")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("weight")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                WeightRecord tempWeight = new WeightRecord();
                                tempWeight.setDate(doc.get("date").toString());
                                tempWeight.setWeight(doc.get("weight").toString());
                                weightRecords.add(tempWeight);
                            }
                            WeightAdapter weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_card, weightRecords);
                            _weightList.setAdapter(weightAdapter);
                        }
                    }
                });

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
