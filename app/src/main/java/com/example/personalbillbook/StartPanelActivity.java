package com.example.personalbillbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.personalbillbook.databinding.ActivityStartPanelBinding;

public class StartPanelActivity extends AppCompatActivity {

    ActivityStartPanelBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginStart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.registerStart.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}