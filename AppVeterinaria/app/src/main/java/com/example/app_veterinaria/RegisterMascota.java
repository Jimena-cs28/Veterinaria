package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RegisterMascota extends AppCompatActivity {

    EditText etnombre, etfoto, etcolor, etgenero, ettamaño, etpeso;
    Button btguardar;
    final String URL = "http://192.168.0.109/veterenaria/controllers/veterinaria.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mascota);
        load();
        btguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarMascota();
            }
        });
    }

    private void registrarMascota(){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Comunicación exitosa
                if (response.equalsIgnoreCase("")){
                    //reiniciar();
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
                parametros.put("operacion", "registrarmascota");
                //parametros.put("idcliente", );
                parametros.put("idraza",etnombre.getText().toString() );
                parametros.put("nombre",etfoto.getText().toString() );
                parametros.put("fotografia", ettamaño.getText().toString());
                parametros.put("color", etpeso.getText().toString());
                parametros.put("genero", etgenero.getText().toString());
                parametros.put("tamaño", etpeso.getText().toString());
                parametros.put("peso", etpeso.getText().toString());
                return parametros;
            }
        };

        //Enviamos la consulta al WS
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void load(){
        btguardar = findViewById(R.id.btguardar);
        //spraza = findViewById(R.id.spraza);
        etnombre = findViewById(R.id.etnombres);
        etfoto = findViewById(R.id.etfoto);
        etcolor = findViewById(R.id.etcolor);
        etgenero = findViewById(R.id.etgenero);
        ettamaño = findViewById(R.id.ettamaño);
        etpeso = findViewById(R.id.etpeso);

    }
}