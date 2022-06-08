package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class NewUserActivity extends AppCompatActivity {

    private AuthManager authManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tres TextInput e um button

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);
    }

    public void actionButton(){
        String name = ""; // pegar name do textinput
        String email = ""; // pegar email do textinput
        String password = ""; // pegar pass do textinput

        authManager.newUser(name, email, password, new CallbackAuth() {
            @Override
            public void onSucess() {
                // Mandar para login e toast de conta criada.
            };

            @Override
            public void onFailure(){
                // Toast de alguma coisa deu errada.
            };
        });
    }
}