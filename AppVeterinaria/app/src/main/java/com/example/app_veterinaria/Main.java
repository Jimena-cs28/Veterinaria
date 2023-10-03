package com.example.app_veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {
    Button btaccederregistrar,btaccederbuscarcliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        load();

        btaccederbuscarcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(BuscarCliente.class);
            }
        });
        btaccederregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RegisterMascota.class);
            }
        });
    }

    private void openActivity(Class nameActivity){
        Intent intent = new Intent(getApplicationContext(), nameActivity);
        startActivity(intent);
    }
    private void load(){
        btaccederbuscarcliente = findViewById(R.id.btaccederbuscarcliente);
        btaccederregistrar = findViewById(R.id.btaccederregistrar);
    }
}