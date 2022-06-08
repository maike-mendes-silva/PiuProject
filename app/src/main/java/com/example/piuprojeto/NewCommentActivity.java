package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

public class NewCommentActivity extends AppCompatActivity {

    private CommentManager commentManager;

    private Button buttonCriar;

    private EditText editTextComment;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_comment_activity);

        buttonCriar = findViewById(R.id.buttonNCA);

        editTextComment = findViewById(R.id.textInputEditTextNCA);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        commentManager = new CommentManager(firebaseDatabase);

        // Um ET e um button
    }

    public void actionNew(){
        String text = editTextComment.getText().toString(); // pegar text do textinput
        commentManager.newComment(text);

        Context context = getApplicationContext();
        CharSequence textToast = "Piu criado!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, textToast, duration);
        toast.show();

        sendToActivity(SelectActivity.class);
    }

    private void sendToActivity(Class finalActivity){
        intent = new Intent(NewCommentActivity.this, finalActivity);
        startActivity(intent);
    }

    private boolean validateInputs(String text){
        if(text.equals("")){
            editTextComment.setError("Preencha corretamente");
            editTextComment.requestFocus();
            return false;
        }

        return true;
    }
}