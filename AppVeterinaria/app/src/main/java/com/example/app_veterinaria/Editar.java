package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Editar extends AppCompatActivity {

    Button btactualizar ;
    EditText etapellidosa, etnombresa, etdnia, etclavea;
    int idcliente;

    final String URL = "http://192.168.0.109/veterenaria/controllers/veterinaria.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        load();
        Bundle parametros = this.getIntent().getExtras();
        if (parametros!= null){
            idcliente = parametros.getInt("idcliente");
            getData(idcliente);

        }
        btactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoActualizar(idcliente);
            }
        });
    }

    private void getData (int idcliente){
        Uri.Builder URLFull = Uri.parse(URL).buildUpon();
        URLFull.appendQueryParameter("operacion", "getData");
        URLFull.appendQueryParameter("idcliente", String.valueOf(idcliente));
        String URLUpdate = URLFull.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLUpdate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    etnombresa.setText(jsonObject.getString("nombres"));
                    etapellidosa.setText(jsonObject.getString("apellidos"));
                    etdnia.setText(jsonObject.getString("dni"));
                    etclavea.setText(jsonObject.getString("claveacceso"));
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void actualizarColaboradores(int idcliente){
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("")){
                    //reiniciar();
                    Toast.makeText(getApplicationContext(), "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error en Comunicacion", error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("operacion","actualizar");
                parametros.put("idcliente", String.valueOf(idcliente));
                parametros.put("apellidos", etapellidosa.getText().toString());
                parametros.put("nombres", etnombresa.getText().toString());
                parametros.put("dni", etdnia.getText().toString());
                parametros.put("claveacceso", etclavea.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void mostrarDialogoActualizar(int idcliente){
        AlertDialog.Builder dialogo =  new AlertDialog.Builder(this);
        dialogo.setTitle("Productos");
        dialogo.setMessage("¿Está seguro de Actualizar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Enviar los datos utilizando Volley
                actualizarColaboradores(idcliente);
            }
        });
        dialogo.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        //Mostrar Dialogo
        dialogo.show();
    }
    private void load(){
        btactualizar = findViewById(R.id.btactualizar);
        etapellidosa = findViewById(R.id.etapellidosa);
        etnombresa = findViewById(R.id.etnombresa);
        etdnia = findViewById(R.id.etdnia);
        etclavea = findViewById(R.id.etclavea);
    }
}