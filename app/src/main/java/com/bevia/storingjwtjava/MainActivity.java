package com.bevia.storingjwtjava;

import static com.bevia.storingjwtjava.TokenManager.getToken;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView textTitleView, textTokenView, jwt, key;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textTitleView = findViewById(R.id.jwt_title_text);
        textTokenView = findViewById(R.id.jwt_text);

        key = findViewById(R.id.generated_key);

        String jwtToken = getString(R.string.jwt);
        jwt = findViewById(R.id.jwt);
        jwt.setText(jwtToken);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            saveJWT(jwtToken);
        });

        Button getButton = findViewById(R.id.get_button);
        getButton.setOnClickListener(v -> {
            getJWT();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getJWT() {

        try {
            textTitleView.setText(R.string.clear_jwt_token);
            textTokenView.setText(AESUtils.decrypt(getToken(this)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveJWT(String jwtToken) {

        try {
            String encryptedData = AESUtils.encrypt(jwtToken);
            TokenManager.saveToken(this, encryptedData);

            key.setText(String.format("Key hashcode: %s", (Object) Arrays.hashCode(
                    AESUtils.generateKey().getEncoded())));

            textTitleView.setText(R.string.encrypted_jwt_token);
            textTokenView.setText(encryptedData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}