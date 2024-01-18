package com.example.puzzle_anibalbenedicto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.Puntuaziones.PuntuacionNvlDosActivity;
import com.example.puzzle_anibalbenedicto.Puntuaziones.PuntuacionNvlUnoActivity;

public class PuntuacionesMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones_menu);

        Button btnPuntuacionesNivelUno = findViewById(R.id.btnPuntuacionesNivelUno);
        Button btnPuntuacionesNivelDos = findViewById(R.id.btnPuntuacionesNivelDos);

        btnPuntuacionesNivelUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuntuacionesMenuActivity.this, PuntuacionNvlUnoActivity.class);
                startActivity(intent);
            }
        });

        btnPuntuacionesNivelDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuntuacionesMenuActivity.this, PuntuacionNvlDosActivity.class);
                startActivity(intent);
            }
        });
    }

}

