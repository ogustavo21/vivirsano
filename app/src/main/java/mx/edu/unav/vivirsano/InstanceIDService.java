package mx.edu.unav.vivirsano;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by gfinanciero on 01/02/2017.
 */

public class InstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(getApplicationContext(),  refreshedToken, Toast.LENGTH_SHORT).show();
       // Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        //Toast.makeText(getApplicationContext(),  token, Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "Refreshed token: " + token);
    }

}
