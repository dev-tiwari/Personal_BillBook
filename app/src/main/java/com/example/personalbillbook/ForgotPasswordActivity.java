package com.example.personalbillbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.personalbillbook.databinding.ActivityForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    int reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        reset = getIntent().getIntExtra("reset", 0);

        setContentView(binding.getRoot());

        if (reset == 1) {
            binding.signIn.setVisibility(View.INVISIBLE);
            binding.already.setVisibility(View.INVISIBLE);
            binding.textView5.setVisibility(View.INVISIBLE);
            binding.textView22.setVisibility(View.VISIBLE);
        }

        binding.already.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        binding.signIn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        binding.forgotPasswordButton.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String email;
            email = Objects.requireNonNull(binding.emailForgot.getEditText()).getText().toString().trim();

            if (email.isEmpty()) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.emailForgot.setError("This field cannot be blank.");
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Password Reset email has been sent.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}