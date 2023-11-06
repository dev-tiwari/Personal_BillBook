package com.example.personalbillbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.personalbillbook.User_Helper_Classes.User;
import com.example.personalbillbook.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        binding.submit.setOnClickListener(view -> {
            String email, pass, name, phoneNumber, confirmPass;

            name = Objects.requireNonNull(binding.fullName.getEditText()).getText().toString().trim();
            email = Objects.requireNonNull(binding.emailAddress.getEditText()).getText().toString().trim();
            phoneNumber = Objects.requireNonNull(binding.phoneNumber.getEditText()).getText().toString().trim();
            pass = Objects.requireNonNull(binding.password.getEditText()).getText().toString().trim();
            confirmPass = Objects.requireNonNull(binding.confirmPassword.getEditText()).getText().toString().trim();

            if (name.isEmpty()) {
                binding.fullName.setError("This field cannot be empty.");
            } else if (email.isEmpty()) {
                binding.emailAddress.setError("This field cannot be empty.");
            } else if (phoneNumber.isEmpty()) {
                binding.phoneNumber.setError("This field cannot be empty.");
            } else if (pass.isEmpty() || pass.length() < 6) {
                binding.password.setError("Password Length Should Exceed 6 Characters!");
            } else if (confirmPass.isEmpty() || confirmPass.length() < 6 ) {
                binding.confirmPassword.setError("Password Length Should Exceed 6 Characters!");
            } else if (!confirmPass.equals(pass)) {
                binding.confirmPassword.setError("Password does not Match!");
            } else {
                final User user = new User(name, email, phoneNumber);
                binding.progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();
                        database
                                .collection("users")
                                .document(uid)
                                .set(user)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        i.putExtra("name", name);
                                        i.putExtra("email", email);
                                        i.putExtra("phoneNumber", phoneNumber);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task1.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}