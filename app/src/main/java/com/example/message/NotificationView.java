package com.example.message;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationView extends AppCompatActivity {
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.txtnumber);
        textView2 = findViewById(R.id.txtmessage);
        //getting the notification message
        String sender = getIntent().getStringExtra("sender");
        String message=getIntent().getStringExtra("message");
        textView1.setText(sender);
        textView2.setText(message);
    }
}

