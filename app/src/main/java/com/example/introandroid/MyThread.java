package com.example.introandroid;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MyThread extends Thread{
    private static final String TAG = "MyThread";

    @Override
    public final void run() {
        try {
            for (int i = 0; i < 5; i++) {
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date current_date = new Date();
                Log.i(TAG, "Running service " + (i + 1) + "/5 @ " + dateFormat.format(current_date));
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Log.i(TAG, "Finalizó el Thread: " + Process.myTid());
        }
    }
}
