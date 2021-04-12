package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulegenerator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    private EditText emailField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.emailAddress);
        passwordField = findViewById(R.id.passwordField);
    }

    public void signin(View v)
    {
        String emailText = emailField.getText().toString();
        String userPassword = passwordField.getText().toString();
        if (emailText.length() == 0 || userPassword.length() == 0)
        {
            Toast.makeText(AuthActivity.this,
                    "Information incomplete", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(emailText, userPassword).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AuthActivity.this,
                                        "successfully signed in the user",
                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(AuthActivity.this, "Authentification failed"
                                                + task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    public void signup(View v)
    {
        String userEmail = emailField.getText().toString();
        String userPassword = passwordField.getText().toString();
        if(!userEmail.contains("@"))
        {
            Toast.makeText(AuthActivity.this,
                    "Please enter valid email address", Toast.LENGTH_LONG).show();
        }
        if (userEmail.length() == 0 || userPassword.length() == 0)
        {
            Toast.makeText(AuthActivity.this,
                    "Information incomplete", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Log.d("SIGN UP", "Successfully signed up the user");
                                FirebaseUser user = mAuth.getCurrentUser();
                                fireStore.collection("users")
                                        .document(user.getUid()).set();
                                Toast.makeText(AuthActivity.this,
                                        "successfully signed up the user",
                                        Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Log.w("SIGN UP","createUserWithEmail: failure",
                                        task.getException());
                                Toast.makeText(AuthActivity.this,
                                        "authentification failed" + task.getException().toString(),
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                    });	        }

    }
}