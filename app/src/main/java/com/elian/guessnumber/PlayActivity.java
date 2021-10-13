package com.elian.guessnumber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.elian.guessnumber.databinding.ActivityPlayBinding;

import java.util.Random;

public class PlayActivity extends AppCompatActivity
{

    ActivityPlayBinding binding;

    String user;
    int attempts = 0;

    int numberToGuess = 0;     // Número a adivinar
    int actualAttempts = 0;    // Número de intentos restantes
    int userNumberToGuess = 0; // Número introducido por el usuario

    boolean numberMatched; // Es verdadero si el jugador ha conseguido acertar el número

    String numberIs_GT_LT; // Contiene el mensaje de si el número es mayor o menor que el introducido

    // Rango del número aleatorio
    final int min_num = 1;
    final int max_num = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //region Obtención de información de ConfigActivity

        // 1. Recoger Intent
        Intent intent = getIntent();

        // 2. Recoger el mensaje
        user = intent.getStringExtra("user");
        attempts = intent.getIntExtra("attempts", 1);
        actualAttempts = attempts;

        //endregion

        // Generamos el número aleatorio entre 1 y 100
        Random ran = new Random();
        numberToGuess = ran.nextInt(max_num + 1) + min_num;


        // Botón Check para comprobar si el número coincide o no con el que hay que adivinar
        binding.btnCheckNumber.setOnClickListener(view ->
        {
            // Si el usuario aún tiene intentos se continúa, si no se pasa a EndPlayActivity
            if (actualAttempts > 0)
            {
                // Si el campo está lleno se prosigue
                if (binding.edUserNumberToGuess.getText().length() != 0)
                {
                    userNumberToGuess = Integer.parseInt(binding.edUserNumberToGuess.getText().toString());

                    //region Si el número es mayor que 100 o menor que 0 no se hace nada
                    if (userNumberToGuess == 0)
                    {
                        Toast.makeText(this, R.string.toastNumberLessThan1, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (userNumberToGuess > max_num)
                    {
                        Toast.makeText(this, R.string.toastNumberGreaterThan100, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //endregion

                    // Se disminuye el número de intentos
                    actualAttempts--;

                    //region Se informa al usuario si el número es mayor, menor que el que tiene que adivinar
                    if (numberToGuess > userNumberToGuess)
                    {
                        numberIs_GT_LT = getResources().getString(R.string.tvInfoNumberIsGreater);
                    }
                    else if (numberToGuess < userNumberToGuess)
                    {
                        numberIs_GT_LT = getResources().getString(R.string.tvInfoNumberIsLess);
                    }
                    else
                    {
                        numberMatched = true; // El jugador ha acetado el número
                        sendIntent();
                    }
                    binding.tvInfo.setText(numberIs_GT_LT);
                    binding.edUserNumberToGuess.setText("");
                    //endregion
                }
                else Toast.makeText(this, R.string.toastEmptyField, Toast.LENGTH_SHORT).show();
            }
            else
            {
                numberMatched = false; // El jugador no ha conseguido acertar el número
                sendIntent();
            }
        });

        //binding.btnCheckNumber.setEnabled(false);
    }

    //region Guardar el valor de tvInfo al rotar el dispositivo
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString("numberIs_GT_LT", numberIs_GT_LT);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        numberIs_GT_LT = savedInstanceState.getString("numberIs_GT_LT");
        binding.tvInfo.setText(numberIs_GT_LT);
    }
    //endregion

    // Envía el intent a EndPlayActivity
    void sendIntent()
    {
        // 1. Añadir contenido al Bundle
        Bundle playBundle = new Bundle();
        playBundle.putBoolean("numberMatched", numberMatched);
        playBundle.putInt("attempts", attempts);
        playBundle.putInt("actualAttempts", actualAttempts);
        playBundle.putString("user", user);

        // 2. Enviar Intent con el bundle
        Intent playIntent = new Intent(this, EndPlayActivity.class);
        playIntent.putExtras(playBundle);

        startActivity(playIntent);
    }
}