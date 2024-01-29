package com.example.puzzle_anibalbenedicto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puzzle_anibalbenedicto.Niveles.NivelDosActivity;
import com.example.puzzle_anibalbenedicto.Niveles.NivelUnoActivity;

public class SelectorNivelesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_niveles);

        Log.d("SelectorNiveles", "onCreate");

        Button btnNivelUno = findViewById(R.id.btnNivel1);
        Button btnNivelDos = findViewById(R.id.btnNivel2);

        btnNivelUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SelectorNiveles", "onClick - Nivel 1");
                Intent intent = new Intent(SelectorNivelesActivity.this, NivelUnoActivity.class);
                startActivity(intent);
            }
        });

        btnNivelDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SelectorNiveles", "onClick - Nivel 2");
                Intent intent = new Intent(SelectorNivelesActivity.this, NivelDosActivity.class);
                startActivity(intent);
            }
        });
        //boton para volver a mainActivity
        Button btnVolverMenu = findViewById(R.id.btnMenu);
        btnVolverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectorNivelesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    }

