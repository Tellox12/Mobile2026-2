package com.example.oauth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.example.oauth.MainActivity;
import com.example.oauth.R;
import com.example.oauth.utils.LocalAccountManager;
import com.example.oauth.utils.TokenManager;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.material.textfield.TextInputLayout;

import java.security.SecureRandom;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    private Button btnGoogle;
    private Button btnEmailLogin;
    private EditText etEmailLogin, etPasswordLogin;
    private TextInputLayout tilEmailLogin, tilPasswordLogin;
    private TextView tvGoRegister;
    private View progressBar;
    private CredentialManager credentialManager;
    private Executor mainExecutor;
    private LocalAccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmailLogin = findViewById(R.id.tilEmailLogin);
        tilPasswordLogin = findViewById(R.id.tilPasswordLogin);
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnEmailLogin = findViewById(R.id.btnEmailLogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        progressBar = findViewById(R.id.progressBar);
        tvGoRegister = findViewById(R.id.tvGoRegister);
        credentialManager = CredentialManager.create(this);
        mainExecutor = ContextCompat.getMainExecutor(this);
        accountManager = new LocalAccountManager(this);

        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        btnEmailLogin.setOnClickListener(v -> loginWithLocalAccount());
        btnGoogle.setOnClickListener(v -> startGoogleSignIn());
    }

    private void loginWithLocalAccount() {
        tilEmailLogin.setError(null);
        tilPasswordLogin.setError(null);

        if (!accountManager.hasAccount()) {
            Toast.makeText(this, "No hay cuenta creada. Crea una para continuar.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, RegisterActivity.class));
            return;
        }

        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString();
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            tilEmailLogin.setError("Ingresa tu correo");
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            tilPasswordLogin.setError("Ingresa tu contrasena");
            valid = false;
        }
        if (!valid) {
            return;
        }

        if (!accountManager.isValidLogin(email, password)) {
            Toast.makeText(this, "Correo o contrasena incorrectos", Toast.LENGTH_LONG).show();
            return;
        }

        new TokenManager(this).saveToken("local_session");
        Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void startGoogleSignIn() {
        String webClientId = getString(R.string.google_web_client_id);
        if (webClientId.startsWith("REEMPLAZA_CON_TU_WEB_CLIENT_ID")) {
            Toast.makeText(this, "Configura google_web_client_id en strings.xml", Toast.LENGTH_LONG).show();
            return;
        }

        setGoogleLoading(true);

        GetSignInWithGoogleOption googleOption = new GetSignInWithGoogleOption.Builder(webClientId)
                .setNonce(generateNonce())
                .build();
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleOption)
                .build();

        credentialManager.getCredentialAsync(
                this,
                request,
                new CancellationSignal(),
                mainExecutor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleGoogleCredential(result);
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        setGoogleLoading(false);
                        Toast.makeText(LoginActivity.this,
                                "No se completo el inicio con Google: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void handleGoogleCredential(GetCredentialResponse result) {
        Credential credential = result.getCredential();
        if (!(credential instanceof CustomCredential)) {
            setGoogleLoading(false);
            Toast.makeText(this, "Google no devolvio una credencial valida", Toast.LENGTH_LONG).show();
            return;
        }

        CustomCredential customCredential = (CustomCredential) credential;
        if (!GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(customCredential.getType())) {
            setGoogleLoading(false);
            Toast.makeText(this, "Tipo de credencial no compatible", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            GoogleIdTokenCredential googleCredential =
                    GoogleIdTokenCredential.createFrom(customCredential.getData());
            saveGoogleSession(googleCredential.getIdToken());
        } catch (RuntimeException e) {
            setGoogleLoading(false);
            Toast.makeText(this, "No se pudo leer el token de Google", Toast.LENGTH_LONG).show();
        }
    }

    private void saveGoogleSession(String idToken) {
        new TokenManager(this).saveToken(idToken);
        setGoogleLoading(false);
        Toast.makeText(this, "Sesion iniciada con Google", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void setGoogleLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnGoogle.setEnabled(!loading);
        btnEmailLogin.setEnabled(!loading);
    }

    private String generateNonce() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.encodeToString(bytes, Base64.NO_WRAP | Base64.URL_SAFE | Base64.NO_PADDING);
    }
}
