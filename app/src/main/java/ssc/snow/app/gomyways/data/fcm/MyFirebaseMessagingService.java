package ssc.snow.app.gomyways.data.fcm;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ssc.snow.app.gomyways.data.session.SessionNotNull;
import ssc.snow.app.gomyways.views.MyApplication;
import ssc.snow.app.gomyways.views.SplashScreen;
import ssc.snow.app.gomyways.views.home.controler.ScreenInboxDetail;


/**
 * Created by Snow-Dell-05 on 05-Feb-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param // messageBody FCM message body received.
     */
    Intent intent;
    private NotificationUtils notificationUtils;
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
   /* private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    } */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //   Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.wtf(TAG, "Refreshed token: " + refreshedToken);

        Log.wtf("refresh", "refreshed");
        //  Saving reg id to shared preferences

        //    If you want to send messages to this application instance or
        //    manage this apps subscriptions on the server side, send the
        //    Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
    //   [END refresh_token]

    /*
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        //  TODO: Implement this method to send token to your app server.
        SessionNotNull mSession = new SessionNotNull(this);
        mSession.setDeviceFCMToken(token);

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // { body=gxgxgxg commented on your post. Click here to view the post!,
        // type=POST,
        // uuid=7b867639-053b-4b26-82ac-0cca53f3e76a,
        // body_img_url=https://media.kofefe.s3-us-west-2.amazonaws.com/image/profile/b304ee36-ba68-4d59-9243-aee624ccbcbb.jpeg,
        // title=Somebody commented on your post!,
        // notificationId=2060c74f-51ad-4778-ae25-e3653219c7ed,
        // title_img_url=https://media.kofefe.thumbnail.s3-us-west-2.amazonaws.com/image/profile/d2257d58-d396-457c-89c4-788eb5665936.jpeg}


        //  TODO(developer): Handle FCM messages here.


        //  Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.wtf(TAG, "DATA: " + remoteMessage);
        //  StaticValues.mNotificationBlinking = true;
        Map<String, String> mMap = remoteMessage.getData();

        //  Check if message contains a notification payload.

     /*
       if (remoteMessage.getNotification() != null) {
            Log.wtf(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification("Hello working");
       }
       */

        //   Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.wtf(TAG, "Message data payload: " + remoteMessage.getData().toString());

            //    handleNow(remoteMessage.getData());
            try {

                if (MyApplication.Companion.activityContext() instanceof ScreenInboxDetail) {
                    return;
                }


                handleNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
                //   handleNow(mMap);
            } catch (Exception e) {
                Log.wtf(TAG, "Exception: " + e.getMessage());
            }


        }

        //  Also if you intend on generating your own notifications as a result of a received FCM
        //  message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     **/
    private void handleNow(Map<String, String> mMapp) {
        Log.wtf(TAG, "Short lived task is done.");
        sendNotification(mMapp);
    }

    private void sendNotification(Map<String, String> mMapp) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String mTitle = "Title", mUuid = "", mBody = "Body Message", mBody_img_url = "",
                mType = "Type", title_img_url = "img", mNotification = "";

      /* if (mType.equals("USER")) {
            intent = new Intent(this, ProfileActivity.class);

            //   setting uuid for profile and notification id


        } else if (mType.equals("POST")) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        } */


        notificationUtils = new NotificationUtils(getApplicationContext());

        notificationUtils.showNotificationMessage(mTitle, mBody, intent, mBody_img_url);


        Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", mBody);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        // play notification sound
        notificationUtils.playNotificationSound();
    }

    private void handleNotification(String mTitle, String message) {
        intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {

            //     is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        }

        notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.showNotificationMessage(mTitle, message, intent, "");
        // play sound when notification is come
        // notificationUtils.playNotificationSound();
    }
}
