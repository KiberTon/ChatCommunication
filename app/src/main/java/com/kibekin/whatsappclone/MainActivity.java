package com.kibekin.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewMessage;
    private WhatsAppMessageAdapter adapter;
    private ProgressBar progressBar;
    private ImageButton imageButtonSendImage;
    private Button buttonSendMessage;
    private EditText editTextMessage;

    private String userName;

    FirebaseDatabase database;
    DatabaseReference databaseReferenceMessage;
    ChildEventListener childEventListenerMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        databaseReferenceMessage = database.getReference().child("messages");

        progressBar = findViewById(R.id.progressBar);
        imageButtonSendImage = findViewById(R.id.imageButtonSendImage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);
        editTextMessage = findViewById(R.id.editTextEditMessage);

        userName = "Default User";

//  Подключение  и установка adapter
        listViewMessage = findViewById(R.id.listViewMessage);
        List<WhatsAppMessage> whatsAppMessages = new ArrayList<>();
        adapter = new WhatsAppMessageAdapter(this, R.layout.message_item, whatsAppMessages);
        listViewMessage.setAdapter(adapter);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//  Отображение кнопки послу ввода текста
                if (s.toString().trim().length() > 0) {
                    buttonSendMessage.setEnabled(true);
                } else {
                    buttonSendMessage.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//  Установка количество символов в EditText
        editTextMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsAppMessage message = new WhatsAppMessage();
                message.setText(editTextMessage.getText().toString());
                message.setName(userName);
                message.setImageUrl(null);

                databaseReferenceMessage.push().setValue(message);

                editTextMessage.setText("");
            }
        });
        imageButtonSendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        childEventListenerMessages = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                WhatsAppMessage message = dataSnapshot.getValue(WhatsAppMessage.class);
                adapter.add(message);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReferenceMessage.addChildEventListener(childEventListenerMessages);
    }
}
