package com.example.afuego;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class PageRegister extends AppCompatActivity {

    //Declaramos el campo del email, contraseña y la Autenticación de Firebase
    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_register);

        //Declaramos los botones y textos que serán usados para dar función
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //Si el email y/o contraseña están vacios, saltará un aviso para que complete los campos
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PageRegister.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Registrar al usuario en Firebase
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(PageRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registro exitoso, puedes navegar a la pantalla de inicio de sesión o a la siguiente actividad
                                        startActivity(new Intent(PageRegister.this, PageLogin.class));
                                    } else {
                                        // El registro falló
                                        Toast.makeText(PageRegister.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}

