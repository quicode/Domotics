package com.example.domotics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final EditText nombreT = (EditText)findViewById(R.id.nombreRegistro);
        final EditText usuarioT = (EditText)findViewById(R.id.usuarioRegistro);
        final EditText claveT = (EditText)findViewById(R.id.claveRegistro);
        final EditText claveT2=(EditText)findViewById(R.id.claveRegistro2);
        final EditText edadT = (EditText)findViewById(R.id.edadRegistro);
        Button btnRegistro= (Button)findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreT.getText().toString();
                String usuario = usuarioT.getText().toString();
                String clave = claveT.getText().toString();
                String edad = edadT.getText().toString();
                String vpass= claveT.getText().toString();
                String vpass2=claveT2.getText().toString();

                if(edad.equals("")) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                    alerta.setMessage("Existen campos vacios").setNegativeButton("Reintentar", null).create().show();
                }else {
                    if (vpass.equals(vpass2)) {
                        Response.Listener<String> respueta = new Response.Listener<String>() {


                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonRespuesta = new JSONObject(response);
                                    boolean ok = jsonRespuesta.getBoolean("success");
                                    if (ok == true) {
                                        Intent i = new Intent(Registro.this, Login.class);
                                        Registro.this.startActivity(i);
                                        Registro.this.finish();
                                    } else {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                        alerta.setMessage("Fallo en el  registro").setNegativeButton("Reintentar", null).create().show();
                                    }
                                } catch (JSONException e) {
                                    e.getMessage();
                                }
                            }

                        };
                        int edad2 = Integer.parseInt(edadT.getText().toString());
                        RegistroRequest r = new RegistroRequest(nombre, usuario, clave, edad2, respueta);
                        RequestQueue cola = Volley.newRequestQueue(Registro.this);
                        cola.add(r);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                        alerta.setMessage("Contraseña no coincide").setNegativeButton("Reintentar", null).create().show();
                    }
                }
            }
        });
    }
}
