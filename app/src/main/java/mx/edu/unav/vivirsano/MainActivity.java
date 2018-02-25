    package mx.edu.unav.vivirsano;

    import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
    import android.content.pm.ActivityInfo;
    import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

    public class MainActivity extends AppCompatActivity {
        ImageView logo1;
        private LoginButton loginButton;
        private CallbackManager callbackManager;
        private static final String LOGTAG = "Mislogs";
//para login
        EditText nombre, password;
        Button button;
///
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            nombre=(EditText) findViewById(R.id.nombre);
            password=(EditText) findViewById(R.id.password);
            loginButton = (LoginButton) findViewById(R.id.login_button);
            callbackManager = CallbackManager.Factory.create();

            if (AccessToken.getCurrentAccessToken() == null) {
                //goLoginScreen();
              //  Log.e(LOGTAG, "el acceso es null");
            }else{

                 }
            verificaPreferencia();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                 GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String profile_name=object.getString("name");
                                    String id=object.getString("id");
                                    String nombre=object.getString("name");


                                    ////
                                    AsyncHttpClient client=new AsyncHttpClient();

                                    String url=getString(R.string.url);

                                    RequestParams requestParams=new RequestParams();
                                    requestParams.add("servicio", "login");
                                    requestParams.add("accion", "accesofacebook");
                                    requestParams.add("id", id);
                                    requestParams.add("nombre", nombre);
                                    requestParams.add("token", FirebaseInstanceId.getInstance().getToken());



                                    RequestHandle post= client.post(url, requestParams, new AsyncHttpResponseHandler() {
                                        String usuario=null;
                                        String success=null;
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            if (statusCode==200){
                                                try {
                                                    JSONObject o= new JSONObject(new String(responseBody));
                                                    success=o.getString("success");
                                                    usuario=o.getString("login");
                                                    if (!TextUtils.isEmpty(success)){
                                                        imcApp app=(imcApp) getApplicationContext();
                                                        app.setUsuario(usuario);

/////gerenara preferencia
                                                        SharedPreferences prefe = getSharedPreferences("Mipref", Context.MODE_PRIVATE); // 0 - for private mode
                                                        SharedPreferences.Editor editor = prefe.edit();
                                                        editor.putString("session", usuario);
                                                        editor.commit();
                                                        startActivity(new Intent(MainActivity.this, recorrido1.class));

                                                    }else {
                                                        Crouton.makeText(MainActivity.this, R.string.login_error_json, Style.ALERT).show();
                                                    }

                                                }catch (JSONException e){
                                                    Crouton.makeText(MainActivity.this, R.string.login_error_request, Style.ALERT).show();
                                                }

                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            Crouton.makeText(MainActivity.this, R.string.login_error_internet, Style.ALERT).show();
                                        }
                                    });
                                  ////////////////////////////////
                                    //Start new activity or use this info in your project.
                                    /*Intent i=new Intent(MainActivity.this, home.class);
                                    Bundle b=new Bundle();
                                    b.putString("profile_image",profile_name);
                                    i.putExtras(b);
                                    startActivity(i);
                                    finish();*/
                                    Toast.makeText(getApplicationContext(),  nombre, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    //  e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }


        public void ValidarOnClick(View v){

            if (!validate()) {
                onLoginFailed();
                return;
            }
            AsyncHttpClient client=new AsyncHttpClient();
            String url=getString(R.string.url);

            RequestParams requestParams=new RequestParams();
            requestParams.add("servicio", "login");
            requestParams.add("accion", "acceso");
            requestParams.add("correo", nombre.getText().toString());
            requestParams.add("password", password.getText().toString());

            RequestHandle post= client.post(url, requestParams, new AsyncHttpResponseHandler() {
                String usuario=null;
                String success=null;
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode==200){
                        try {
                            JSONObject o= new JSONObject(new String(responseBody));
                            success=o.getString("success");
                            usuario=o.getString("login");
                            if (!TextUtils.isEmpty(success)){
                                imcApp app=(imcApp) getApplicationContext();
                                app.setUsuario(usuario);

/////gerenara preferencia
                                SharedPreferences prefe = getSharedPreferences("Mipref", Context.MODE_PRIVATE); // 0 - for private mode
                                SharedPreferences.Editor editor = prefe.edit();
                                editor.putString("session", usuario);
                                Toast.makeText(getBaseContext(), usuario, Toast.LENGTH_LONG).show();
                                editor.commit();

                                startActivity(new Intent(MainActivity.this, recorrido1.class));

                            }else {
                                Crouton.makeText(MainActivity.this, R.string.login_error_json, Style.ALERT).show();
                            }

                        }catch (JSONException e){
                            Crouton.makeText(MainActivity.this, R.string.login_error_request, Style.ALERT).show();
                        }

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Crouton.makeText(MainActivity.this, R.string.login_error_internet, Style.ALERT).show();
                }
            });
        }



        public void verificaPreferencia(){
            SharedPreferences prefe = getSharedPreferences("Mipref", Context.MODE_PRIVATE);
            String usuario=prefe.getString("session", "");
            Crouton.makeText(MainActivity.this, usuario, Style.ALERT).show();
            if (usuario!=""){
                Intent i=new Intent(MainActivity.this,  home.class);
                startActivity(i);
                finish();
            }
        }

        public void registro(View view) {
            startActivity(new Intent(MainActivity.this, registro.class));
        }

        public boolean validate() {
            boolean valid = true;

            nombre=(EditText) findViewById(R.id.nombre);
            password=(EditText) findViewById(R.id.password);

            String email = nombre.getText().toString();
            String password1 = password.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                nombre.setError(getBaseContext().getResources().getString(R.string.login_email));
                valid = false;
            } else {
                nombre.setError(null);
            }

            if (password1.isEmpty() || password1.length() < 4 || password1.length() > 10) {
                password.setError(getBaseContext().getResources().getString(R.string.login_contrasena));
                valid = false;
            } else {
                password.setError(null);
            }

            return valid;
        }

        public void onLoginFailed() {

            button = (Button) findViewById(R.id.button2);

            Toast.makeText(getBaseContext(), R.string.fallo_sesion, Toast.LENGTH_LONG).show();

            button.setEnabled(true);
        }
        private void goLoginScreen() {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        public void logout(View view) {
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }
    }

