package com.example.afuego;

import androidx.appcompat.app.AppCompatActivity;
//Importamos todos las clases necesarias (importadas al declarar la función
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Llamamos a la vista ".xml" correspondiente

        //Primero creamos una función "OnClick" para cuando al clickear en la imagen con id = "perfil" nos mande a la página "PagePerfil"
        ImageView perfil = findViewById(R.id.perfil);
        perfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),PagePerfil.class); //Aquí declaramos que nos mande a "PagePerfil"
                startActivityForResult(intent, 0);
            }
        });

        //Declaramos otra función igual a la anterior, pero esta vez nos mandará a "PageLogin"
        ImageView login = findViewById(R.id.imageView15);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),PageLogin.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}