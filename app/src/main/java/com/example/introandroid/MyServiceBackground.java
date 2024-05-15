package com.example.introandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyServiceBackground extends Service {

    private static final String TAG = "MyServiceBackground";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, String.format("El proceso %s ejecutando comando en segundo plano", Process.myPid()));
        Thread running = new MyThread();
        running.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Ejecuta: OnCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Ejecuta: OnDestroy");
    }
}
