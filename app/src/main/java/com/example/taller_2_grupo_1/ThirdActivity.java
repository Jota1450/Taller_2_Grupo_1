package com.example.taller_2_grupo_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    DBControlador controlador;
    private Spinner spinnerServicio;
    EditText lbMedida, lbDireccion;
    TextView tvUnidad;
    Button btGuardar, btCancelar;

    int tipoDServicio;
    int indice;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        spinnerServicio = findViewById(R.id.spinnerServicio);
        lbMedida = findViewById(R.id.lb_second_Medida);
        lbDireccion = findViewById(R.id.lb_second_Servicio);
        tvUnidad = findViewById(R.id.tvMedida);
        btGuardar = findViewById(R.id.btnGuardar);
        btCancelar = findViewById(R.id.btnCancelar);

        controlador = new DBControlador(getApplicationContext());

        //obtenemos el indice del registro y mostramos los datos en pantalla
        Intent i = getIntent();
        indice = i.getIntExtra("indice", 0);

        ArrayList<Servicio> lista = controlador.optenerRegistros();

        Servicio servicio = lista.get(indice);
        id = servicio.getId();

        lbDireccion.setText(servicio.getDireccion());
        lbMedida.setText(Integer.toString(servicio.getMedida()));


        ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
                , R.array.spinner_Servicio, R.layout.support_simple_spinner_dropdown_item);
        spinnerServicio.setAdapter(otroAdacter);
        spinnerServicio.setSelection(servicio.getTipoServicio());
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
                //para guardar instanciamos un Calendar con la fecha y hora actual y creamos un objeto Servicio para despues actualizar
                //el registro dejamos un mensaje le decimos al intent que fue exitosa y finalizamos la actividad si no mostramos mensaje de error
                Calendar calendar = Calendar.getInstance();
                try {
                    int medicion = lbMedida.getText().toString().isEmpty() ? 0 : Integer.parseInt(lbMedida.getText().toString());
                    Servicio servicio = new Servicio(id, lbDireccion.getText().toString(), calendar, medicion, tipoDServicio);
                    int retorno = controlador.actualizarRegistro(servicio);
                    if (retorno == 1) {
                        Toast.makeText(getApplicationContext(), "actualizacion exitosa", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "fallo en la actualizacion", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException nuEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnCancelar:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}