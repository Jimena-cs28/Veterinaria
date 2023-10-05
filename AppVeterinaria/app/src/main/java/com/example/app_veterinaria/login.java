package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    Button btaccederRegistroC,btlogin;
    EditText etnombreusuario, etcontraseña;
    final String URL = "http://192.168.0.109/veterenaria/controllers/usuario.php";
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
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Validar las credenciales (esto es un ejemplo simple, en una aplicación real deberías verificar con una base de datos)
                            if (etnombreusuario.equals("dni") && etcontraseña.equals("claveacceso")) {
                                // Credenciales correctas, mostrar un mensaje de éxito
                                Toast.makeText(login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                openActivity(Main.class);
                                // Aquí podrías abrir la siguiente actividad o realizar otras acciones después del inicio de sesión
                            }else {
                                // Credenciales incorrectas, mostrar un mensaje de error
                                Toast.makeText(login.this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // notificar(error.toString());
                        }
                    }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("operacion", "login");
                    parametros.put("dni",etnombreusuario.getText().toString());
                    parametros.put("claveIngresada", etcontraseña.getText().toString());
                    return parametros;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
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