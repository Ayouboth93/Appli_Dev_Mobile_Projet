package com.example.appli_dev_mobile_v2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class HabitatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ListView listView;  // Déclaration globale de listView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat);

        // Configurer la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Configurer le DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Ajouter un bouton "menu burger" pour ouvrir/fermer le menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialisation de la ListView
        listView = findViewById(R.id.listViewHabitats);

        // Récupérer les données depuis la base
        fetchHabitats();

        // Gestion du clic sur un élément de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Habitat habitat = (Habitat) parent.getItemAtPosition(position);
                Toast.makeText(HabitatActivity.this, "Résident : " + habitat.getNom(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Gérer les clics sur les éléments du menu
        int itemId = item.getItemId();

        if (itemId == R.id.nav_habitat) {
            // Ouvrir le fragment ou l'activité "Mon compte"
        } else if (itemId == R.id.nav_habitat) {
            // Ouvrir le fragment ou l'activité "Consommation"
        } else if (itemId == R.id.nav_disconnect) {
            // Fermer l'application
            finish();
        }

        // Fermer le menu déroulant après la sélection
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Fermer le menu déroulant si ouvert
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void fetchHabitats() {
        String url = "http://10.0.2.2/powerhome/getAllData.php"; // Vérifie si c'est bien l'IP correcte

        Ion.with(this)
                .load(url)

                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {


                        if (result == null) {
                            Toast.makeText(HabitatActivity.this, "Réponse vide du serveur", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(HabitatActivity.this,"connexion réussie",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }



}
