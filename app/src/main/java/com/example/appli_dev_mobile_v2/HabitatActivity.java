package com.example.appli_dev_mobile_v2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class HabitatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat);

        ListView listView = findViewById(R.id.listViewHabitats);

        // Création des données
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
}
