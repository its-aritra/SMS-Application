package com.example.message;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_RECEIVE_SMS = 0;
    TextView txt_number, txt_message;
    EditText editTextNumber;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        setMessage();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)) {
                //Do not do anything
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSION_REQUEST_RECEIVE_SMS);
            }
        }

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
    }

    public void buttonForward(View view){
        editTextNumber = findViewById(R.id.editTextNumber);
        String number = editTextNumber.getText().toString();

        SmsManager smsManagerSend = SmsManager.getDefault();
        smsManagerSend.sendTextMessage(number,
                null,
                txt_message.getText().toString(),
                null,
                null);

        Toast.makeText(MainActivity.this,"message sent to : " + number, Toast.LENGTH_SHORT).show();
    }
    private void setMessage() {
        Bundle b = getIntent().getBundleExtra("data");
        txt_number = findViewById(R.id.txtnumber);
        txt_message = findViewById(R.id.txtmessage);

        if (b != null) {

            String number = b.getString("number");
            String content = b.getString("content");

            txt_number.setText(number);
            txt_message.setText(content);
        }
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    protected void onStop () {
        super .onStop() ;
        startService( new Intent( this, MySMSReceiver. class )) ;
    }
    public void closeApp (View view) {
        finish() ;
    }

}


