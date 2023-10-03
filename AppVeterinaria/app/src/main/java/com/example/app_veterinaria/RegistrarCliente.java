package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistrarCliente extends AppCompatActivity {

    Button btregistrarcliene;
    EditText etapellidos, etnombres, etdni, etclave;

    final String URL = "http://192.168.0.109/veterenaria/controllers/veterinaria.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        load();
        btregistrarcliene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoRegistro();
            }
        });
    }
    private void registrarCliente(){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Comunicación exitosa
                if (response.equalsIgnoreCase("")){
                    reiniciar();
                    Toast.makeText(getApplicationContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //No funcionó la comunicación con el WS
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("operacion", "registrarcliente");
                parametros.put("apellidos",etapellidos.getText().toString() );
                parametros.put("nombres",etnombres.getText().toString() );
                parametros.put("dni", etdni.getText().toString());
                parametros.put("claveacceso", etclave.getText().toString());
                return parametros;
            }
        };

        //Enviamos la consulta al WS
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void load(){
        btregistrarcliene = findViewById(R.id.btregistrarcliene);
        etapellidos = findViewById(R.id.etapellidos);
        etnombres = findViewById(R.id.etnombres);
        etclave = findViewById(R.id.etclave);
        etdni = findViewById(R.id.etdni);
    }

    private void mostrarDialogoRegistro(){
        //Construcción y configuración
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Está seguro de registrar?");
        dialogo.setCancelable(false);

        //Definir ACEPTAR / CANCELAR
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Enviar los datos utilizando Volley
                registrarCliente();
            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //Mostrar diálogo
        dialogo.show();
    }

    private void reiniciar(){
        etdni.setText("");
        etnombres.setText("");
        etapellidos.setText("");
        etclave.setText("");
    }
}