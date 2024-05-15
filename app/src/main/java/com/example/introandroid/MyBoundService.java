package com.example.introandroid;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyBoundService extends Service {
    private static final String TAG = "BoundService";
    private final Random mGenerator = new Random();
    private final IBinder binder = new LocalBinder();
    public class LocalBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String getDateTime() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date current_date = new Date();
        return dateFormat.format(current_date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroy Service");
    }
}
