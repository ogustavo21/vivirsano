package mx.edu.unav.vivirsano;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class imcObesidad extends AppCompatActivity {
    TextView ResIMC4;
    ImageButton enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_obesidad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ResIMC4 = (TextView) findViewById(R.id.lblimc4);

        Bundle bundle = this.getIntent().getExtras();

        ResIMC4.setText(bundle.getString("resultado"));

        enviar= (ImageButton) findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enlace = new Intent(imcObesidad.this, home.class);
                startActivity(enlace);
            }
        });
    }
}
