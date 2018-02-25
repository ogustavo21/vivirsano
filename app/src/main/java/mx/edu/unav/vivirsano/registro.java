package mx.edu.unav.vivirsano;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class registro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nombre;
    EditText edad;
    Spinner sexo;
    Spinner pais;
    Spinner estado;
    EditText ciudad;
    EditText correo;
    EditText pass;
    EditText pass2;

    Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nombre = (EditText) findViewById(R.id.nombre);
        edad = (EditText) findViewById(R.id.edad);
        ciudad = (EditText) findViewById(R.id.ciudad);
        correo = (EditText) findViewById(R.id.correo);
        pass = (EditText) findViewById(R.id.pass);
        pais = (Spinner) findViewById(R.id.pais);
        estado = (Spinner) findViewById(R.id.estado);
        sexo = (Spinner) findViewById(R.id.sexo);
        sexo.setOnItemSelectedListener(this);
        pais.setOnItemSelectedListener(this);
        estado.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.pais, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pais.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.estado, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado.setAdapter(adapter3);


    }

    public void GuardarDatos(View view){
        if (!validate()) {
            onLoginFailed();
            return;
        }

        AsyncHttpClient client=new AsyncHttpClient();
        String url=getString(R.string.url);


        RequestParams requestParams=new RequestParams();
        requestParams.add("servicio", "login");
        requestParams.add("accion", "registrarse");
        requestParams.add("nombre", nombre.getText().toString());
        requestParams.add("age", edad.getText().toString());
        requestParams.add("gender", sexo.getSelectedItem().toString());
        requestParams.add("pais", pais.getSelectedItem().toString());
        requestParams.add("estado", estado.getSelectedItem().toString());
        requestParams.add("ciudad", ciudad.getText().toString());
        requestParams.add("correo", correo.getText().toString());
        requestParams.add("password", pass.getText().toString());
        requestParams.add("token", FirebaseInstanceId.getInstance().getToken());

        RequestHandle post= client.post(url, requestParams, new AsyncHttpResponseHandler() {
            String usuario=null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    try {
                        JSONObject o= new JSONObject(new String(responseBody));
                        usuario=o.getString("login");
                        if (!TextUtils.isEmpty(usuario)){
                            imcApp app=(imcApp) getApplicationContext();
                            app.setUsuario(usuario);
                            startActivity(new Intent(registro.this, MainActivity.class));
                        }else {
                            Crouton.makeText(registro.this, R.string.login_error_json, Style.ALERT).show();
                        }

                    }catch (JSONException e){
                        Crouton.makeText(registro.this, R.string.login_error_request, Style.ALERT).show();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Crouton.makeText(registro.this,  R.string.login_error_internet, Style.ALERT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean validate() {
        boolean valid = true;

        nombre = (EditText) findViewById(R.id.nombre);
        edad = (EditText) findViewById(R.id.edad);
        ciudad = (EditText) findViewById(R.id.ciudad);
        correo = (EditText) findViewById(R.id.correo);
        pass = (EditText) findViewById(R.id.pass);
        pass2 = (EditText) findViewById(R.id.pass2);
        pais = (Spinner) findViewById(R.id.pais);
        estado = (Spinner) findViewById(R.id.estado);
        sexo = (Spinner) findViewById(R.id.sexo);
        sexo.setOnItemSelectedListener(this);
        pais.setOnItemSelectedListener(this);
        estado.setOnItemSelectedListener(this);

       String nombre1=nombre.getText().toString();
        String edad1=edad.getText().toString();
        String sexo1=sexo.getSelectedItem().toString();
        String pais1=pais.getSelectedItem().toString();
        String estado1=estado.getSelectedItem().toString();
        String ciudad1=ciudad.getText().toString();
        String correo1=correo.getText().toString();
        String pass1=pass.getText().toString();
        String pass21=pass2.getText().toString();

        //valida nombre
        if (nombre1.isEmpty()) {
            nombre.setError(getString(R.string.error_registro_nombre));
            valid = false;
        } else {
            nombre.setError(null);
        }
        //valida edad
        if (edad1.isEmpty()) {
            edad.setError(getString(R.string.error_registro_edad));
            valid = false;
        } else {
            edad.setError(null);
        }
        //valida sexo
        if (sexo1.equals("Selecciona")) {
            Crouton.makeText(registro.this, R.string.error_registro_sexo, Style.ALERT).show();
            valid = false;
        } else {

        }
        //valida pais
        if (pais1.equals("Selecciona")) {
            Crouton.makeText(registro.this, R.string.error_registro_pais, Style.ALERT).show();
            valid = false;
        } else {

        }
        //valida estado
        if (estado1.equals("Selecciona")) {
            Crouton.makeText(registro.this, R.string.error_registro_estado, Style.ALERT).show();
            valid = false;
        } else {

        }
        //valida ciudad
        if (ciudad1.isEmpty()) {
            ciudad.setError(getString(R.string.error_registro_ciudad));
            valid = false;
        } else {
            ciudad.setError(null);
        }
        //valida correo
        if (correo1.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo1).matches()) {
            correo.setError(getString(R.string.error_registro_correo));
            valid = false;
        } else {
            correo.setError(null);
        }
        //valida contrasena
        if (pass1.isEmpty() || pass1.length() < 4 || pass1.length() > 10) {
            pass.setError(getString(R.string.error_registro_pass));
            valid = false;
        } else {
            pass.setError(null);
        }
        //valida repite contrasena
        if (pass21.isEmpty() || pass21.length() < 4 || pass21.length() > 10 || !(pass21.equals(pass1))) {
            pass2.setError(getString(R.string.error_registro_pass2));
            valid = false;
        } else {
            pass2.setError(null);
        }


        return valid;
    }

    public void onLoginFailed() {

        regist = (Button) findViewById(R.id.registrar);

        Toast.makeText(getBaseContext(), "Fallo el registro", Toast.LENGTH_LONG).show();

        regist.setEnabled(true);
    }
}
