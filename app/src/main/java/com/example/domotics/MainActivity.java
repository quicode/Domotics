package com.example.domotics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;





public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String STRING_PREFERENCES = "example.sism7";
    private static final String PREFERENCE_ESTADO = "estado.button.sesion";
    private static final String PREFERENCE_NOMBRE = "NOMBRE.USER";
    public String Nickname ;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //final TextView mensaje= findViewById(R.id.username);
    //Intent i = this.getIntent();
    //String nombre = i.getStringExtra("nombre");

    //mensaje.setText(mensaje.getText()+" "+nombre+"");
    // Nickname= i.getStringExtra("nombre");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        TextView mensaje= findViewById(R.id.username);
        navigationView.setNavigationItemSelectedListener(this);

        //mensaje.setText(mensaje.getText()+" "+nombre+"");

        //mensaje.setText("hola", TextView.BufferType.SPANNABLE);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState== null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_home()).commit();
            navigationView.setCheckedItem(R.id.home_frag);
        }
    }



    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_home()).commit();
                break;

            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_setting()).commit();
                break;
            case R.id.cam:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_camara()).commit();
                break;
            case R.id.medidores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_medidores()).commit();
                break;
            case R.id.ubicacion:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_ubicacion()).commit();
                break;

            case R.id.log_out:

                //AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                //                //alerta.setMessage("Fallo en el inicio").setNegativeButton("Reintentar", null).create().show();

                Intent logout = new Intent(MainActivity.this, Login.class);

                MainActivity.this.startActivity(logout);
                guardarEstado();
                MainActivity.this.finish();


                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }
    public void guardarEstado () {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCE_ESTADO, false).apply();

    }
    public String obtenerNombre () {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getString(PREFERENCE_NOMBRE,"Default");

    }
}
