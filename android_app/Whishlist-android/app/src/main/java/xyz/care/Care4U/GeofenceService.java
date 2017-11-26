package xyz.care.Care4U;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Locale;



public class GeofenceService extends IntentService {

    public static  final String TAG="Omnalista";



    public GeofenceService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()){
            // TODO : Handle error
        }else{

            int transition = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);

            String requestId = geofence.getRequestId();

            Location location = geofencingEvent.getTriggeringLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
                Log.d(TAG,"Entering Geofence"+requestId);
                sendNotification(requestId,latitude,longitude);
            }
        }

    }

    public void sendNotification(String wish,double latitude,double longitude) {





        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.icon);

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        //Intent intent = new Intent(this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);


        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        builder.setContentTitle("Nearby Medical Store");
        builder.setContentText("You can get your medicine   "+wish+" from nearby location ");
        builder.setSubText("You can get a medicine nearby");
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Your medicine - "+wish+" nearby"));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }
}
