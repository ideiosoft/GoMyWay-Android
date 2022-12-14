package ssc.snow.app.gomyways.data.fcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ssc.snow.app.gomyways.R;


/**
 * Created by Snow-Dell-05 on 05-Feb-18.
 */

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    int numMessages = 0;
    Bitmap bitmap = null;
    private Context mContext;
    private Ringtone ringTone = null;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

  /*  public void showNotificationMessage(String title, String message, Intent intent) {
        showNotificationMessage(title, message, intent, null);
    }*/

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void showNotificationMessage(final String title, final String message, Intent intent, String imageUrl) {
        //      Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        String id = mContext.getString(R.string.default_notification_channel_id);
        //        notification icon1
        final int icon = R.drawable.ic_launcher;
        intent.putExtra("fromNotification", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(
                mContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, id);
        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");

        // final Uri alarmSound = RingtoneManager.getActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_RINGTONE);

        //  final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if (!TextUtils.isEmpty(imageUrl)) {
            // bitmap = getBitmapFromURL(imageUrl);
            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                new GeneratePictureStyleNotification(mBuilder, icon, title, message,

                        resultPendingIntent, alarmSound, imageUrl).execute();
            }
        } else {

            showSmallNotification(mBuilder, icon, title, message, resultPendingIntent, alarmSound);
            playNotificationSound();
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message,
                                       PendingIntent resultPendingIntent, Uri alarmSound) {
        //      NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        //      inboxStyle.
        String id = mContext.getString(R.string.default_notification_channel_id);

        NotificationManager notifManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        int mPriority = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mPriority = NotificationManager.IMPORTANCE_MAX;
                NotificationChannel mChannel = notifManager.getNotificationChannel(id);
                if (mChannel == null) {
                    mChannel = new NotificationChannel(id, title, mPriority);
                    mChannel.enableVibration(true);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(Color.RED);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notifManager.createNotificationChannel(mChannel);
                }
            } else {
                mPriority = NotificationCompat.PRIORITY_HIGH;
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        notification = mBuilder
                .setTicker(title)
                .setAutoCancel(true)
                .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                .setFullScreenIntent(resultPendingIntent, true)
                .setSound(alarmSound)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setPriority(mPriority)
                .setContentTitle(title)  // required
                .setSmallIcon(R.mipmap.app_icon_48)
                .setContentText(message)  // required
                .build();


        notifManager.notify(Config.NOTIFICATION_ID, notification);

    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);

        String id = mContext.getString(R.string.default_notification_channel_id);
        int mPriority;

        Notification notification;
        NotificationManager notifManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mPriority = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, mPriority);
                mChannel.enableVibration(true);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
        } else {
            mPriority = NotificationCompat.PRIORITY_HIGH;
        }

        notification = mBuilder
                .setTicker(title)
                .setAutoCancel(true)
                .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setPriority(mPriority)
                .setStyle(bigPictureStyle)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setShowWhen(true)
                .setChannelId(id)
                // new BitmapDrawable(mContextgetResources(), bitmap)
                .setSmallIcon(R.drawable.com_facebook_profile_picture_blank_portrait)

                .setContentText(message)
                .build();

        notifManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {

            //    Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/raw/notification");
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            ringTone = RingtoneManager.getRingtone(mContext, notification);
            if (ringTone != null) {
                ringTone.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    private class GeneratePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {
        private NotificationCompat.Builder mBuilderr;
        private int mIconn;
        private String mTitlel, mMessages;
        private PendingIntent mResultPendingIntentt;
        private Uri mAlaramSound;
        private String mImgUrl;

        public GeneratePictureStyleNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message,
                                                PendingIntent resultPendingIntent, Uri alarmSound, String mImgUrll) {
            this.mBuilderr = mBuilder;
            this.mIconn = icon;
            this.mTitlel = title;
            this.mMessages = message;
            this.mResultPendingIntentt = resultPendingIntent;
            this.mImgUrl = mImgUrll;
            this.mAlaramSound = alarmSound;

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(mImgUrl);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("error", e.toString());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                showBigNotification(result, mBuilderr, mIconn, mTitlel, mMessages, mResultPendingIntentt, mAlaramSound);
            } else {
                showSmallNotification(mBuilderr, mIconn, mTitlel, mMessages, mResultPendingIntentt, mAlaramSound);
            }
        }
    }

}
