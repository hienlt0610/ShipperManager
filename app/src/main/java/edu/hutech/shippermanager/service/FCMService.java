package edu.hutech.shippermanager.service;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.model.FMessage;
import edu.hutech.shippermanager.ui.activity.MainActivity;

/**
 * Created by hienl on 11/19/2016.
 */

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "hienlt0610";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) return;
        if(remoteMessage.getData() != null){
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(remoteMessage.getData());
            FMessage message = gson.fromJson(element, FMessage.class);

            if(message!=null && message.getUserid() != null && message.getUserid().equals(user.getUid())){
                sendNotification(message);
                //showAlertDialog(message);
            }
        }
    }

    private void showAlertDialog(final FMessage message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(FCMService.this)
                        .setTitle(message.getTitle())
                        .setMessage(message.getMessage())
                        .create();

                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                alertDialog.show();
            }
        });
    }

    private void sendNotification(FMessage message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_a_photo_white)
                .setContentTitle(message.getTitle())
                .setContentText(message.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
