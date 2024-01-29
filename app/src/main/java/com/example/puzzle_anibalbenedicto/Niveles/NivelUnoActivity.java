package com.example.puzzle_anibalbenedicto.Niveles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NivelUnoActivity extends AppCompatActivity {

    private static final int FILAS = 2;
    private static final int COLUMNAS = 4;
    private ImageButton[][] imageButtons;
    private ImageButton selectedImageButton;
    private int intentos = 0;
    private TextView textViewIntentos;
    private DatabaseHelper dbHelper;
    private Button buttonFinalizar;

    private List<Integer> listaRecursosImagenes = Arrays.asList(
            R.drawable.imagen1,
            R.drawable.imagen2,
            R.drawable.imagen3,
            R.drawable.imagen4,
            R.drawable.imagen5,
            R.drawable.imagen6,
            R.drawable.imagen7,
            R.drawable.imagen8
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
        setContentView(R.layout.selector_nvl1);
        buttonFinalizar = findViewById(R.id.buttonFinalizar);
        buttonFinalizar.setOnClickListener(v -> onFinalizarClick());
        dbHelper = new DatabaseHelper(this);
        GridLayout gridLayout = findViewById(R.id.gridLayoutNivelUno);
        textViewIntentos = findViewById(R.id.textViewIntentos);
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
        String nombreJugador=getPlayerName();
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
        Intent intent = new Intent(NivelUnoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveScore(String nombreJugador) {

        if (dbHelper != null) {

            dbHelper.addScoreNivelUno(nombreJugador, intentos);
        }
    }

    private String getPlayerName() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("UsuarioPreferences", MODE_PRIVATE);
        return preferences.getString("nombreUsuario", "NombrePorDefecto");
    }


    /**private boolean compareDrawables(Drawable drawable1, Drawable drawable2) {
        if (drawable1 == null && drawable2 == null) {
            return true;
        }
        if (drawable1 == null || drawable2 == null) {
            return false;
        }
        Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();
        return bitmap1.sameAs(bitmap2);
    }**/
}


