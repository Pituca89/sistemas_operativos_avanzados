package com.example.introandroid;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btn_start;
    TextView txt_main;
    MyBoundService boundService;
    boolean mBound = false;
    private static final String TAG = "MainActivity";
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);

        if (permission == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show();
        }else{
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        @SuppressLint("UnsafeIntentLaunch") PendingIntent updatedPendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                1,
                getIntent(),
                PendingIntent.FLAG_IMMUTABLE // setting the mutability flag
        );
        NotificationChannel channel = new NotificationChannel(
                "running_channel",
                "Running Notification",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);


        Log.i(TAG, "Ejecuta: OnCreate");
        Log.i(TAG, String.format("Process ID: %s", Process.myPid()));
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn_start = findViewById(R.id.btn_start);
        txt_main = findViewById(R.id.txt_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_start.setOnClickListener(btnListener);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            txt_main.setText("INICIANDO APP...");
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            String current_date = boundService.getDateTime();
            intent.putExtra("date", current_date);
            startActivity(intent);
                                                                                                                        finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Ejecuta: OnStart");
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Ejecuta: OnResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Ejecuta: OnStop");
        unbindService(connection);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Ejecuta: OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Ejecuta: OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Ejecuta: OnRestart");
    }

    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyBoundService.LocalBinder binder = (MyBoundService.LocalBinder) service;
            boundService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}