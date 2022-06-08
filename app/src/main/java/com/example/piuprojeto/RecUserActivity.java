package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class RecUserActivity extends AppCompatActivity {

    private AuthManager authManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextInput e Button

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);

    }

    public void actionButton(){
        String email = ""; // pegar email do textinput
        authManager.resetPassword(email, new CallbackAuth() {
            @Override
            public void onSucess() {
                // Mostrar toast e mandar para Login
            };

            @Override
            public void onFailure(){
                // Toast de alguma coisa deu errada.
            };
        });
    }
}