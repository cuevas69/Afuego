package com.example.afuego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PageChats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_chats);

        //Boton que navegará a la página MainActivity
        ImageView inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView chat1ImageView = findViewById(R.id.chat1ImageView);
        chat1ImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),PageChat.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
