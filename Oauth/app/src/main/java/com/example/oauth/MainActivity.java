package com.example.oauth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oauth.ui.LoginActivity;
import com.example.oauth.utils.LocalAccountManager;
import com.example.oauth.utils.TokenManager;

public class MainActivity extends AppCompatActivity {

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenManager = new TokenManager(this);

        if (!tokenManager.isLoggedIn()) {
            goToLogin();
            return;
        }

        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        btnLogout.setOnClickListener(v -> {
            tokenManager.clearToken();
            Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
            goToLogin();
        });

        btnDeleteAccount.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("Esta seguro? Esta accion no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    tokenManager.clearToken();
                    new LocalAccountManager(this).clearAccount();
                    Toast.makeText(this, "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                    goToLogin();
                })
                .setNegativeButton("Cancelar", null)
                .show());
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
