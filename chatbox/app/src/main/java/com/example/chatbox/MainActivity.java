package com.example.chatbox;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    private MessageAdapter adapter;
    private List<Message> messageList;
    private String deviceId;
    private String userName;
    private EditText editMessage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        loadUserName();

        // Setup UI
        editMessage = findViewById(R.id.edit_message);
        ImageButton btnSend = findViewById(R.id.btn_send);
        recyclerView = findViewById(R.id.recycler_messages);

        messageList = new ArrayList<>();
        adapter = new MessageAdapter(messageList, deviceId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnSend.setOnClickListener(v -> sendMessage());

        listenForMessages();
    }

    private void loadUserName() {
        SharedPreferences prefs = getSharedPreferences("ChatPrefs", MODE_PRIVATE);
        userName = prefs.getString("user_name", null);

        if (userName == null) {
            showNameDialog();
        }
    }

    private void showNameDialog() {
        EditText input = new EditText(this);
        input.setHint("Enter your name");
        new AlertDialog.Builder(this)
                .setTitle("Welcome")
                .setMessage("Please enter your name to start chatting")
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        userName = name;
                        getSharedPreferences("ChatPrefs", MODE_PRIVATE)
                                .edit()
                                .putString("user_name", name)
                                .apply();
                    } else {
                        showNameDialog(); // Keep asking if empty
                    }
                })
                .show();
    }

    private void listenForMessages() {
        db.collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(TAG, "Listen failed.", error);
                        return;
                    }

                    if (value != null) {
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            Message message = dc.getDocument().toObject(Message.class);
                            message.setId(dc.getDocument().getId());

                            switch (dc.getType()) {
                                case ADDED:
                                    messageList.add(message);
                                    adapter.notifyItemInserted(messageList.size() - 1);
                                    recyclerView.scrollToPosition(messageList.size() - 1);
                                    break;
                                case MODIFIED:
                                    updateMessageInList(message);
                                    break;
                                case REMOVED:
                                    // Handle removal if needed
                                    break;
                            }
                        }
                    }
                });
    }

    private void updateMessageInList(Message message) {
        for (int i = 0; i < messageList.size(); i++) {
            // FIX: Use Objects.equals for null-safe comparison (fixes reported NPE)
            if (Objects.equals(messageList.get(i).getId(), message.getId())) {
                messageList.set(i, message);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }

    private void sendMessage() {
        String text = editMessage.getText().toString().trim();
        if (text.isEmpty() || userName == null) return;

        Message message = new Message(null, text, deviceId, userName, Timestamp.now());
        db.collection("messages").add(message)
                .addOnSuccessListener(documentReference -> editMessage.setText(""))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error sending message", e);
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}