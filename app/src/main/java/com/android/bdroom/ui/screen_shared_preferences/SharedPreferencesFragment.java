package com.android.bdroom.ui.screen_shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bdroom.R;

public class SharedPreferencesFragment extends Fragment {
    /*
     * Cuando asignes un nombre a tus archivos de preferencias compartidas,
     * este debe ser identificable de forma exclusiva con tu app.
     * Una manera fácil de hacerlo es agregar el nombre del archivo al ID de aplicación.
     * Por ejemplo: "com.example.myapp.PREFERENCE_FILE_KEY"
     */

    // UI
    private View rootView;
    private EditText etName;
    private TextView tvShared;
    private Button btnSave;

    // Referencia SharedPreferences como variable global
    private SharedPreferences prefs;

    public SharedPreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shared_preferences, container, false);

        // init view
        initView();

        // poner el nombre en el textView en caso que ya exista en las preferencias
        sayHi();

        // Guardar nombre en preferencias
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el texto del EditText
                String name = etName.getText().toString();
                // Guardar el nombre en la preferencia
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(getString(R.string.saved_name), name);
                editor.apply();
                // Msg de confirmacion
                Toast.makeText(getContext(), R.string.msg_save, Toast.LENGTH_SHORT).show();
                etName.setText("");
                if (name.isEmpty()) {
                    tvShared.setText(R.string.empty_data);
                } else {
                    tvShared.setText("Hola " + name);
                }
            }
        });

        return rootView;
    }

    private void sayHi() {
        // Si en vez de un Fragment, usamos un Activity, se accede sin poner "rootView.getContext()."
        prefs = rootView.getContext().
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String saved_name = prefs.getString(getString(R.string.saved_name), "");

        if (!saved_name.isEmpty()) {
            tvShared.setText("Hola " + saved_name);
        }
    }

    private void initView() {
        etName = rootView.findViewById(R.id.et_name);
        tvShared = rootView.findViewById(R.id.tv_shared);
        btnSave = rootView.findViewById(R.id.btn_save);
    }
}
