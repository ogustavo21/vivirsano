package mx.edu.unav.vivirsano;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragmentH32 extends Fragment implements View.OnClickListener {
    private interfaz3 mCallback;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragmenth3_2, container, false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (interfaz3) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement mx.edu.unav.vivirsano.IFragmentToActivity");
        }
    }


    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
              //  mCallback.showToast("Hello from Fragment 2");
                break;
        }
    }
}
