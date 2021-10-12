package com.elian.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.elian.guessnumber.databinding.ActivityEndPlayBinding;

public class EndPlayActivity extends AppCompatActivity
{
    ActivityEndPlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityEndPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. Recoger Intent
        Intent intent = getIntent();

        // 2. Recoger el mensaje
       boolean numberMatched = intent.getBooleanExtra("numberMatched", false);
       int attempts = intent.getIntExtra("attempts", 0);
       int actualAttempts = intent.getIntExtra("actualAttempts", 0);
       String user = intent.getStringExtra("user");

        // 3. Actualizamos la vista
        if (numberMatched) binding.tvNumberGuessed.setText(getResources().getString(R.string.tvNumberGuessed_1) + user + getResources().getString(R.string.tvNumberGuessed_2_1));
        else binding.tvNumberGuessed.setText(getResources().getString(R.string.tvNumberGuessed_1) + user + getResources().getString(R.string.tvNumberGuessed_2_2));

        binding.tvActualAttemptsOutOfAttempts.setText(getResources().getString(R.string.tvActualAttemptsOutOfAttempts_1) + (attempts - actualAttempts) + getResources().getString(R.string.tvActualAttemptsOutOfAttempts_2) + attempts + ".");
    }
}