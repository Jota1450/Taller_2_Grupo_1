package com.example.taller_2_grupo_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.taller_2_grupo_1.clases.Servicio;
import com.example.taller_2_grupo_1.sql.DBControlador;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    DBControlador controlador;
    private Spinner spinnerServicio;
    EditText lbMedida, lbDireccion;
    TextView tvUnidad;
    Button btGuardar, btCancelar;
    int tipoDServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        spinnerServicio = findViewById(R.id.spinnerServicio);
        lbMedida = findViewById(R.id.lb_second_Medida);
        lbDireccion = findViewById(R.id.lb_second_Servicio);
        tvUnidad = findViewById(R.id.tvMedida);
        btGuardar = findViewById(R.id.btnGuardar);
        btCancelar = findViewById(R.id.btnCancelar);

        controlador = new DBControlador(getApplicationContext());

        //ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
        //        , R.array.spinner_Servicio, R.layout.support_simple_spinner_dropdown_item);
        //spinnerServicio.setAdapter(otroAdacter);

        String g  [] = { "Agua","Luz","Gas"};
        ArrayAdapter<String> g2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,g);
        spinnerServicio.setAdapter(g2);
        spinnerServicio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDServicio = position;
                switch (tipoDServicio) {
                    case 0:
                        tvUnidad.setText("m^3");
                        break;
                    case 1:
                        tvUnidad.setText("kw/h");
                        break;
                    case 2:
                        tvUnidad.setText("cc");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btGuardar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardar:
                Calendar calendar = Calendar.getInstance();
                try {
                    int medicion = lbMedida.getText().toString().isEmpty() ? 0 : Integer.parseInt(lbMedida.getText().toString());
                    Servicio servicio = new Servicio(lbDireccion.getText().toString(), calendar, medicion, tipoDServicio);
                    long retorno = controlador.agregarRegistro(servicio);
                    if (retorno != -1) {
                        Toast.makeText(v.getContext(), "registro guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                    }
                    limpiarCampo();
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancelar:
                limpiarCampo();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Listado) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        lbMedida.setText("");
        lbDireccion.setText("");
    }

}
