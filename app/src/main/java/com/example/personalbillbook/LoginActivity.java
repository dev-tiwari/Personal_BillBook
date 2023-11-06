package com.example.personalbillbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalbillbook.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();

        setContentView(binding.getRoot());

        binding.login.setOnClickListener(v -> {

            String email = Objects.requireNonNull(binding.email.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(binding.password.getEditText()).getText().toString();

            if (email.isEmpty()) {
                binding.email.setError("This field cannot be empty.");
            } else if (password.isEmpty() || password.length() < 6) {
                binding.password.setError("Password Length Should Exceed 6 Characters!");
            } else {
                binding.progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.signUp.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
            finish();
        });

        binding.forgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(i);
            finish();
        });

    }

}