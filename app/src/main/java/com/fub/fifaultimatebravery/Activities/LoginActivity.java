package com.fub.fifaultimatebravery.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fub.fifaultimatebravery.R;
import com.fub.fifaultimatebravery.Spotify.ExitService;
import com.fub.fifaultimatebravery.ViewModels.LoginActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    // Variables
    private LoginActivityViewModel viewModel;
    EditText EdtTxtUserName, EdtTxtPassword;
    Button bntLogIn, bntRegister, bntExit;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Resources
        mAuth = FirebaseAuth.getInstance();

        bntLogIn = findViewById(R.id.loginBtn);
        bntRegister = findViewById(R.id.registerBtn);
        bntExit = findViewById(R.id.exitBtn);
        EdtTxtUserName = findViewById(R.id.userNameET);
        EdtTxtPassword = findViewById(R.id.passwordET);
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        Intent exitIntent = new Intent(this, ExitService.class);
        startService(exitIntent);

        bntLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInClicked();
            }
        });

        bntRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterClicked();
            }
        });

        bntExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitClicked();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.startSpotify(this);
    }

    private void tryCreateNewUser(){
        String email = EdtTxtUserName.getText().toString();
        String PW = EdtTxtPassword.getText().toString();
        if(email == null||email.length()<1||PW == null || PW.length()<1){
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, PW)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "Created User");
                                Toast.makeText(LoginActivity.this, "Created User", Toast.LENGTH_SHORT).show();
                                populateWagers();
                            } else {
                                Log.d("TAG", "Could not create user");
                                Toast.makeText(LoginActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void trySigningIn(){
        String email = EdtTxtUserName.getText().toString();
        String PW = EdtTxtPassword.getText().toString();
        if(email == null||email.length()<1||PW == null || PW.length()<1){
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email, PW)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Tag", "Login Success");
                                goToSecurePart();
                            } else {
                                Log.d("Tag", "Login failed");
                                Toast.makeText(LoginActivity.this, "Failed to log in", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void goToSecurePart() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private void populateWagers(){
        String userID = mAuth.getCurrentUser().getUid();
        if (Locale.getDefault().getLanguage().equals("en"))
        {
            for (int i = 0; i < 10; i++ ){
                String customWager = getResources().getStringArray(R.array.WagersList)[i];
                viewModel.addWager(customWager, userID);
            }
        } else if (Locale.getDefault().getLanguage().equals("da")) {
            for (int i = 0; i < 10; i++) {
                String customWager = getResources().getStringArray(R.array.WagersList)[i];
                viewModel.addWager(customWager, userID);
            }
        }
    }

    private void ExitClicked() {
        finish();
    }

    private void RegisterClicked() {
        tryCreateNewUser();
    }

    private void LogInClicked() {
        trySigningIn();
    }
}