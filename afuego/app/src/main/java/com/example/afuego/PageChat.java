package com.example.afuego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PageChat extends AppCompatActivity {

    private List<Message> messages;
    private MessageAdapter messageAdapter;
    private EditText editTextMessage;
    private Button buttonSendMessage;
    private RecyclerView recyclerViewChat; // Declarar recyclerViewChat aquí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_chat);

        // Botón que navegará a la página MainActivity
        ImageView chats = findViewById(R.id.chats);
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PageChats.class);
                startActivityForResult(intent, 0);
            }
        });

        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);

        recyclerViewChat = findViewById(R.id.recyclerViewChat); // Inicializar recyclerViewChat aquí
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(messageAdapter);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String messageContent = editTextMessage.getText().toString().trim();
        if (!messageContent.isEmpty()) {
            // Reemplazar "SenderName" con el nombre real del remitente
            Message newMessage = new Message("SenderName", messageContent);
            messages.add(newMessage);
            messageAdapter.notifyItemInserted(messages.size() - 1);
            recyclerViewChat.scrollToPosition(messages.size() - 1);
            editTextMessage.getText().clear();
        }
    }
}


