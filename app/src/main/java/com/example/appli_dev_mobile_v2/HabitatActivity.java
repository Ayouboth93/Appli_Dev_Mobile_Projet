package com.example.appli_dev_mobile_v2;

import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

public class HabitatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

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

        // Initialisation de la ListView et des données
        ListView listView = findViewById(R.id.listViewHabitats);
        List<Habitat> habitants = new ArrayList<>();

        // Exemple pour Gaëtan Leclair
        List<Integer> equipGaetan = new ArrayList<>();
        equipGaetan.add(R.drawable.ic_aspirateur);
        equipGaetan.add(R.drawable.ic_machine_a_laver);
        equipGaetan.add(R.drawable.ic_fer_a_repasser);
        equipGaetan.add(R.drawable.ic_climatiseur);
        habitants.add(new Habitat("Gaëtan Leclair", 1, equipGaetan));

        // Pour Cédric Boudet (1 équipement)
        List<Integer> equipCedric = new ArrayList<>();
        equipCedric.add(R.drawable.ic_aspirateur);
        habitants.add(new Habitat("Cédric Boudet", 1, equipCedric));

        // etc.
        List<Integer> equipGaylord = new ArrayList<>();
        equipGaylord.add(R.drawable.ic_machine_a_laver);
        equipGaylord.add(R.drawable.ic_fer_a_repasser);
        habitants.add(new Habitat("Gaylord Thibodeaux", 2, equipGaylord));

        // Adam Jacquinot (3 équipements)
        List<Integer> equipAdam = new ArrayList<>();
        equipAdam.add(R.drawable.ic_aspirateur);
        equipAdam.add(R.drawable.ic_machine_a_laver);
        equipAdam.add(R.drawable.ic_fer_a_repasser);
        habitants.add(new Habitat("Adam Jacquinot", 3, equipAdam));

        // Abel Fresnel (1 équipement)
        List<Integer> equipAbel = new ArrayList<>();
        equipAbel.add(R.drawable.ic_climatiseur);
        habitants.add(new Habitat("Abel Fresnel", 3, equipAbel));

        // Création de l'Adapter
        HabitatAdapter adapter = new HabitatAdapter(this, habitants);
        listView.setAdapter(adapter);

        // Gestion du clic sur un élément
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

        if (itemId == R.id.nav_account) {
            // Ouvrir le fragment ou l'activité "Mon compte"
        } else if (itemId == R.id.nav_consumption) {
            // Ouvrir le fragment ou l'activité "Consommation"
        } else if (itemId == R.id.nav_exit) {
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
}