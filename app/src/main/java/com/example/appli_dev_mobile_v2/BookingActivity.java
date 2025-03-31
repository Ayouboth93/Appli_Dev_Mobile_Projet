package com.example.appli_dev_mobile_v2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    private Spinner applianceSpinner, timeSlotSpinner;
    private Button btnReserve;
    private List<String> appliancesList;
    private List<String> timeSlots;
    private List<String> formattedTimeSlots; // Pour stocker les créneaux formatés pour l'API
    private String selectedAppliance;
    private String selectedTimeSlot;
    private String selectedDate; // Pour stocker la date sélectionnée

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        selectedDate = getIntent().getStringExtra("selected_date");
        if (selectedDate == null) {
            // Si aucune date n'est fournie, utilisez la date du jour
            selectedDate = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(new java.util.Date());
        }

        Log.d("BookingActivity", "Date sélectionnée : " + selectedDate);

        applianceSpinner = findViewById(R.id.spinnerAppliance);
        timeSlotSpinner = findViewById(R.id.spinnerTimeSlot);
        btnReserve = findViewById(R.id.btnReserveSlot);

        appliancesList = new ArrayList<>();

        // Générer les créneaux horaires avec le format pour l'affichage et pour l'API
        generateTimeSlots();

        // Récupérer l'ID de l'habitat et vérifier la disponibilité des appareils
        int habitatId = Habitat.getHabitat_id();
        fetchAppliances(habitatId);

        // Remplir les créneaux horaires
        setupTimeSlotsSpinner();

        // Listener pour le bouton de réservation
        btnReserve.setOnClickListener(v -> checkAndReserveSlot());
    }
    private void generateTimeSlots() {
        timeSlots = new ArrayList<>();
        formattedTimeSlots = new ArrayList<>();

        for (int hour = 8; hour < 22; hour++) {  // Créneaux entre 08:00 et 22:00
            // Format d'affichage pour l'utilisateur: "08:00 - 09:00"
            String displayFormat = String.format("%02d:00 - %02d:00", hour, hour + 1);
            timeSlots.add(displayFormat);

            // Format pour l'API: ["2024-03-31 08:00:00", "2024-03-31 09:00:00"]
            String startTime = String.format("%s %02d:00:00", selectedDate, hour);
            String endTime = String.format("%s %02d:00:00", selectedDate, hour + 1);
            formattedTimeSlots.add(startTime + "," + endTime);
        }
    }

    private void setupTimeSlotsSpinner() {
        // Remplissage du Spinner des créneaux horaires avec le format d'affichage
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeSlots);
        timeSlotSpinner.setAdapter(timeAdapter);
        timeSlotSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Stocker le créneau formaté pour l'API
                selectedTimeSlot = formattedTimeSlots.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void fetchAppliances(int habitatId) {
        String url = DBConfig.HABITAT_APPLIANCES +  "?habitat_id="+habitatId;
        Log.d("BookingActivity", "URL de l'API pour récupérer les appareils : " + url); // Log de l'URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Vérifie si l'API a retourné des appareils
                        String status = response.getString("status");
                        if ("success".equals(status)) {
                            JSONArray appliancesArray = response.getJSONArray("appliances");
                            appliancesList.clear();
                            appliancesList.add("Aucun appareil sélectionné"); // Option par défaut

                            // Récupérer les noms des appareils
                            for (int i = 0; i < appliancesArray.length(); i++) {
                                JSONObject appliance = appliancesArray.getJSONObject(i);
                                String name = appliance.getString("name");
                                appliancesList.add(name);
                            }

                            // Adapter pour le Spinner des appareils
                            ArrayAdapter<String> applianceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, appliancesList);
                            applianceSpinner.setAdapter(applianceAdapter);

                            // Listener pour le spinner des appareils
                            applianceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedAppliance = appliancesList.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {}
                            });

                        } else {
                            Toast.makeText(this, "Aucun appareil trouvé", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur de parsing des données", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        Log.e("BookingActivity", "Code de réponse : " + error.networkResponse.statusCode);
                        Log.e("BookingActivity", "Détails de l'erreur : " + new String(error.networkResponse.data));
                    } else {
                        Log.e("BookingActivity", "Erreur Volley : " + error.getMessage());
                    }
                    Toast.makeText(this, "Erreur de communication avec l'API", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private List<String> generateFixedTimeSlots() {
        List<String> slots = new ArrayList<>();
        for (int hour = 8; hour < 22; hour++) {  // Créneaux entre 08:00 et 22:00
            String start = String.format("%02d:00", hour);
            String end = String.format("%02d:00", hour + 1);
            slots.add(start + " - " + end);
        }
        return slots;
    }

    private void checkAndReserveSlot() {
        if (selectedAppliance == null || selectedAppliance.equals("Aucun appareil sélectionné") || selectedTimeSlot == null) {
            Toast.makeText(this, "Veuillez sélectionner un appareil et un créneau", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vérification...");
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("habitat_id", String.valueOf(Habitat.getHabitat_id()));
        params.put("appliance_name", selectedAppliance);
        params.put("time_slot", selectedTimeSlot);
        Log.e("valeur", "Appareil: " + selectedAppliance + ", Créneau: " + selectedTimeSlot + ", Habitat: " + Habitat.getHabitat_id());
        String url = DBConfig.CHECK_RESERVATION_URL;
        Log.d("BookingActivity", "URL de vérification : " + url); // Log de l'URL de vérification

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    progressDialog.dismiss();
                    try {
                        if (response.getString("status").equals("success")) {
                            reserveSlot();
                        } else {
                            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur de réponse serveur", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    if (error.networkResponse != null) {
                        Log.e("BookingActivity", "Code de réponse : " + error.networkResponse.statusCode);
                        Log.e("BookingActivity", "Détails de l'erreur : " + new String(error.networkResponse.data));
                    } else {
                        Log.e("BookingActivity", "Erreur Volley : " + error.getMessage());
                    }
                    Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void reserveSlot() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Réservation en cours...");
        progressDialog.show();

        String url = DBConfig.RESERVE_SLOT_URL;
        Log.d("BookingActivity", "URL de réservation : " + url); // Log de l'URL de réservation

        Map<String, String> params = new HashMap<>();
        params.put("habitat_id", String.valueOf(Habitat.getHabitat_id()));
        params.put("appliance_name", selectedAppliance);
        params.put("time_slot", selectedTimeSlot);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    progressDialog.dismiss();
                    try {
                        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur lors de la réservation", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    if (error.networkResponse != null) {
                        Log.e("BookingActivity", "Code de réponse : " + error.networkResponse.statusCode);
                        Log.e("BookingActivity", "Détails de l'erreur : " + new String(error.networkResponse.data));
                    } else {
                        Log.e("BookingActivity", "Erreur Volley : " + error.getMessage());
                    }
                    Toast.makeText(this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
