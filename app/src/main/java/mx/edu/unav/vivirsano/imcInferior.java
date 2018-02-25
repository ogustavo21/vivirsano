package mx.edu.unav.vivirsano;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class imcInferior extends AppCompatActivity {

    TextView ResIMC;
    ImageButton enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_inferior);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ResIMC = (TextView) findViewById(R.id.lblimc1);

        Bundle bundle = this.getIntent().getExtras();

        ResIMC.setText( bundle.getString("resultado"));

        enviar= (ImageButton) findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enlace = new Intent(imcInferior.this, home.class);
                startActivity(enlace);
            }
        });

    }
}
