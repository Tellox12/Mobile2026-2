package com.example.oauth.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oauth.R;
import com.example.oauth.utils.LocalAccountManager;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etUsername, etEmail, etPhone, etPassword;
    private Button btnRegister;
    private TextView tvBack;
    private TextInputLayout tilFullName, tilUser, tilEmail, tilPhone, tilPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilFullName = findViewById(R.id.tilFullName);
        tilUser = findViewById(R.id.tilUser);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilPassword = findViewById(R.id.tilPassword);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvBack = findViewById(R.id.tvBack);

        tvBack.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString();

            if (!isValidForm(fullName, username, email, phone, password)) {
                return;
            }

            new LocalAccountManager(this).saveAccount(fullName, username, email, phone, password);
            Toast.makeText(this, "Cuenta creada localmente", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private boolean isValidForm(String fullName, String username, String email, String phone, String password) {
        tilFullName.setError(null);
        tilUser.setError(null);
        tilEmail.setError(null);
        tilPhone.setError(null);
        tilPassword.setError(null);

        boolean valid = true;
        if (TextUtils.isEmpty(fullName)) {
            tilFullName.setError("Ingresa tu nombre");
            valid = false;
        }
        if (TextUtils.isEmpty(username)) {
            tilUser.setError("Crea un usuario");
            valid = false;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Correo no valido");
            valid = false;
        }
        if (phone.length() < 10) {
            tilPhone.setError("Ingresa un telefono valido");
            valid = false;
        }
        if (password.length() < 6) {
            tilPassword.setError("Usa minimo 6 caracteres");
            valid = false;
        }
        return valid;
    }
}
