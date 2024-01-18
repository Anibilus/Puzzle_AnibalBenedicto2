package com.example.puzzle_anibalbenedicto.Puntuaziones;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.DatabaseHelper;
import com.example.puzzle_anibalbenedicto.R;
public class PuntuacionNvlDosActivity extends AppCompatActivity {

    private ListView listViewPuntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones_nvl2);

        listViewPuntuaciones = findViewById(R.id.listViewPuntuacionesNvlDos);

        // Consultar las puntuaciones del nivel 2 desde la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.getColumnId(),
                DatabaseHelper.getColumnName(),
                DatabaseHelper.getColumnScore(),
                DatabaseHelper.getColumnNivelDosScore() // Cambiado a nivel dos
        };

        String selection = DatabaseHelper.getColumnNivelDosId() + " = ?";
        String[] selectionArgs = {"1"}; // Puedes cambiar esto según tus necesidades

        Cursor cursor = db.query(
                DatabaseHelper.getTableScoresNivelDos(), // Cambiado a nivel dos
                projection,
                selection,
                selectionArgs,
                null,
                null,
                DatabaseHelper.getColumnNivelDosScore() + " ASC" // Puedes ordenar por score
        );

        // Configurar el adaptador en la lista utilizando SimpleCursorAdapter
        String[] fromColumns = {DatabaseHelper.getColumnName(), DatabaseHelper.getColumnNivelDosScore()};
        int[] toViews = {android.R.id.text1, android.R.id.text2}; // Utiliza los identificadores predeterminados para TextView

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2, // Diseño predeterminado de Android con dos líneas
                cursor,
                fromColumns,
                toViews,
                0
        );

        listViewPuntuaciones.setAdapter(adapter);

        // Cerrar el cursor y la conexión a la base de datos
        cursor.close();
        db.close();
    }
}
