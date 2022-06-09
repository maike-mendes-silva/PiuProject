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

public class RecUserActivity extends AppCompatActivity implements View.OnClickListener{

    private AuthManager authManager;
    
    private Button buttonRec;
    private EditText editTextEmail;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_user_activity);

        // TextInput e Button

        buttonRec = findViewById(R.id.buttonRUA);
        editTextEmail = findViewById(R.id.textInputEditTextRUA);

        buttonRec.setOnClickListener(this); 

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);

        authManager =  new AuthManager(firebaseDatabase);

    }

    public void actionRec(){
        String email = editTextEmail.getText().toString(); // pegar email do textinput        

        if(! validateInputs( email)){
            return ;
        }

        authManager.resetPassword(email, new CallbackAuth() {
            @Override
            public void onSucess() {
                Context context = getApplicationContext();
                CharSequence text = "Email de recuperação enviado!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                sendToActivity(LoginActivity.class);
            };

            @Override
            public void onFailure(){
                Context context = getApplicationContext();
                CharSequence text = "Verifique o email ou conexão!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            };
        });
    }

    private void sendToActivity(Class finalActivity){
        intent = new Intent(RecUserActivity.this, finalActivity);
        startActivity(intent);
    }

    private boolean validateInputs(String email){

        if(email.equals("") || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Preencha corretamente");
            editTextEmail.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        actionRec();
    }
}