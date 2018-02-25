package mx.edu.unav.vivirsano;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TabFragmentH23 extends Fragment implements View.OnClickListener {
    private IFragmentToActivity mCallback;
    private Button btnh2;

    ////// los checkbox
    private CheckBox domF, lunF, marF, mierF, jueF, vieF, sabF, cada;
    public static SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragmenth2_3, container, false);
        btnh2 = (Button) view.findViewById(R.id.buttonh2);
        btnh2.setOnClickListener(this);

        ////check
        domF = (CheckBox) view.findViewById(R.id.domF);
        lunF = (CheckBox) view.findViewById(R.id.LunF);
        marF = (CheckBox) view.findViewById(R.id.MarF);
        mierF = (CheckBox)view.findViewById(R.id.MiF);
        jueF = (CheckBox)view.findViewById(R.id.JuF);
        vieF = (CheckBox)view.findViewById(R.id.ViF);
        sabF = (CheckBox)view.findViewById(R.id.SaF);
        cada = (CheckBox)view.findViewById(R.id.cada);

        pref = getActivity().getApplicationContext().getSharedPreferences("Mipref", 0);
        String correo = pref.getString("session", "");


        AsyncHttpClient client=new AsyncHttpClient();
        String url=getString(R.string.url);

        RequestParams requestParams=new RequestParams();
        requestParams.add("servicio", "habitos");
        requestParams.add("accion", "verificar");
        requestParams.add("correo", correo);
        requestParams.add("habito", String.valueOf(2));


        RequestHandle post= client.post(url, requestParams, new AsyncHttpResponseHandler() {
            String usuario=null;
            String habito=null;
            String domingo=null;
            String lunes=null;
            String martes=null;
            String miercoles=null;
            String jueves=null;
            String viernes=null;
            String sabado=null;
            String cadadia=null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    try {
                        JSONObject o= new JSONObject(new String(responseBody));
                        usuario=o.getString("login");
                        habito=o.getString("habito");
                        domingo=o.getString("domingo");
                        lunes=o.getString("lunes");
                        martes=o.getString("martes");
                        miercoles=o.getString("miercoles");
                        jueves=o.getString("jueves");
                        viernes=o.getString("viernes");
                        sabado=o.getString("sabado");
                        cadadia=o.getString("cadadia");
                        if (!TextUtils.isEmpty(usuario)){
                            imcApp app=(imcApp) getActivity().getApplicationContext();
                            app.setUsuario(usuario);
                            //Toast.makeText(getActivity().getApplicationContext(), "Carga de Habito8 ", Toast.LENGTH_SHORT).show();
                            if (domingo.equals("1")) { domF.setChecked(true); }
                            if (lunes.equals("1") ) { lunF.setChecked(true); }
                            if (martes.equals("1") ) { marF.setChecked(true); }
                            if (miercoles.equals("1")) { mierF.setChecked(true); }
                            if (jueves.equals("1") ) { jueF.setChecked(true); }
                            if (viernes.equals("1") ) { vieF.setChecked(true); }
                            if (sabado.equals("1") ) { sabF.setChecked(true); }
                            if (cadadia.equals("1") ) { cada.setChecked(true); }

                        }else {
                            Toast.makeText(getActivity().getApplicationContext(),  R.string.habitos_error_json, Toast.LENGTH_SHORT).show();
                        }

                    }catch (JSONException e){
                        Toast.makeText(getActivity().getApplicationContext(),  R.string.habitos_error_request, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity().getApplicationContext(),  R.string.habitos_error_internet, Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IFragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement mx.edu.unav.vivirsano.IFragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public void onRefresh() {

    }

    public void fragmentCommunication() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonh2:
               // mCallback.showToast("Hello from Fragment 2");
                mCallback.guardarh2();
                break;
        }
    }



}
