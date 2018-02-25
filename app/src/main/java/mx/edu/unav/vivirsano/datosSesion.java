package mx.edu.unav.vivirsano;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class datosSesion extends AppCompatActivity {

    EditText estaura;
    EditText peso;
       SharedPreferences pref;
    Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_sesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        estaura = (EditText) findViewById(R.id.estatura);
        peso = (EditText) findViewById(R.id.peso);

    }



    public void calculo(View view){
        double est = Float.parseFloat(estaura.getText().toString());
        double pes = Float.parseFloat(peso.getText().toString());

        double imc = pes/(est*est);
        String imcenv = String.format( "%.2f", imc );

        if (imc <= 18.5){
            Intent enlace = new Intent(datosSesion.this, imcInferior.class);

            Bundle b = new Bundle();
            b.putString("resultado", String.valueOf(imcenv));

            enlace.putExtras(b);

            startActivity(enlace);
        }
        if (imc > 18.5 && imc <= 24.9){
            Intent enlace = new Intent(datosSesion.this, imcNormal.class);

            Bundle b = new Bundle();
            b.putString("resultado", String.valueOf(imcenv));

            enlace.putExtras(b);

            startActivity(enlace);
        }
        if (imc > 25 && imc <= 29.9){
            Intent enlace = new Intent(datosSesion.this, imcSuperior.class);

            Bundle b = new Bundle();
            b.putString("resultado", String.valueOf(imcenv));

            enlace.putExtras(b);

            startActivity(enlace);
        }
        if (imc > 30){
            Intent enlace = new Intent(datosSesion.this, imcObesidad.class);

            Bundle b = new Bundle();
            b.putString("resultado", String.valueOf(imcenv));

            enlace.putExtras(b);

            startActivity(enlace);
        }


    }
}
