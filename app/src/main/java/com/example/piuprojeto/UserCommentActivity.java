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

public class UserCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private CommentManager commentManager;
    
    private Button buttonDelete;
    private Button buttonEdit;

    private EditText editTextComment;

    private Comment comment;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Dois TextInput e um button

        buttonDelete = findViewById(R.id.buttonUCA);
        buttonEdit = findViewById(R.id.button2UCA);

        editTextComment = findViewById(R.id.textInputEditTextUCA);

        buttonDelete.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        firebaseDatabase.setPersistenceEnabled(true);

        commentManager =  new CommentManager(firebaseDatabase);

        try {
            Intent intent = getIntent();
            comment = (Comment) intent.getSerializableExtra("comment");
            if (!comment.equals(null)){
                editTextComment.setText(comment.getText());
            }else{
                sendToActivity(ListUserCommentsActivity.class);
            }
        }catch (Exception e){

        }
    }

    public void actionEdit(){
        String text = editTextComment.getText().toString(); // pegar email do textinput

        if(! validateInputs(text)){
            return;
        }

        commentManager.editCommentText(comment, text);
        
    }

    private void actionDelete(){

        commentManager.deleteComment(comment);

        Context context = getApplicationContext();
        CharSequence text = "Comentario deletado!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        sendToActivity(ListUserCommentsActivity.class);
    }

    private boolean validateInputs(String text){
        if(text.equals("")){
            editTextComment.setError("Preencha corretamente");
            editTextComment.requestFocus();
            return false;
        }

        return true;
    }

    private void sendToActivity(Class finalActivity){
        intent = new Intent(UserCommentActivity.this, finalActivity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonUCA:
                actionEdit();
                break;
            case R.id.button2UCA:
                sendToActivity(NewUserActivity.class);
                break;
        }
    }
}