package com.example.taller_2_grupo_1.clases;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taller_2_grupo_1.R;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter {
    Activity context;
    private ArrayList<Servicio> lista;


    public Adaptador(@NonNull Activity context, @NonNull ArrayList<Servicio> lista) {
        super(context, R.layout.list_item ,lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item,null);
        TextView txtId = view.findViewById(R.id.lbID);
        TextView txtFecha = view.findViewById(R.id.lbFecha);
        TextView txtDireccion = view.findViewById(R.id.lbDireccion);
        TextView txtMedida = view.findViewById(R.id.lbMedida);
        TextView txtServicio = view.findViewById(R.id.lbServicio);
        txtId.setText(lista.get(position).getId()+"");
        txtFecha.setText(lista.get(position).getFechaString());
        txtDireccion.setText(lista.get(position).getDireccion()+"");
        switch (lista.get(position).getTipoServicio()) {
            case 0:
                txtServicio.setText("Agua");
                txtMedida.setText(lista.get(position).getMedida()+" m^3");
                break;
            case 1:
                txtServicio.setText("Luz");
                txtMedida.setText(lista.get(position).getMedida()+" kw/h");
                break;
            case 2:
                txtServicio.setText("Gas");
                txtMedida.setText(lista.get(position).getMedida()+" cc");
                break;
        }
        return view;
    }
}
