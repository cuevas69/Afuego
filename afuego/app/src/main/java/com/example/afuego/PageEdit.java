package com.example.afuego;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PageEdit extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK1 = 1;
    private static final int REQUEST_IMAGE_PICK2 = 2;
    private static final int REQUEST_IMAGE_PICK3 = 3;
    private static final int REQUEST_IMAGE_PICK4 = 4;
    private ImageView imagen1;
    private ImageView imagen2;
    private ImageView imagen3;
    private ImageView imagen4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_edit);

        ImageView flechaedit = findViewById(R.id.flechaedit);
        imagen1 = findViewById(R.id.imagen1);
        imagen2 = findViewById(R.id.imagen2);
        imagen3 = findViewById(R.id.imagen3);
        imagen4 = findViewById(R.id.imagen4);

        flechaedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PagePerfil.class);
                startActivityForResult(intent, 0);
            }
        });

        ImageView suma1 = findViewById(R.id.suma1);
        suma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre el explorador de archivos para seleccionar una imagen.
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK1);
            }
        });

        ImageView suma2 = findViewById(R.id.suma2);
        suma2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre el explorador de archivos para seleccionar una imagen.
                Intent intent = new  Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK2);
            }
        });

        ImageView suma3 = findViewById(R.id.suma3);
        suma3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre el explorador de archivos para seleccionar una imagen.
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK3);
            }
        });

        ImageView suma4 = findViewById(R.id.suma4);
        suma4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre el explorador de archivos para seleccionar una imagen.
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Establece la imagen seleccionada en "imagen1".
            imagen1.setImageURI(selectedImageUri);
        } else if (requestCode == REQUEST_IMAGE_PICK2 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Establece la imagen seleccionada en "imagen2".
            imagen2.setImageURI(selectedImageUri);
        } else if (requestCode == REQUEST_IMAGE_PICK3 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Establece la imagen seleccionada en "imagen3".
            imagen3.setImageURI(selectedImageUri);
        } else if (requestCode == REQUEST_IMAGE_PICK4 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Establece la imagen seleccionada en "imagen4".
            imagen4.setImageURI(selectedImageUri);
        }
    }
}
