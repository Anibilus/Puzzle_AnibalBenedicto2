package com.example.puzzle_anibalbenedicto.Niveles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.DatabaseHelper;
import com.example.puzzle_anibalbenedicto.R;

import java.lang.reflect.Field;
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
    private final int[][] posicionesCorrectas = {
            {0, 0}, {0, 1}, {0, 2}, {0, 3},
            {1, 0}, {1, 1}, {1, 2}, {1, 3}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_nvl1);

        // Obtener el GridLayout y el TextView
        GridLayout gridLayout = findViewById(R.id.gridLayoutNivelUno);
        textViewIntentos = findViewById(R.id.textViewIntentos);

        // Obtener una instancia de DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Inicializar la matriz de ImageButton
        imageButtons = new ImageButton[FILAS][COLUMNAS];

        // Llenar la matriz con referencias a los ImageButton en el GridLayout
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                String buttonID = "imageButton" + ((i * COLUMNAS) + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = findViewById(resID);

                // Añadir OnClickListener a cada ImageButton
                imageButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onImageButtonClick((ImageButton) v);
                    }
                });
            }
        }

        // Desordenar las imágenes
        shuffleImages();

        // Configurar el contador de intentos
        updateIntentosCounter();
    }

    // Método para desordenar las imágenes
    private void shuffleImages() {
        Collections.shuffle(listaRecursosImagenes);
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                imageButtons[i][j].setImageResource(listaRecursosImagenes.get(i * COLUMNAS + j));
            }
        }
    }

    // Método para desordenar un arreglo
    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Intercambiar elementos
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    private int getImageResourceId(ImageButton imageButton) {
        Drawable drawable = imageButton.getDrawable();
        if (drawable != null) {
            return getDrawableResourceId(drawable);
        }
        return 0;
    }
    private int getDrawableResourceId(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            try {
                Field field = BitmapDrawable.class.getDeclaredField("mBitmapState");
                field.setAccessible(true);
                Object bitmapState = field.get(drawable);
                Field bitmapField = bitmapState.getClass().getDeclaredField("mBitmap");
                bitmapField.setAccessible(true);
                Bitmap bitmap = (Bitmap) bitmapField.get(bitmapState);

                // Devuelve el ID del recurso asociado con el Bitmap (puede ser 0 si no se cargó desde un recurso)
                return bitmapState.hashCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
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
            if (isPuzzleCompleted()) {
                // Guardar la puntuación en la base de datos
                saveScore();
            }
        }
    }

    // Método para actualizar el contador de intentos en el TextView
    private void updateIntentosCounter() {
        textViewIntentos.setText("Intentos: " + intentos);
    }

    // Método para guardar la puntuación en la base de datos
    private void saveScore() {
        // Obtener el nombre del jugador (puedes implementar la lógica para obtener el nombre)
        String nombreJugador = "NombrePorDefecto";

        // Guardar la puntuación en la base de datos
        dbHelper.addScoreNivelUno(nombreJugador, intentos);

        // Aquí puedes realizar acciones adicionales si lo necesitas, como mostrar un mensaje al jugador, etc.
    }

    // Método para verificar si el puzzle está completado
    private boolean isPuzzleCompleted() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                // Obtener el recurso de la imagen actual
                int resource = getResourceId(imageButtons[i][j]);

                // Obtener la posición correcta de la imagen actual
                int[] posicionCorrecta = posicionesCorrectas[i * COLUMNAS + j];

                // Verificar si la imagen está en la posición correcta
                if (resource != listaRecursosImagenes.get(posicionCorrecta[0] * COLUMNAS + posicionCorrecta[1])) {
                    return false; // Si al menos una imagen está en la posición incorrecta, el puzzle no está completo
                }
            }
        }
        return true; // Todas las imágenes están en la posición correcta, el puzzle está completo
    }

    private int getResourceId(ImageButton imageButton) {
        Drawable drawable = imageButton.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            try {
                Field field = BitmapDrawable.class.getDeclaredField("mBitmapState");
                field.setAccessible(true);
                Object bitmapState = field.get(drawable);
                Field bitmapField = bitmapState.getClass().getDeclaredField("mBitmap");
                bitmapField.setAccessible(true);
                Bitmap bitmap = (Bitmap) bitmapField.get(bitmapState);

                // Devuelve el ID del recurso asociado con el Bitmap (puede ser 0 si no se cargó desde un recurso)
                return bitmapState.hashCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
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


