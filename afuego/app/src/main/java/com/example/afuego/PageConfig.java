package com.example.afuego;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class PageConfig extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_config); //Llamamos a la vista ".xml" correspondiente

        Places.initialize(getApplicationContext(), "AIzaSyBVr5_bm3SMipEA4w-Q-oZUZrivyPwRQAA");
        //Primero creamos una función "OnClick" para cuando al clickear en la imagen con id = "inicio" nos mande a la página "MainActivity"
        ImageView inicio = findViewById(R.id.inicio);
        inicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Esta función realizará el arrastre de la barra de KM, actualizando el TextView de los KM.
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView km = findViewById(R.id.km);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualiza el TextView con la distancia seleccionada
                km.setText(progress + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Método requerido, pero no necesitamos realizar ninguna acción aquí.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Método requerido, pero no necesitamos realizar ninguna acción aquí.
            }
        });

        //Aquí añadimos función al grupo de RadioButton que selecciona entre mujer, hombre o ambos
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        RadioButton femaleRadioButton = findViewById(R.id.femaleRadioButton);
        RadioButton maleRadioButton = findViewById(R.id.maleRadioButton);
        RadioButton bothRadioButton = findViewById(R.id.bothRadioButton);

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Función para guardar en la caché los cambios
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Función para que marque el RadioButton con su id correspondiente
                if (checkedId == R.id.femaleRadioButton) {
                    editor.putString("selectedGender", "Mujeres");
                } else if (checkedId == R.id.maleRadioButton) {
                    editor.putString("selectedGender", "Hombres");
                } else if (checkedId == R.id.bothRadioButton) {
                    editor.putString("selectedGender", "Ambos");
                }
                editor.apply();

                //Función para que marque el RadioButton con su id correspondiente
                String selectedGender = sharedPreferences.getString("selectedGender", "");

                if (selectedGender.equals("Mujeres")) {
                    femaleRadioButton.setChecked(true);
                } else if (selectedGender.equals("Hombres")) {
                    maleRadioButton.setChecked(true);
                } else if (selectedGender.equals("Ambos")) {
                    bothRadioButton.setChecked(true);
                }
            }
        });

        //Esta función realizará el arrastre de la barra de Edad, actualizando el TextView de la Edad.
        SeekBar seekBar2 = findViewById(R.id.seekBar2);
        TextView km2 = findViewById(R.id.km2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                // Actualiza el TextView con la distancia seleccionada
                km2.setText(progress + " años");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {
                // Método requerido, pero no necesitamos realizar ninguna acción aquí.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {
                // Método requerido, pero no necesitamos realizar ninguna acción aquí.
            }
        });

        TextView logoutButton = findViewById(R.id.textView11);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión
                FirebaseAuth.getInstance().signOut();
                // Navegar de nuevo a la actividad de inicio de sesión (LoginActivity)
                startActivity(new Intent(PageConfig.this, PageLogin.class));
                finish(); // Cierra la actividad actual
            }
        });

        TextView locationTextView = findViewById(R.id.textView30);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzar la actividad de autocompletado de Places
                List<Field> fields = Arrays.asList(Field.LAT_LNG, Field.NAME, Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(PageConfig.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                // Obtener la información de la ubicación seleccionada
                String locationName = place.getName();
                String locationAddress = place.getAddress();
                // Actualizar el TextView con la ubicación seleccionada
                TextView locationTextView = findViewById(R.id.textView30);
                locationTextView.setText(locationName + ", " + locationAddress);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Manejar errores
            } else if (resultCode == RESULT_CANCELED) {
                // El usuario canceló la selección
            }
        }
    }
}
