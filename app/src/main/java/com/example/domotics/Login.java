package com.example.domotics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private RadioButton sesion;
    private boolean isActivate;
    private static final String STRING_PREFERENCES = "example.sism7";
    private static final String PREFERENCE_ESTADO = "estado.button.sesion";
    private static final String PREFERENCE_NOMBRE = "NOMBRE.USER";

    private EditText usuarioT;
    private EditText claveT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (obtenerEstado()) {

            Intent main = new Intent(Login.this, MainActivity.class);
            main.putExtra("nombre", obtenerNombre());
            Login.this.startActivity(main);
            Login.this.finish();
        }

        TextView registro = (TextView) findViewById(R.id.registroLogin);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        sesion = (RadioButton) findViewById(R.id.radioButton);
        usuarioT = (EditText) findViewById(R.id.usuarioLogin);
        claveT = (EditText) findViewById(R.id.claveLogin);
        //final EditText usuarioT = (EditText)findViewById(R.id.usuarioLogin);
        //final EditText claveT   = (EditText)findViewById(R.id.claveLogin) ;

        isActivate = sesion.isChecked();


        sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isActivate) {
                    sesion.setChecked(false);
                }
                isActivate = sesion.isChecked();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(Login.this, Registro.class);
                Login.this.startActivity(registro);
                /* finish(); */
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //valida();
                final String usuario = usuarioT.getText().toString();
                final String clave = claveT.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if (ok == true) {
                                String nombre = jsonRespuesta.getString("nombre");
                                guardarNombre(nombre);
                                int edad = jsonRespuesta.getInt("edad");
                                Intent MainActivity = new Intent(Login.this, MainActivity.class);
                                MainActivity.putExtra("nombre", nombre);
                                MainActivity.putExtra("edad", edad);
                                Login.this.startActivity(MainActivity);
                                guardarEstado();
                                Login.this.finish();
                            }else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Usuario y/o contraseña invalida").setNegativeButton("Reintentar", null).create().show();
                            }
                        }catch (JSONException e) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                            alerta.setMessage("Sin conexion con el servidor").setNegativeButton("Reintentar", null).create().show();
                            e.getMessage();
                        }
                    }
                    //termina onResponse
                };
                LoginRequest r = new LoginRequest(usuario, clave, respuesta);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);
            }
        });




    /*
    public void valida(){
        final String usuario = usuarioT.getText().toString();
        final String clave = claveT.getText().toString();

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonRespuesta = new JSONObject(response);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok==true){
                        String nombre= jsonRespuesta.getString("nombre");
                        int edad= jsonRespuesta.getInt("edad");

                        Intent bienvenido = new Intent(Login.this,Bienvenido.class);
                        bienvenido.putExtra("nombre",nombre);
                        bienvenido.putExtra("edad",edad);
                        Login.this.startActivity(bienvenido);
                        guardarEstado();
                        Login.this.finish();
                    }else{
                        AlertDialog.Builder alerta= new AlertDialog.Builder(Login.this);
                        alerta.setMessage("Fallo en el inicio").setNegativeButton("Reintentar",null).create().show();
                    }
                }catch (JSONException e){

                    e.getMessage();
                }
            }
        };
        LoginRequest r = new LoginRequest(usuario,clave,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Login.this);
        cola.add(r);


    }




    }

     */


    }
    public void guardarEstado () {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO, sesion.isChecked()).apply();

    }

    public void guardarNombre(String nombre){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putString(PREFERENCE_NOMBRE,nombre).apply();
    }


    public String obtenerNombre () {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_NOMBRE,"Default");

    }
    public boolean obtenerEstado () {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO, false);

    }

}
