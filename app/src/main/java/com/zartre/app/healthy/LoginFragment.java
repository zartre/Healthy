package com.zartre.app.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        Button _loginBtn = getView().findViewById(R.id.login_btn_login);
        _loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText _email = getView().findViewById(R.id.login_input_email);
                EditText _password = getView().findViewById(R.id.login_input_password);
                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_emailStr.isEmpty() || _passwordStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing username or password", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("LOGIN", "Missing username or password");
                } else {
                    Log.d("LOGIN", "Go to BMI");
                    loginUser(_emailStr, _passwordStr);
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
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void loginUser(String email, String password) {
        final ProgressBar _loginLoading = getView().findViewById(R.id.login_loading);
        final Button _loginBtn = getView().findViewById(R.id.login_btn_login);
        _loginLoading.setVisibility(View.VISIBLE);
        _loginBtn.setVisibility(View.GONE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MenuFragment())
                                .commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        _loginLoading.setVisibility(View.GONE);
                        _loginBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
