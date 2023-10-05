package com.example.app_veterinaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Listar extends AppCompatActivity {

    ListView lvlista;
    private List<String> dataList = new ArrayList<>();
    private List<Integer> dataId = new ArrayList<>();
    private CustomerAdapter adapter;
    private String[] opciones = {"Editar","Cancelar"};
    final String URL = "http://192.168.0.109/veterenaria/controllers/veterinaria.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        load();
        getData();
        lvlista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                showAlertOption(dataId.get(position), position);

            }
        });

    }

    private void getData(){
        dataId.clear();
        dataList.clear();
        adapter = new CustomerAdapter(this, dataList);
        lvlista.setAdapter(adapter);

        //construir una nueva URL(pasar N parametros)
        Uri.Builder URLfull = Uri.parse(URL).buildUpon();
        URLfull.appendQueryParameter("operacion", "listarcliente");
        String URLupdate = URLfull.build().toString();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLupdate, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //cuando manipulemos el resultado JSONArray
                try {
                    //un jsonArray => coleccion de json
                    for(int i = 0; i < response.length(); i++){
                        //Crear un JSONobject por acada elemento recorrido
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        dataList.add(jsonObject.getString("nombres")+ " "+ jsonObject.getString("apellidos")+ " "+ jsonObject.getString("dni"));
                        dataId.add(jsonObject.getInt("idcliente"));
                    }
                    //alertaremos¡ al adaptador indicando que hay cambios
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void showAlertOption(int primaryKey,int positionIndex){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(dataList.get(positionIndex));
        dialogo.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int itemIndex) {
                //3 opciones => 0,1,2
                switch (itemIndex){
                    case 0:
                        //editar
                        Intent intent = new Intent(getApplicationContext(), Editar.class);
                        intent.putExtra("idcliente",primaryKey);
                        startActivity(intent);

                        break;
                    case 1:
                        //eliminar
                        dialogInterface.dismiss();
                        break;
                }
            }
        });
        dialogo.show();
    }

    private void load(){
        lvlista = findViewById(R.id.lvlista);
    }


}