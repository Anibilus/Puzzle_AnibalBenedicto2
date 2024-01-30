package com.example.puzzle_anibalbenedicto.Niveles;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.DatabaseHelper;
import com.example.puzzle_anibalbenedicto.MainActivity;
import com.example.puzzle_anibalbenedicto.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NivelDosActivity extends AppCompatActivity {

    private static final int FILAS = 4;
    private static final int COLUMNAS = 4;
    private ImageButton[][] imageButtons;
    private ImageButton selectedImageButton;
    private int intentos = 0;
    private TextView textViewIntentos;
    private DatabaseHelper dbHelper;
    private Button buttonFinalizar;

    private List<Integer> listaRecursosImagenes = Arrays.asList(
            R.drawable.harry1, R.drawable.harry2, R.drawable.harry3, R.drawable.harry4,
            R.drawable.harry5, R.drawable.harry6, R.drawable.harry7, R.drawable.harry8,
            R.drawable.harry9, R.drawable.harry10, R.drawable.harry11, R.drawable.harry12,
            R.drawable.harry13, R.drawable.harry14, R.drawable.harry15, R.drawable.harry16
    );

    private void shuffleImagesInGridLayout() {
        List<Integer> imagenesDesordenadas = new ArrayList<>(listaRecursosImagenes);
        Collections.shuffle(imagenesDesordenadas);

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                imageButtons[i][j].setImageResource(imagenesDesordenadas.get(i * COLUMNAS + j));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_nvl2);
        buttonFinalizar = findViewById(R.id.buttonFinalizar);
        buttonFinalizar.setOnClickListener(v -> onFinalizarClick());
        dbHelper = new DatabaseHelper(this);
        GridLayout gridLayout = findViewById(R.id.gridLayoutNivelDos);
        textViewIntentos = findViewById(R.id.textViewIntentos);
        imageButtons = new ImageButton[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                String buttonID = "imageButton" + ((i * COLUMNAS) + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = findViewById(resID);
                imageButtons[i][j].setOnClickListener(v -> onImageButtonClick((ImageButton) v));
            }
        }
        shuffleImagesInGridLayout();
        Intent intent = getIntent();
        String nombrePersona = intent.getStringExtra("nombrePersona");

        TextView textViewOrdena = findViewById(R.id.textViewOrdena);
        String nombreJugador = getPlayerName();
        textViewOrdena.setText("Ordena el Puzzle " + nombreJugador);
    }

    private void onImageButtonClick(ImageButton clickedImageButton) {
        if (selectedImageButton == null) {
            selectedImageButton = clickedImageButton;
        } else {
            Drawable tempDrawable = selectedImageButton.getDrawable();
            selectedImageButton.setImageDrawable(clickedImageButton.getDrawable());
            clickedImageButton.setImageDrawable(tempDrawable);
            intentos++;

            selectedImageButton = null;
            updateIntentosCounter();
        }
    }

    private void updateIntentosCounter() {
        textViewIntentos.setText("Intentos: " + intentos);
    }

    private void onFinalizarClick() {
        String nombreJugador = getPlayerName();
        saveScore(nombreJugador);
        Toast.makeText(this, "Tu puntuación se ha guardado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NivelDosActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveScore(String nombreJugador) {
        if (dbHelper != null) {
            dbHelper.addScoreNivelDos(nombreJugador, intentos);
        }
    }

    private String getPlayerName() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UsuarioPreferences", MODE_PRIVATE);
        return preferences.getString("nombreUsuario", "NombrePorDefecto");
    }
}
