package com.example.oauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

        LocalAccountManager accountManager = new LocalAccountManager(this);
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        TextView tvAccountHolder = findViewById(R.id.tvAccountHolder);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        Button btnTransfer = findViewById(R.id.btnTransfer);
        Button btnPayService = findViewById(R.id.btnPayService);
        Button btnCard = findViewById(R.id.btnCard);
        Button btnWithdraw = findViewById(R.id.btnWithdraw);
        View tileOpportunities = findViewById(R.id.tileOpportunities);
        View tileSecurity = findViewById(R.id.tileSecurity);

        String fullName = accountManager.getFullName();
        String email = accountManager.getEmail();
        if (fullName != null && !fullName.isEmpty()) {
            tvGreeting.setText("Hola, " + fullName.split(" ")[0]);
            tvAccountHolder.setText(fullName);
        } else if (email != null && !email.isEmpty()) {
            tvGreeting.setText("Hola");
            tvAccountHolder.setText(email);
        }

        btnTransfer.setOnClickListener(v -> showFeature("Transferencias disponibles en modo demostracion"));
        btnPayService.setOnClickListener(v -> showFeature("Pago de servicios listo para conectar"));
        btnCard.setOnClickListener(v -> showFeature("Tarjeta digital protegida"));
        btnWithdraw.setOnClickListener(v -> showFeature("Retiro sin tarjeta preparado"));
        tileOpportunities.setOnClickListener(v -> showFeature("No tienes oportunidades nuevas por ahora"));
        tileSecurity.setOnClickListener(v -> showFeature("Seguridad activa con acceso protegido"));

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

    private void showFeature(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
