package com.example.schedulegenerator.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.schedulegenerator.Model.User;
import com.example.schedulegenerator.R;
import com.example.schedulegenerator.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText userEmail;
    private EditText userPassword;
    private EditText userRealName;
    private ImageView imageView;
    private Spinner userRole;
    private final int PICK_IMAGE_REQUEST = 22;

    private Uri filePath;


    private FirebaseAuth mAuth;
    private FirebaseFirestore fireStore;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        userEmail = findViewById(R.id.emailField);
        userPassword = findViewById(R.id.password);
        userRealName = findViewById(R.id.realName);
        imageView = findViewById(R.id.profilePic);
        userRole = findViewById(R.id.user_role);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRole.setAdapter(adapter);
        userRole.setOnItemSelectedListener(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public void signUp(View v) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), Constants.EMPTYFIELDERROR, Toast.LENGTH_SHORT)
                    .show();
            updateUI(null);
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), Constants.INVALIDATEEMAIL, Toast.LENGTH_SHORT)
                    .show();
            updateUI(null);
        } else
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                String userID = getCurrentUser().getUid();
                                fireStore.collection(Constants.USER).document(userID).set(
                                        getCurrentUser());
                                updateUI(mAuth.getCurrentUser());
                                uploadImage();
                                Toast.makeText(SignUpActivity.this,
                                        "Sign up successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void updateUI(FirebaseUser firebaseUser)
    {
        if (firebaseUser != null)
        {
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra(Constants.ROLE, userRole.getSelectedItem().toString());
            startActivity(i);
        }
    }
    public void selectImage(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + getCurrentUser().getImgID());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,
                                    "Imaged upload failure", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            progressDialog.dismiss();
                        }
                    });
        }
    }
    private User getCurrentUser()
    {
        ArrayList<String> newArrayList = new ArrayList<String>();
        String role = userRole.getSelectedItem().toString();
        User currentUser = new User(mAuth.getUid(), userRealName.getText().toString(),
                userEmail.getText().toString(), newArrayList, role,
                userRealName.getText().toString() + userPassword.getText().toString());
        return currentUser;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String text = "Choose a role!";
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }
}