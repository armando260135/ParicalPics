package com.example.inventarioparciales;

import com.google.firebase.messaging.FirebaseMessagingService;

public class PushNotificationService extends FirebaseMessagingService {


    // CAMBIAR A API 26 PARA MANDAR NOTIFIACACIONES Y LLEGUEN INCLUSO CUANDO SE ESTA DENTRO DE LA APP
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        String title = remoteMessage.getNotification().getTitle();
//        String text = remoteMessage.getNotification().getBody();
//        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
//        NotificationChannel channel = new NotificationChannel(
//                CHANNEL_ID,
//                "Heads Up Notification",
//                NotificationManager.IMPORTANCE_HIGH
//        );
//        getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(text)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setAutoCancel(true);
//        NotificationManagerCompat.from(this).notify(1,notification.build());
//        super.onMessageReceived(remoteMessage);
//    }
}
