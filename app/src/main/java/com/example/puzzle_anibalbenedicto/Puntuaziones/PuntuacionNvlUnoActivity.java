package com.example.puzzle_anibalbenedicto.Puntuaziones;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.DatabaseHelper;
import com.example.puzzle_anibalbenedicto.MainActivity;
import com.example.puzzle_anibalbenedicto.R;

public class PuntuacionNvlUnoActivity extends AppCompatActivity {

    private ListView listViewPuntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones_nvl1);

        listViewPuntuaciones = findViewById(R.id.listViewPuntuacionesNvlUno);

        // Consultar las puntuaciones del nivel 1 desde la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Cursor cursor = dbHelper.getAllScoresNivelUno();  // Llama al método aquí

        // Configurar el adaptador en la lista utilizando SimpleCursorAdapter
        String[] fromColumns = {DatabaseHelper.getColumnName(), DatabaseHelper.getColumnNivelUnoScore()};
        int[] toViews = {android.R.id.text1, android.R.id.text2}; // Utiliza los identificadores predeterminados para TextView

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listViewPuntuaciones.setAdapter(adapter);

       //boton volver al mainactivity
        Button btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuntuacionNvlUnoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

