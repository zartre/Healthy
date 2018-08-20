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

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SCREEN", "RegisterFragment onCreateView");
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("SCREEN", "RegisterFragment onCreateView");
        super.onActivityCreated(savedInstanceState);

        Button _regBtn = getView().findViewById(R.id.register_btn_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText _username = getView().findViewById(R.id.register_input_username);
                EditText _realName = getView().findViewById(R.id.register_input_realname);
                EditText _age = getView().findViewById(R.id.register_input_age);
                EditText _password = getView().findViewById(R.id.register_input_password);

                String _usernameStr = _username.getText().toString();
                String _realNameStr = _realName.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_usernameStr.isEmpty() || _realNameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("REGISTER", "Field name is empty");
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                            .show();
                } else if (_usernameStr.equalsIgnoreCase("admin")) {
                    Log.d("REGISTER", "Username already exists");
                    Toast.makeText(getActivity(), "Username exists", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Log.d("REGISTER", "Go to BMI");
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .commit();
                }
            }
        });
    }
}
