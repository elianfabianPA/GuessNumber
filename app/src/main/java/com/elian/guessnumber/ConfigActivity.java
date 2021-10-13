package com.elian.guessnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.elian.guessnumber.databinding.ActivityConfigBinding;

/**
 * Esta Activity se encarga de de recibir el nombre de usuario y el número de
 * intentos en el que se tratará de adivinar el número.
 */
public class ConfigActivity extends AppCompatActivity
{
    private ActivityConfigBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Botón Play que nos envía a la siguiente Activity
        binding.btnPlay.setOnClickListener(view ->
        {
            // Campos Usuario e Intentos
            String user = String.valueOf(binding.etUser.getText());
            int attempts = 0;

            // Se comprueba que los campos están llenos
            if (user.length() > 0 && binding.etAttempts.getText().length() > 0)
            {
                // Se inicializa aquí para evitar errores al hacer un parseInt de una cadena vacía
                attempts = Integer.parseInt(binding.etAttempts.getText().toString());

                // Si el número de intentos es menor a 1 entonces no se ejectuará la siguiente Activity
                if (attempts < 1)
                {
                    Toast.makeText(this, R.string.toastNumberLessThan1, Toast.LENGTH_SHORT).show();
                    return;
                }

                //region Enviar Intent

                // 1. Crear objeto Bundle
                Bundle bundle = new Bundle();

                // 2. Añadir contenido al Bundle
                bundle.putString("user", user);
                bundle.putInt("attempts", attempts);

                // 3. Enviar Intent con el bundle
                Intent intent = new Intent(this, PlayActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

                //endregion
            }
            // Se envía un mensaje al usario si alguno de los campos está vacío
            else Toast.makeText(this, R.string.toastEmptyFields, Toast.LENGTH_LONG).show();
        });
    }
}