package mx.edu.unav.vivirsano;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class imcNormal extends AppCompatActivity {

    TextView ResIMC2;
    ImageButton enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_normal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ResIMC2 = (TextView) findViewById(R.id.lblimc2);

        Bundle bundle = this.getIntent().getExtras();

        ResIMC2.setText(bundle.getString("resultado"));

        enviar= (ImageButton) findViewById(R.id.enviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enlace = new Intent(imcNormal.this, home.class);
                startActivity(enlace);
            }
        });
    }
}
