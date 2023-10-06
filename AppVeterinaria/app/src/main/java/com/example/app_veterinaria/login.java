package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class login extends AppCompatActivity {
    Button btaccederRegistroC,btlogin;
    EditText etnombreusuario, etcontraseña;
    int idcliente;
    final String URL = "http://192.168.0.109/Veterinaria/veterenaria/controllers/usuario.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        load();
        btaccederRegistroC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegistrarCliente.class);
            }
        });
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarsesion();
            }
        });
    }
    private void iniciarsesion(){
        if(etnombreusuario.getText().toString().isEmpty()){
            Toast.makeText(this, "Escriba su dni y contraseña", Toast.LENGTH_SHORT).show();
        }else{
            Uri.Builder URLNEW = Uri.parse(URL).buildUpon();
            URLNEW.appendQueryParameter("operacion","login");
            URLNEW.appendQueryParameter("dni", etnombreusuario.getText().toString());
            URLNEW.appendQueryParameter("claveIngresada",etcontraseña.getText().toString());
            String URLUPDATE = URLNEW.build().toString();
            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, URLUPDATE, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            idcliente = response.getInt("idcliente");
                            Toast.makeText(login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Main.class);
                            intent.putExtra("idcliente", idcliente);
                            startActivity(intent);
                        }else {
                            // por si sale
                            Toast.makeText(login.this, response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException error){
                        error.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error volley", error.toString());
                }
            });


            Volley.newRequestQueue(this).add(jsonObjectRequest);
        }
    }
    private void openActivity(Class nameActivity){
        Intent intent = new Intent(getApplicationContext(), nameActivity);
        startActivity(intent);
    }

    private void load(){
        btaccederRegistroC = findViewById(R.id.btaccederRegistroC);
        btlogin = findViewById(R.id.btlogin);
        etnombreusuario = findViewById(R.id.etnombreusu);
        etcontraseña = findViewById(R.id.etcontraseña);
    }

}