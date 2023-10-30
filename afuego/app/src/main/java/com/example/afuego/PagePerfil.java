package com.example.afuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PagePerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_perfil);

        //Boton que navegará a la página MainActivity
        ImageView inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Boton que navegará a la página PageConfig
        ImageView ajustes = findViewById(R.id.ajustes);
        ajustes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),PageConfig.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView imagenedit = findViewById(R.id.imagenedit);
        imagenedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),PageEdit.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
