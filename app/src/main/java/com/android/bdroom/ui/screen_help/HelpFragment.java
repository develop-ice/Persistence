package com.android.bdroom.ui.screen_help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bdroom.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_help, container, false);

        // Obtener el texto de "raw" y setearselo al tvHelp
        TextView tvHelp = rootView.findViewById(R.id.tv_help);
        String str = new String();
        try {
            InputStream textRaw = getResources().openRawResource(R.raw.text);
            BufferedReader br = new BufferedReader(new InputStreamReader(textRaw));
            String linea;
            while ((linea = br.readLine()) != null) {
                str += linea + "\n";
            }
            textRaw.close();
            // set text
            tvHelp.setText("" + str);
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }

        return rootView;
    }

}
