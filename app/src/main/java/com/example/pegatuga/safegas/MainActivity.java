package com.example.pegatuga.safegas;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.telecom.Call;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText money,magnav,diseln,premiumr;
    private TextView result;
    private Button calcular;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //variables
        money = (EditText) findViewById(R.id.dinero);
        magnav = (EditText) findViewById(R.id.MagnaV);
        diseln = (EditText) findViewById(R.id.DiselN);
        premiumr = (EditText) findViewById(R.id.PremiumR);
        result = (TextView) findViewById(R.id.Resultado);
        //boton que manda lllamr el metodo de la operacion
        calcular = (Button) findViewById(R.id.btnCalcular);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dividir();
            }
        });

        TableroFragment tableroFragment = new TableroFragment();
        fragmentManager.beginTransaction().replace(R.id.contenedor, tableroFragment, tableroFragment.getTag()).commit();

//boton amarrillo para reportar la gasolinera
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            //aqui se pone el numero que marca el boton
            public void onClick(View view) {
                CALL("01 800 468 8722");
            }
            //metodo para el boton de llamar
            private void CALL(final String s) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",s,null)));
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
//metodo que lleva la operacion
    private void dividir() {
        double monto = Double.parseDouble(money.getText().toString());
        double precio = Double.parseDouble(magnav.getText().toString());
        double operacion =  monto/precio;
        result.setText(String.valueOf(operacion));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {


        }else if (id == R.id.nav_tablero) {
            TableroFragment tableroFragment = new TableroFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, tableroFragment, tableroFragment.getTag()).commit();
        } else if (id == R.id.nav_car) {

        } else if (id == R.id.nav_map) {
            MapFragment mapFragment = new MapFragment();
            fragmentManager.beginTransaction().replace(R.id.contenedor, mapFragment, mapFragment.getTag()).commit();
        } else if (id == R.id.nav_fuel) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
