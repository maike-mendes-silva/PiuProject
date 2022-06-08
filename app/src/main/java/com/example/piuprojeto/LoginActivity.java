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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AuthManager authManager;
    
    private Button buttonLogin;
    private Button buttonCadastro;
    private Button buttonRecUser;

    private EditText editTextEmail;
    private EditText editTextPassword;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Dois TextInput e um button

        buttonLogin = findViewById(R.id.buttonLA);
        buttonCadastro = findViewById(R.id.button2LA);
        buttonRecUser = findViewById(R.id.button3LA);

        editTextEmail = findViewById(R.id.textInputEditTextLA);
        editTextPassword = findViewById(R.id.textInputEditText2LA);

        buttonLogin.setOnClickListener(this);
        buttonCadastro.setOnClickListener(this);
        buttonRecUser.setOnClickListener(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);
    }

    public void actionLogin(){
        String email = editTextEmail.getText().toString(); // pegar email do textinput
        String password = editTextPassword.getText().toString(); // pegar pass do textinput

        if(! validateInputs(email, password)){
            return;
        }

        authManager.login(email, password, new CallbackAuth() {
            @Override
            public void onSucess() {
                // Mandar para selector de telas (All commments ou user comments)
                sendToActivity(SelectActivity.class);
            };

            @Override
            public void onFailure(){
                Context context = getApplicationContext();
                CharSequence text = "Verifique os dados!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            };
        });
    }

    private boolean validateInputs(String email, String password){
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
        intent = new Intent(LoginActivity.this, finalActivity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLA:
                actionLogin();
                break;
            case R.id.button2LA:
                sendToActivity(NewUserActivity.class);
                break;
            case R.id.button3LA:
                sendToActivity(RecUserActivity.class);
                break;
        }
    }
}