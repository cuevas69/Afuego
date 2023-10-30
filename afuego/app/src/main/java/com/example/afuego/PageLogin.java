package com.example.afuego;

import android.content.Intent;
import android.os.Bundle;
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

public class PageLogin extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Declaramos los String email y contraseña
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Esta función hará que si dejamos vacío el email, nos saltará un aviso en el que pida que rellene los campos.
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PageLogin.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Si rellenamos el campo, iniciará sesión con Firebase
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(PageLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Inicio de sesión exitoso, puedes navegar a la siguiente actividad
                                        startActivity(new Intent(PageLogin.this, MainActivity.class));
                                    } else {
                                        // El inicio de sesión falló
                                        Toast.makeText(PageLogin.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad de registro (RegisterActivity)
                startActivity(new Intent(PageLogin.this, PageRegister.class));
            }
        });
    }
}
