package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener {

    private AuthManager authManager;

    private Button buttonCriar;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_activity);

        // Tres TextInput e um button

        buttonCriar = findViewById(R.id.buttonNUA);

        editTextName = findViewById(R.id.textInputEditTextNUA);
        editTextEmail = findViewById(R.id.textInputEditText2NUA);
        editTextPassword = findViewById(R.id.textInputEditText3NUA);
        
        buttonCriar.setOnClickListener(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);
    }

    public void actionCriar(){
        String name = editTextName.getText().toString() ;// pegar name do textinput
        String email = editTextEmail.getText().toString(); // pegar email do textinput
        String password = editTextPassword.getText().toString(); // pegar pass do textinput

        if(! validateInputs(name, email, password)){
            return ;
        }

        authManager.newUser(name, email, password, new CallbackAuth() {
            @Override
            public void onSucess() {
                // Mandar para login e toast de conta criada.

                Context context = getApplicationContext();
                CharSequence text = "Conta criada!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                sendToActivity(LoginActivity.class);
            };

            @Override
            public void onFailure(){
                // Toast de alguma coisa deu errada.
                Context context = getApplicationContext();
                CharSequence text = "Verifique os dados!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            };
        });
    }

    private boolean validateInputs(String name, String email, String password){
        if(name.equals("")){
            editTextPassword.setError("Preencha corretamente");
            editTextPassword.requestFocus();
            return false;
        }

        if(email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Preencha corretamente");
            editTextEmail.requestFocus();
            return false;
        }

        if(password.equals("")){
            editTextPassword.setError("Preencha corretamente");
            editTextPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void sendToActivity(Class finalActivity){
        intent = new Intent(NewUserActivity.this, finalActivity);
        startActivity(intent);
    }
    

    @Override
    public void onClick(View v) {
        actionCriar();
    }
}
