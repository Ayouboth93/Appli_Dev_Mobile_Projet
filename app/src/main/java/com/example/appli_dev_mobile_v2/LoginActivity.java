package com.example.appli_dev_mobile_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Références vers les vues
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Écouteur du bouton
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Ici tu peux mettre ta propre vérification avec une base ou une API
                if (email.equals("test@mail.com") && password.equals("1234")) {
                    // Connexion réussie -> redirection vers HabitatActivity
                    Intent intent = new Intent(LoginActivity.this, HabitatActivity.class);
                    startActivity(intent);
                    finish(); // Ferme la page de login
                } else {
                    Toast.makeText(LoginActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
