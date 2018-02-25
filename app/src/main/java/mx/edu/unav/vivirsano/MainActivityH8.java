package mx.edu.unav.vivirsano;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivityH8 extends AppCompatActivity implements interfaz8, NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MainActivity";
    private PagerAdapter8 adapter;
    private TabLayout tabLayout;
    ////// los checkbox
    private CheckBox domF, lunF, marF, mierF, jueF, vieF, sabF, cadadia;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhocho);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ////back
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewh8);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /////

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Habito 8");
        tabs.add("Material");
        tabs.add("Actividad");
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter8(getSupportFragmentManager(), tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            int position = tabLayout.getSelectedTabPosition();
            Fragment fragment = adapter.getFragment(tabLayout
                    .getSelectedTabPosition());
            if (fragment != null) {
                switch (position) {
                    case 0:
                        ((TabFragmentH81) fragment).onRefresh();
                        break;
                    case 1:
                        ((TabFragmentH82) fragment).onRefresh();
                        break;
                    case 3:
                        ((TabFragmentH83) fragment).onRefresh();
                        break;
                }
            }
            startActivity(new Intent(MainActivityH8.this, datosSesion.class));
            return true;
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
            startActivity(new Intent(MainActivityH8.this, MainActivityH1.class));
        } else if (id == R.id.habito2) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH2.class));

        } else if (id == R.id.habito3) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH3.class));

        } else if (id == R.id.habito4) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH4.class));

        } else if (id == R.id.habito5) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH5.class));

        } else if (id == R.id.habito6) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH6.class));

        } else if (id == R.id.habito7) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH7.class));


        } else if (id == R.id.habito8) {
            startActivity(new Intent(MainActivityH8.this, MainActivityH8.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.salir) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout8);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        SharedPreferences prefe = getSharedPreferences("Mipref", Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = prefe.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(MainActivityH8.this, MainActivity.class));
    }


    public void guardarh8() {
        domF = (CheckBox)findViewById(R.id.domF);
        lunF = (CheckBox)findViewById(R.id.LunF);
        marF = (CheckBox)findViewById(R.id.MarF);
        mierF = (CheckBox)findViewById(R.id.MiF);
        jueF = (CheckBox)findViewById(R.id.JuF);
        vieF = (CheckBox)findViewById(R.id.ViF);
        sabF = (CheckBox)findViewById(R.id.SaF);
        cadadia = (CheckBox)findViewById(R.id.cada);

        String vdF = "0", vlF = "0", vmF = "0", vmiF = "0", vjF = "0", vvF = "0", vsF = "0", c = "0";

        if (domF.isChecked()){ vdF = "1"; }
        if (lunF.isChecked()){ vlF = "1"; }
        if (marF.isChecked()){ vmF = "1"; }
        if (mierF.isChecked()){ vmiF = "1"; }
        if (jueF.isChecked()){ vjF = "1"; }
        if (vieF.isChecked()){ vvF = "1"; }
        if (sabF.isChecked()){ vsF = "1"; }
        if (cadadia.isChecked()){ c = "1"; }


        pref = getApplicationContext().getSharedPreferences("Mipref", 0);
        String correo = pref.getString("session", "");

        AsyncHttpClient client = new AsyncHttpClient();
        String url = getString(R.string.url);

        RequestParams requestParams = new RequestParams();
        requestParams.add("servicio", "habitos");
        requestParams.add("accion", "insertahabito");
        requestParams.add("correo", correo);
        requestParams.add("domingo", vdF);
        requestParams.add("lunes", vlF);
        requestParams.add("martes", vmF);
        requestParams.add("miercoles", vmiF);
        requestParams.add("jueves", vjF);
        requestParams.add("viernes", vvF);
        requestParams.add("sabado", vsF);
        requestParams.add("cadadia", c);
        requestParams.add("habito", String.valueOf(8));


        RequestHandle post = client.post(url, requestParams, new AsyncHttpResponseHandler() {
            String usuario = null;

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        JSONObject o = new JSONObject(new String(responseBody));
                        usuario = o.getString("login");

                        if (!TextUtils.isEmpty(usuario)) {
                            imcApp app = (imcApp) getApplicationContext();
                            app.setUsuario(usuario);
                            Toast.makeText(getApplicationContext(), R.string.mensaje_guardar, Toast.LENGTH_SHORT).show();
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


