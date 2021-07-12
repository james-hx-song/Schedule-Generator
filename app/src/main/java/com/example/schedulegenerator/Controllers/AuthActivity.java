package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Responsible for user sign in, activity that the user first encounters when opening the app
 */
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

    /**
     * Action of signing in: authentificating the user with fireabse
     * @param v sign in button
     */
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
            //Verification with firebase
            mAuth.signInWithEmailAndPassword(emailText, userPassword).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AuthActivity.this,
                                        Constants.SUCCESS,
                                        Toast.LENGTH_LONG).show();
                                updateUI(mAuth.getCurrentUser());
                            }
                            else
                            {
                                Toast.makeText(AuthActivity.this, Constants.FAILURE
                                                + task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    /**
     * Checks if firebaseuser is null or not
     * @param firebaseUser current user
     */
    private void updateUI(FirebaseUser firebaseUser)
    {
        if (firebaseUser != null)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    /**
     * redirects to sign up page if needed
     * @param v of signup button
     */
    public void signup(View v) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivity(i);
    }
}