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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

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

        final ProgressBar _submitProgress = getView().findViewById(R.id.register_loading);
        final Button _regBtn = getView().findViewById(R.id.register_btn_register);
        final EditText _email = getView().findViewById(R.id.register_input_email);
        final EditText _password = getView().findViewById(R.id.register_input_password);
        final EditText _repassword = getView().findViewById(R.id.register_input_repassword);

        firebaseAuth = FirebaseAuth.getInstance();

        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();
                String _repasswordStr = _repassword.getText().toString();

                if (isInputValid(_emailStr, _passwordStr, _repasswordStr)) {
                    _email.setEnabled(false);
                    _password.setEnabled(false);
                    _repassword.setEnabled(false);
                    _submitProgress.setVisibility(View.VISIBLE);

                    registerUser(_emailStr, _passwordStr);
                }
            }
        });
    }

    public void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authResult.getUser().sendEmailVerification()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(
                                                getActivity(),
                                                "Registered. Please click the confirmation link sent to your email.",
                                                Toast.LENGTH_SHORT).show();
                                        getFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.main_view, new LoginFragment())
                                                .commit();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        final EditText _email = getView().findViewById(R.id.register_input_email);
                        final EditText _password = getView().findViewById(R.id.register_input_password);
                        final EditText _repassword = getView().findViewById(R.id.register_input_repassword);
                        final ProgressBar _submitProgress = getView().findViewById(R.id.register_loading);

                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        _submitProgress.setVisibility(View.GONE);
                        _email.setEnabled(true);
                        _password.setEnabled(true);
                        _repassword.setEnabled(true);
                    }
                });
    }

    private Boolean isInputValid(String eml, String pwd1, String pwd2) {
        if (eml.isEmpty() || pwd1.isEmpty() || pwd2.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pwd1.length() < 6) {
            Toast.makeText(getActivity(), "Password must contain at least 6 characters", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (!pwd1.equals(pwd2)) {
            Toast.makeText(getActivity(), "Please make sure you typed the same password", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
}
