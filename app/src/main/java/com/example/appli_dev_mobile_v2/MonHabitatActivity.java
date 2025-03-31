package com.example.appli_dev_mobile_v2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class MonHabitatActivity extends AppCompatActivity {

    private TextView consommationTextView;
    private int habitatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_habitat);

        consommationTextView = findViewById(R.id.textViewConsommation);

        habitatId = Habitat.getHabitat_id();

        fetchConsumption();
    }

    private void fetchConsumption() {
        String url = DBConfig.CONSO_HABITAT_URL + "?habitat_id=" + habitatId;

        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(MonHabitatActivity.this, "Erreur de connexion : " + e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Log.e("DEBUG_RESPONSE", "Réponse du serveur : " + result);

                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            if (jsonResponse.has("error")) {
                                Toast.makeText(MonHabitatActivity.this, jsonResponse.getString("error"), Toast.LENGTH_LONG).show();
                                return;
                            }

                            int totalConsumption = jsonResponse.getInt("total_consumption");
                            consommationTextView.setText("Consommation totale : " + totalConsumption + " W");

                        } catch (JSONException jsonException) {
                            Toast.makeText(MonHabitatActivity.this, "Erreur de parsing des données", Toast.LENGTH_LONG).show();
                            Log.e("MonHabitatActivity", "Parsing error", jsonException);
                        }
                    }
                });
    }
}