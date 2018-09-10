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

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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
        Button _regBtn = getView().findViewById(R.id.register_btn_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText _username = getView().findViewById(R.id.register_input_username);
                final EditText _email = getView().findViewById(R.id.register_input_email);
                final EditText _realName = getView().findViewById(R.id.register_input_realname);
                final EditText _age = getView().findViewById(R.id.register_input_age);
                final EditText _password = getView().findViewById(R.id.register_input_password);

                String _usernameStr = _username.getText().toString();
                String _emailStr = _email.getText().toString();
                String _realNameStr = _realName.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_usernameStr.isEmpty() || _realNameStr.isEmpty() || _ageStr.isEmpty() || _passwordStr.isEmpty()) {
                    Log.d("REGISTER", "Field name is empty");
                    Toast.makeText(getActivity(), "Please fill in all the fields", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    _username.setEnabled(false);
                    _email.setEnabled(false);
                    _realName.setEnabled(false);
                    _age.setEnabled(false);
                    _password.setEnabled(false);
                    _submitProgress.setVisibility(View.VISIBLE);
                    firebaseAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    authResult.getUser().sendEmailVerification()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(
                                                            getActivity(),
                                                            "Registered. Please click the confirmation link sent to your email",
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
                                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    _submitProgress.setVisibility(View.GONE);
                                    _username.setEnabled(true);
                                    _email.setEnabled(true);
                                    _realName.setEnabled(true);
                                    _age.setEnabled(true);
                                    _password.setEnabled(true);
                                }
                            });
                }
            }
        });
    }
}
