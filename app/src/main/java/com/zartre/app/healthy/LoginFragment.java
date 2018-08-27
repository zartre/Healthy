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

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button _loginBtn = getView().findViewById(R.id.login_btn_login);
        _loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText _userId = getView().findViewById(R.id.login_input_username);
                EditText _password = getView().findViewById(R.id.login_input_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing username or password", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("LOGIN", "Missing username or password");
                } else if (_userIdStr.equalsIgnoreCase("admin") && _passwordStr.equalsIgnoreCase("admin")) {
                    Log.d("LOGIN", "Go to BMI");
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .commit();
                }
            }

        });

        TextView _regBtn = getView().findViewById(R.id.text_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOGIN", "Register button clicked.");
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
            }
        });
    }
}
