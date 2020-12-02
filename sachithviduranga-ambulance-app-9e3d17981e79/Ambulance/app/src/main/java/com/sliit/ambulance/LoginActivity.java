package com.sliit.ambulance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private final String username = "amb";
    private final String password = "123";
    private String usernameInput;
    private String passwordInput;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);


        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInput = String.valueOf(editTextUsername.getText());
                passwordInput = String.valueOf(editTextPassword.getText());
                if (usernameInput.equals(username)){
                    if (passwordInput.equals(password)){
                        makeToast("Login successful");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }else {
                        makeToast("Invalid password");
                    }
                }else {
                    makeToast("Invalid username");
                }
            }
        });

    }

    private void makeToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
