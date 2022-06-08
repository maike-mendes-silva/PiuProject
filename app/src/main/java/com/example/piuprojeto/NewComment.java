package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class NewComment extends AppCompatActivity {

    private CommentManager commentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_comment_activity);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        commentManager = new CommentManager(firebaseDatabase);

        // Um ET e um button
    }

    public void actionButton(){
        String text = ""; // pegar text do textinput
        commentManager.newComment(text);
    }
}