package com.example.taller_2_grupo_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.example.taller_2_grupo_1.clases.Adaptador;
import com.example.taller_2_grupo_1.clases.Servicio;
import com.example.taller_2_grupo_1.sql.DBControlador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    ListView lista;
    ArrayList<Servicio> listaServicios = new ArrayList<>();
    FloatingActionButton btnAdd;
    DBControlador controlador;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar();
            }
        });
            lista = findViewById(R.id.listadeservicios);
            controlador = new DBControlador(this);
            listaServicios = controlador.optenerRegistros();

            adaptador = new Adaptador(this,listaServicios);
            lista.setAdapter(adaptador);
        Toast.makeText(getApplicationContext(),listaServicios.size()+"", Toast.LENGTH_LONG).show();
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Servicio> listaServis = controlador.optenerRegistros();
                Adaptador aux = new Adaptador(this,listaServis);
                lista.setAdapter(aux);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.Modificar:
                modificarRegistro(menuInfo.position);
                adaptador.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Modificar", Toast.LENGTH_LONG).show();
                return true;
            case R.id.Borrar:
                borrarRegistro(menuInfo.position);
                adaptador.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"Borrar", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void agregar(){
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i);
    }

    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaServicios.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            listaServicios = controlador.optenerRegistros();
            adaptador = new Adaptador(this,listaServicios);
            lista.setAdapter(adaptador);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }
}
