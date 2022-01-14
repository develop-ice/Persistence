package com.android.bdroom.ui.screen_to_do;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.bdroom.R;

import java.util.ArrayList;

public class ToDoFragment extends Fragment {

    // UI
    private View rootView;
    private EditText etItem;
    private Button btnAgregar;
    private ListView lvLista;

    // Adapter
    private ArrayList<String> itemList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    public ToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_todo, container, false);

        // inicializar los view en UI
        init();
        // llenar la lista
        setupList();
        // Añadir onClick
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        // Eliminar de la lista
        lvLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Mostrar un Dialogo
                showDialogDelete(position);
            }
        });

        return rootView;
    }

    private void init() {
        etItem = rootView.findViewById(R.id.et_nota);
        btnAgregar = rootView.findViewById(R.id.btn_agregar);
        lvLista = rootView.findViewById(R.id.list_notas);
    }

    private void setupList() {
        itemList = FileHelper.readData(getContext());
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, itemList);
        lvLista.setAdapter(arrayAdapter);
    }

    private void addItem() {
        // Obtener el texto del editText
        String nota = etItem.getText().toString().trim();
        // Validación
        if (!nota.isEmpty()) {
            // Agregar a la lista
            itemList.add(nota);
            // Limpiar el editText
            etItem.setText("");
            // Guardar en el File
            FileHelper.writeData(itemList, getContext());
            // Actualizar el ListView
            arrayAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getContext(), "Nota vacía...", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(final int position) {
        // Setup Alert Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        // Titulo
        dialog.setTitle("Eliminar");
        // Mensaje
        dialog.setMessage("Desea eliminar este item?");
        // Quitar que se cancele si se presiona fuera del dialogo
        dialog.setCancelable(false);
        // Boton negativo
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Boton positivo
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                FileHelper.writeData(itemList, getContext());
            }
        });
        // Crear el Alert Dialog
        AlertDialog alertDialog = dialog.create();
        // Mostrar el dialogo
        alertDialog.show();
    }

}
