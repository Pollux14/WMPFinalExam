package com.example.wmpfinalexam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class RegisterPage extends AppCompatActivity {

private Button loginButton, signUpButton;
    private EditText PasswordEditText, EmailEditText, ConfirmPasswordEditText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views and Buttons
        PasswordEditText = findViewById(R.id.EmailEditText);
        EmailEditText = findViewById(R.id.PasswordEditText);
        ConfirmPasswordEditText = findViewById(R.id.ConfirmPasswordEditText);
        loginButton = findViewById(R.id.LoginBtn);
        signUpButton = findViewById(R.id.RegisterBtn);

        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = PasswordEditText.getText().toString().trim();
                String password = EmailEditText.getText().toString().trim();
                String confirmpassword = ConfirmPasswordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    PasswordEditText.setError("Please enter an email address.");
                    return;
                } else if (TextUtils.isEmpty(password)){
                    EmailEditText.setError("Please enter a password.");
                    return;
                } else if (TextUtils.isEmpty(confirmpassword)){
                    EmailEditText.setError("Please re-enter a password.");
                    return;
                } else {
                    signUpUser(email, password, confirmpassword);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpUser(String email, String password, String confirmpassword){
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            PasswordEditText.setError("Please enter a valid email address.");
            return;
        }

        if (password.length() < 8) {
            EmailEditText.setError("Password must be at least 8 characters.");
            return;
        }

        // Validate password length
        if (!password.equals(confirmpassword)) {
            ConfirmPasswordEditText.setError("Password must be the same.");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterPage.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                            // Proceed to the next activity or screen
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration Failed.";
                            Toast.makeText(RegisterPage.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}