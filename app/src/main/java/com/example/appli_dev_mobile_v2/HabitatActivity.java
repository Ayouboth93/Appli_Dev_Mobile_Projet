package com.example.appli_dev_mobile_v2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.appli_dev_mobile_v2.Habitat;
import java.util.ArrayList;
import java.util.List;

public class HabitatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private List<Habitat> habitatList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        listView = findViewById(R.id.listViewHabitats);
        habitatList = new ArrayList<>();

        fetchHabitats();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Habitat habitat = habitatList.get(position);
                Toast.makeText(HabitatActivity.this, "Étage : " + habitat.getFloor() + ", Surface : " + habitat.getArea() + "m²", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_habitat) {
            // Ouvrir l'activité habitat
        } else if (itemId == R.id.nav_consommation) {
            // Ouvrir l'activité consommation
        } else if (itemId == R.id.nav_disconnect) {
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void fetchHabitats() {
        String url = "http://10.0.2.2/powerhome/getAllData.php"; // Vérifie si l'IP et l'URL sont corrects

        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(HabitatActivity.this, "Erreur de connexion : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (result == null || result.isEmpty()) {
                            Toast.makeText(HabitatActivity.this, "Réponse vide du serveur", Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Affichage pour debug
                        Log.d("fetchHabitats", "Réponse JSON : " + result);

                        // Parser le JSON en liste d'objets Habitat
                        List<Habitat> habitatList = Habitat.getListFromJson(result);

                        if (habitatList != null) {
                            // Mettre à jour la ListView avec un adapter
                            HabitatAdapter adapter = new HabitatAdapter(HabitatActivity.this, habitatList);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(HabitatActivity.this, "Erreur lors du parsing des habitats", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
