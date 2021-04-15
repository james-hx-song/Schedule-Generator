package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private EditText userEmail;
    private EditText userPassword;
    private EditText userRealName;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        userEmail = findViewById(R.id.emailField);
        userPassword = findViewById(R.id.password);
        userRealName = findViewById(R.id.realName);
    }

    public void signUp(View v) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "please enter email address", Toast.LENGTH_SHORT)
                    .show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "invalid email address", Toast.LENGTH_SHORT)
                    .show();
        } else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                String userID = getCurrentUser().getUid();
                                fireStore.collection("users").document(userID).set(
                                        getCurrentUser()
                                );
                                Intent i = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }
    }
    private User getCurrentUser()
    {
        User currentUser = new User(mAuth.getUid(), userRealName.getText().toString(),
                userEmail.getText().toString(), new ArrayList<String>(), "default");
        return currentUser;
    }
}