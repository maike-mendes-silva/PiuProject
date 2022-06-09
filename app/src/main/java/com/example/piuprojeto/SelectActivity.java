package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonAllComments;
    Button buttonUserComments;
    Button buttonNewComments;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);

        buttonAllComments = findViewById(R.id.buttonSA);
        buttonUserComments = findViewById(R.id.button2SA);
        buttonNewComments = findViewById(R.id.button3SA);


        buttonNewComments.setOnClickListener(this);
        buttonUserComments.setOnClickListener(this);
        buttonNewComments.setOnClickListener(this);

        // Uma lista que quando clicar vai para listar todos os comentarios, so do user ou para criar um novo
    }

    private void sendToActivity(Class finalActivity){
        intent = new Intent(SelectActivity.this, finalActivity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSA:
                sendToActivity(ListAllCommentsActivity.class);
                break;
            case R.id.button2SA:
                sendToActivity(ListUserCommentsActivity.class);
                break;
            case R.id.button3SA:
                sendToActivity(NewCommentActivity.class);
                break;
        }
    }
}
