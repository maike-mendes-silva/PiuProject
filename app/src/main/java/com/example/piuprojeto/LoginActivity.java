package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private AuthManager authManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dois TextInput e um button

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);
    }

    public void actionButton(){
        String email = ""; // pegar email do textinput
        String password = ""; // pegar pass do textinput

        authManager.login(email, password, new CallbackAuth() {
            @Override
            public void onSucess() {
                // Mandar para selector de telas (All commments ou user comments)
            };

            @Override
            public void onFailure(){
                // Toast de alguma coisa deu errada.
            };
        });
    }
}