package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListUserCommentsActivity extends AppCompatActivity {

    ArrayList<Comment> commentsUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        DatabaseReference commentsNode = firebaseDatabase.getReference().child("comments");
        subscribeQueryOnValueEventListener(commentsNode);

        // Uma lista que quando clicar vai pruma tela com um TextInput desabilitado, tendo um
        // button para deletar outro para editar; cada um realizando a função necessaria
        // seria passado como info entre telas o Comment clicado.

    }

    private void subscribeQueryOnValueEventListener(DatabaseReference commentsNode) {
        Query queryAllComments = commentsNode;

        queryAllComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsUser.clear();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Comment comment = dataSnapshot1.getValue(Comment.class);

                    if (comment.getUid().equals( AuthManager.getUser().getUid())) {
                        commentsUser.add(comment);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
