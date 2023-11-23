package com.example.afuego;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageChat extends AppCompatActivity {

    private static final String TAG = "PageChat";

    // Lista de mensajes y adaptador para el RecyclerView
    private List<Message> messages;
    private MessageAdapter messageAdapter;

    // Elementos de la interfaz de usuario
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private RecyclerView recyclerViewChat;

    // Autenticación de Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    // Referencia a la base de datos de Firebase
    private DatabaseReference messagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_chat);

        // Inicialización de Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        messagesRef = FirebaseDatabase.getInstance().getReference("conversations");

        // Verificar si el usuario está autenticado
        if (currentUser == null) {
            // El usuario no está autenticado, iniciar la actividad de inicio de sesión
            Intent intent = new Intent(this, PageLogin.class);
            startActivity(intent);
            finish(); // Para cerrar la actividad actual y evitar que el usuario regrese a PageChat presionando el botón "Atrás"
            return; // Salir del método onCreate
        }

        // Configuración del botón para ir a la página PageChats
        ImageView chats = findViewById(R.id.chats);
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PageChats.class);
                startActivityForResult(intent, 0);
            }
        });

        // Inicialización de la lista de mensajes y del adaptador
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);

        // Configuración del RecyclerView
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(messageAdapter);

        // Inicialización de los elementos de la interfaz de usuario
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);

        // Configuración del botón de enviar mensaje
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Configuración de ChildEventListener para recibir mensajes en tiempo real
        String currentUserEmail = currentUser.getEmail();
        String receiverEmail = "sandra@gmail.com";
        String conversationId = generateConversationId(currentUserEmail, receiverEmail);

        DatabaseReference conversationRef = messagesRef.child(conversationId);

        conversationRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                // Cuando se agrega un nuevo mensaje a la base de datos
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);
                messageAdapter.notifyDataSetChanged();
                recyclerViewChat.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                // Manejar cambios si es necesario
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // Manejar eliminaciones si es necesario
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                // Manejar movimientos si es necesario
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores
                Log.e(TAG, "Error en ChildEventListener", databaseError.toException());
            }
        });
    }

    // Método para enviar un mensaje
    private void sendMessage() {
        try {
            // Obtener el contenido del mensaje desde el EditText
            String messageContent = editTextMessage.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                // Obtener el correo electrónico del remitente
                String senderEmail = currentUser.getEmail();
                // Definir el correo electrónico del destinatario (puede ser dinámico en una aplicación real)
                String receiverEmail = "sandra@gmail.com";

                // Crear una clave única para la conversación
                String conversationId = generateConversationId(senderEmail, receiverEmail);

                // Referencia a la ubicación específica de la base de datos
                DatabaseReference conversationRef = messagesRef.child(conversationId);

                // Crear un nuevo mensaje
                Message newMessage = new Message(senderEmail, receiverEmail, messageContent);

                // Enviar el mensaje a Firebase Realtime Database
                String messageId = conversationRef.push().getKey();
                newMessage.setMessageId(messageId);
                conversationRef.child(messageId).setValue(newMessage);

                // Limpiar el campo de texto localmente
                editTextMessage.getText().clear();
            } else {
                // Manejar caso en que el contenido del mensaje está vacío
                Log.w(TAG, "No se puede enviar un mensaje vacío");
            }
        } catch (Exception e) {
            // Manejar excepción general
            e.printStackTrace();
            Log.e(TAG, "Error al enviar el mensaje", e);
        }
    }

    // Método para generar una clave única para la conversación
    private String generateConversationId(String senderEmail, String receiverEmail) {
        String sanitizedSender = sanitizeEmail(senderEmail);
        String sanitizedReceiver = sanitizeEmail(receiverEmail);
        List<String> ids = new ArrayList<>();
        ids.add(sanitizedSender);
        ids.add(sanitizedReceiver);
        Collections.sort(ids);
        return ids.get(0) + "_" + ids.get(1);
    }

    // Método para sanear el correo electrónico y obtener una clave válida para Firebase
    private String sanitizeEmail(String email) {
        return email.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
