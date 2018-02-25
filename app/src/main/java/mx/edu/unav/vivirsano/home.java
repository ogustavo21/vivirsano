package mx.edu.unav.vivirsano;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView p1,p2,p3,p4,p5,p6,p7,p8;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        porcentajes();

    }


    public void iniciarReto(View v){
        startActivity(new Intent(home.this, MainActivityH1.class));

    }
    public void iniciarReto2(View v){
        startActivity(new Intent(home.this, MainActivityH2.class));

    }
    public void iniciarReto3(View v){
        startActivity(new Intent(home.this, MainActivityH3.class));

    }
    public void iniciarReto4(View v){
        startActivity(new Intent(home.this, MainActivityH4.class));

    }
    public void iniciarReto5(View v){
        startActivity(new Intent(home.this, MainActivityH5.class));

    }
    public void iniciarReto6(View v){
        startActivity(new Intent(home.this, MainActivityH6.class));

    }
    public void iniciarReto7(View v){
        startActivity(new Intent(home.this, MainActivityH7.class));

    }
    public void iniciarReto8(View v){
        startActivity(new Intent(home.this, MainActivityH8.class));

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(home.this, datosSesion.class));
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.habito1) {
            // Handle the camera action
            startActivity(new Intent(home.this, MainActivityH1.class));
        } else if (id == R.id.habito2) {
            startActivity(new Intent(home.this, MainActivityH2.class));
        } else if (id == R.id.habito3) {
            startActivity(new Intent(home.this, MainActivityH3.class));

        } else if (id == R.id.habito4) {
            startActivity(new Intent(home.this, MainActivityH4.class));

        } else if (id == R.id.habito5) {
            startActivity(new Intent(home.this, MainActivityH5.class));

        } else if (id == R.id.habito6) {
            startActivity(new Intent(home.this, MainActivityH6.class));

        } else if (id == R.id.habito7) {
            startActivity(new Intent(home.this, MainActivityH7.class));

        } else if (id == R.id.habito8) {
            startActivity(new Intent(home.this, MainActivityH8.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.salir) {
            logout();
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        SharedPreferences prefe = getSharedPreferences("Mipref", Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = prefe.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(home.this, MainActivity.class));
    }

    public void porcentajes(){
        p1 = (TextView)findViewById(R.id.porcentaje1);
        p2 = (TextView)findViewById(R.id.porcentaje2);
        p3 = (TextView)findViewById(R.id.porcentaje3);
        p4 = (TextView)findViewById(R.id.porcentaje4);
        p5 = (TextView)findViewById(R.id.porcentaje5);
        p6 = (TextView)findViewById(R.id.porcentaje6);
        p7 = (TextView)findViewById(R.id.porcentaje7);
        p8 = (TextView)findViewById(R.id.porcentaje8);

        pref = getApplicationContext().getSharedPreferences("Mipref", 0);
        String correo = pref.getString("session", "");

        AsyncHttpClient client = new AsyncHttpClient();
        String url = getString(R.string.url);

        RequestParams requestParams = new RequestParams();
        requestParams.add("servicio", "habitos");
        requestParams.add("accion", "porcentajes");
        requestParams.add("correo", correo);

        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            String usuario = null;
            String porcentaje1=null;
            String porcentaje2=null;
            String porcentaje3=null;
            String porcentaje4=null;
            String porcentaje5=null;
            String porcentaje6=null;
            String porcentaje7=null;
            String porcentaje8=null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject o = new JSONObject(new String(responseBody));
                        usuario = o.getString("login");
                        porcentaje1 = o.getString("jp1");
                        porcentaje2 = o.getString("jp2");
                        porcentaje3 = o.getString("jp3");
                        porcentaje4 = o.getString("jp4");
                        porcentaje5 = o.getString("jp5");
                        porcentaje6 = o.getString("jp6");
                        porcentaje7 = o.getString("jp7");
                        porcentaje8 = o.getString("jp8");


                        if (!TextUtils.isEmpty(usuario)) {
                            imcApp app = (imcApp) getApplicationContext();
                            app.setUsuario(usuario);
                             p1.setText(porcentaje1);
                            p2.setText(porcentaje2);
                            p3.setText(porcentaje3);
                            p4.setText(porcentaje4);
                            p5.setText(porcentaje5);
                            p6.setText(porcentaje6);
                            p7.setText(porcentaje7);
                            p8.setText(porcentaje8);

                           // Toast.makeText(getApplicationContext(), porcentaje1, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),  R.string.habitos_error_guardar, Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),  R.string.habitos_error_request, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(),  R.string.habitos_error_internet, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
