package com.example.appli_dev_mobile_v2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private TextView monthYearText;
    private GridView calendarGridView;
    private Calendar currentCalendar = Calendar.getInstance(); // Initialisation avec la date actuelle
    private HashMap<String, String> colorMap; // Stockage des couleurs par date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        monthYearText = findViewById(R.id.monthYearText);
        calendarGridView = findViewById(R.id.calendarGridView);
        Button prevMonth = findViewById(R.id.prevMonth);
        Button nextMonth = findViewById(R.id.nextMonth);
        Button btnReserve = findViewById(R.id.btnReserve);

        currentCalendar = Calendar.getInstance();
        colorMap = new HashMap<>();

        fetchConsumptionData();

        prevMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateCalendar();
        });

        nextMonth.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateCalendar();
        });

        btnReserve.setOnClickListener(v -> {
            Toast.makeText(CalendarActivity.this, "Ouverture de la réservation...", Toast.LENGTH_SHORT).show();
            // Rediriger vers l'activité de réservation si nécessaire
        });
    }

    private void fetchConsumptionData() {
        String url = DBConfig.CONSUMPTION_URL;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        colorMap.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject data = response.getJSONObject(i);
                            String date = data.getString("date"); // Format AAAA-MM-JJ
                            String color = data.getString("color");
                            colorMap.put(date, color);
                        }
                        updateCalendar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(CalendarActivity.this, "Erreur : " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void updateCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearText.setText(sdf.format(currentCalendar.getTime()));

        List<String> days = new ArrayList<>();
        Calendar tempCalendar = (Calendar) currentCalendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        // On ne calcule plus firstDayOfWeek ni n'ajoute de cellules vides
        int daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= daysInMonth; i++) {
            days.add(String.valueOf(i)); // Ajoute uniquement les numéros des jours
        }

        CalendarAdapter adapter = new CalendarAdapter(this, days, colorMap, currentCalendar);
        calendarGridView.setAdapter(adapter);

        // Gestion du clic sur un jour
        calendarGridView.setOnItemClickListener((parent, view, position, id) -> {
            String day = days.get(position);
            if (!day.isEmpty()) {
                String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d",
                        currentCalendar.get(Calendar.YEAR),
                        currentCalendar.get(Calendar.MONTH) + 1,
                        Integer.parseInt(day));
                Intent intent = new Intent(CalendarActivity.this, BookingActivity.class);
                intent.putExtra("selected_date", formattedDate);
                startActivity(intent);
            }
        });
    }

}
