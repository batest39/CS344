package com.example.bates.finalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "home_brew";
            String description = "test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("id_home_brew", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "id_home_brew")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Home Brew")
                    .setContentText("Check your brews!")
                    .setSmallIcon(R.mipmap.ic_wine)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Check your brews!"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notificationManager.notify(1, mBuilder.build());
        }




    }
}
