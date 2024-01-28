package com.example.puzzle_anibalbenedicto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNombre;
    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nombre_aplicacion);

        mediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        editNombre = findViewById(R.id.editNombre);
        Button btnEmpezar = findViewById(R.id.btnEmpezar);
        Button btnPuntuaciones = findViewById(R.id.btnPuntuaciones);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el nombre ingresado
                String nombre = (editNombre != null) ? editNombre.getText().toString() : "";

                // Verificar si se ingresó un nombre
                if (!nombre.isEmpty()) {
                    // Almacenar el nombre en SharedPreferences (podrías utilizar una base de datos)
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("UsuarioPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombreUsuario", nombre);
                    editor.apply();

                    // Ir al selector de niveles
                    Intent intent = new Intent(MainActivity.this, SelectorNivelesActivity.class);
                    startActivity(intent);
                } else {
                    // Mostrar un mensaje de error si no se ingresó un nombre
                    Toast.makeText(MainActivity.this, "Por favor, ingrese su nombre", Toast.LENGTH_SHORT).show();
                }

                // Iniciar o pausar la música según el estado actual
                toggleMusic();
            }
        });

        btnPuntuaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ir a puntuaciones
                Intent intent = new Intent(MainActivity.this, PuntuacionesMenuActivity.class);
                startActivity(intent);

                // Iniciar o pausar la música según el estado actual
                toggleMusic();
            }
        });
    }

    private void toggleMusic() {
        if (isMusicPlaying) {
            // Pausar la música
            mediaPlayer.pause();
        } else {
            // Iniciar la música
            mediaPlayer.start();
        }

        isMusicPlaying = !isMusicPlaying; // Cambiar el estado
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del MediaPlayer al salir de la actividad
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
