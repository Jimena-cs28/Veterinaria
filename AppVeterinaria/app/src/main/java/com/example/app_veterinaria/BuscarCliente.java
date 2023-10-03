package com.example.app_veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BuscarCliente extends AppCompatActivity {

    ImageView  ivfotoanimal;
    Button btbuscarB;
    EditText etdni, etApellidosB, etNombresB, etdniB, etAnimalB, etrazaB, etnombresB, etcolorB, etgeneroB, etpesoB;
    final String URL = "http://192.168.0.109/veterenaria/controllers/veterinaria.php";
    final String URL_images = "http://192.168.0.109/veterenaria/img/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);
        load();
        btbuscarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarcliente();
            }
        });
    }

    private void buscarcliente(){
        if(etdniB.getText().toString().isEmpty()){
            Toast.makeText(this, "Escriba un DNI", Toast.LENGTH_SHORT).show();
        }else{
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                //convierte la cadena en un json
                                JSONArray jsonArray = new JSONArray(response);
                                if (jsonArray.length()>0){
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    etApellidosB.setText(jsonObject.getString("apellidos"));
                                    etNombresB.setText(jsonObject.getString("nombres"));
                                    etdni.setText(jsonObject.getString("dni"));
                                    etAnimalB.setText(jsonObject.getString("nombreanimal"));
                                    etrazaB.setText(jsonObject.getString("nombreraza"));
                                    etnombresB.setText(jsonObject.getString("nombre"));
                                    etcolorB.setText(jsonObject.getString("color"));
                                    etgeneroB.setText(jsonObject.getString("genero"));
                                    etpesoB.setText(jsonObject.getString("peso"));

                                    loadImage(jsonObject.getString("fotografia"));
                                }else{
                                    notificar("no encontramos");
                                }
                                //pendiente... cargar la imagen
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            notificar(error.toString());
                        }
                    }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("operacion", "buscarcliente");
                    parametros.put("dni", etdniB.getText().toString());
                    return parametros;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    private void loadImage(String imagen){
        //Uri si = Uri.parse(URL_images+imagen);
        //String URL_verdadero = si.toString();

        String URL_2 = URL_images+ imagen;

        ImageRequest imageRequest = new ImageRequest(
                URL_2,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //voley le responde y se pone responser
                        ivfotoanimal.setImageBitmap(response);
                    }
                }, //los ceros trae la iamgen tal como estan
                0,
                0,
                null,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        notificar(error.toString());
                    }
                }
        );
        Volley.newRequestQueue(this).add(imageRequest);

        //ivplato.setImageBitmap();
    }

    private void load(){
        btbuscarB = findViewById(R.id.btBuscar);
        etdniB = findViewById(R.id.etdni);
        etdni = findViewById(R.id.etdniB);
        etApellidosB = findViewById(R.id.etapellidosB);
        etNombresB = findViewById(R.id.etnombreB);
        etAnimalB = findViewById(R.id.etanimalB);
        etrazaB = findViewById(R.id.etrazaB);
        etnombresB = findViewById(R.id.etnombreanimalB);
        etcolorB = findViewById(R.id.etcolorB);
        etgeneroB = findViewById(R.id.etgeneroB);
        etpesoB = findViewById(R.id.etpesoB);
        ivfotoanimal = findViewById(R.id.ivfotoanimalB);
    }
    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}