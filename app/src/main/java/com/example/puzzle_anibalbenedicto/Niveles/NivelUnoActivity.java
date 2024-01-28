package com.example.puzzle_anibalbenedicto.Niveles;

import android.content.Intent;
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
    private int intentos = 0; // Contador de intentos
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
        // Obtener el GridLayout y el TextView
        GridLayout gridLayout = findViewById(R.id.gridLayoutNivelUno);
        textViewIntentos = findViewById(R.id.textViewIntentos);

        // Inicializar la matriz de ImageButton
        imageButtons = new ImageButton[FILAS][COLUMNAS];

        // Llenar la matriz con referencias a los ImageButton en el GridLayout y asignar OnClickListener
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                String buttonID = "imageButton" + ((i * COLUMNAS) + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = findViewById(resID);

                // Añadir OnClickListener a cada ImageButton
                imageButtons[i][j].setOnClickListener(v -> onImageButtonClick((ImageButton) v));
            }
        }

        // Desordenar las imágenes después de crear los ImageButton
        shuffleImagesInGridLayout();
    }
    private void onImageButtonClick(ImageButton clickedImageButton) {
        if (selectedImageButton == null) {
            // No hay ninguna imagen seleccionada, seleccionar la actual
            selectedImageButton = clickedImageButton;
        } else {
            // Intercambiar las imágenes
            Drawable tempDrawable = selectedImageButton.getDrawable();
            selectedImageButton.setImageDrawable(clickedImageButton.getDrawable());
            clickedImageButton.setImageDrawable(tempDrawable);

            // Incrementar el contador de intentos
            intentos++;

            // Reiniciar la imagen seleccionada
            selectedImageButton = null;

            // Actualizar el contador de intentos
            updateIntentosCounter();
        }
    }

    // Método para actualizar el contador de intentos en el TextView
    private void updateIntentosCounter() {
        textViewIntentos.setText("Intentos: " + intentos);
    }
    private void onFinalizarClick() {
        // Guardar la puntuación en la base de datos
        saveScore();


        // Mostrar Toast indicando que la puntuación se ha guardado
        Toast.makeText(this, "Tu puntuación se ha guardado", Toast.LENGTH_SHORT).show();

        // Regresar al MainActivity
        Intent intent = new Intent(NivelUnoActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual para evitar que el usuario vuelva atrás
    }
    private void saveScore() {
        // Asegúrate de que dbHelper no es nulo antes de usarlo
        if (dbHelper != null) {
            // Obtener el nombre del jugador (puedes implementar la lógica para obtener el nombre)
            String nombreJugador = "NombrePorDefecto";

            // Guardar la puntuación en la base de datos utilizando DatabaseHelper
            dbHelper.addScoreNivelUno(nombreJugador, intentos);
        }
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


