package com.example.introandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class SecondActivity extends AppCompatActivity {

    TextView txt_date;
    Button btn_background;
    Button btn_foreground;
    Button btn_worker;

    WorkManager workManager;
    private static final String TAG = "SecondActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        btn_background = findViewById(R.id.btn_background);
        btn_foreground = findViewById(R.id.btn_foreground);
        btn_worker = findViewById(R.id.btn_worker);
        txt_date = findViewById(R.id.txt_date);
        String data = null;
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                data = extras.getString("date");
            }
        }else{
            data = savedInstanceState.getString("date");
        }
        if(data == null){
            data = "Desconocido";
        }
        Log.i(TAG, data);
        txt_date.setText(String.format("Hora actual: %s", data));
        btn_background.setOnClickListener(btnListenerBackground);
        btn_foreground.setOnClickListener(btnListenerForeground);
        btn_worker.setOnClickListener(btnListenerWorkerService);

        workManager = WorkManager.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    View.OnClickListener btnListenerForeground = new View.OnClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(SecondActivity.this, MyServiceForeground.class);
            Context context = getApplicationContext();
            context.startForegroundService(intent); // Ejecución en primer plano
        }
    };
    View.OnClickListener btnListenerBackground = new View.OnClickListener() {
        @SuppressLint({"SetTextI18n"})
        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(SecondActivity.this, MyServiceBackground.class);
            startService(intent); // Ejecución en segundo plano
        }
    };

    View.OnClickListener btnListenerJobService = new View.OnClickListener() {
        @SuppressLint({"SetTextI18n"})
        @Override
        public void onClick(View v) {
            Intent intent;
            intent = new Intent(SecondActivity.this, MyJobIntentService.class);
            MyJobIntentService.enqueueWork(SecondActivity.this, intent);
        }
    };

    View.OnClickListener btnListenerWorkerService = new View.OnClickListener() {
        @SuppressLint({"SetTextI18n"})
        @Override
        public void onClick(View v) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiresCharging(false)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setConstraints(constraints)
                    .build();
            workManager.enqueue(oneTimeWorkRequest);
        }
    };
}