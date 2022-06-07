package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

// AO USAR COMMENTMANAGER OU AUTHMANANGER, ADICIONE 
// [COMEÇA]
import com.google.firebase.database.FirebaseDatabase;
// [TERMINA]

// SE PRECISAR LER COMMENTS DO FIREBASE, ADICIONE ESSE CODIGO
// [COMEÇA]
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
// [TERMINA]

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	// Coloca os manager como atributos
    private AuthManager authManager;
    private CommentManager commentManager;

    // SE PRECISAR LER COMMENTS DO FIREBASE, ADICIONE ESSE CODIGO
	// [COMEÇA]
    ArrayList<Comment> commentsUser = new ArrayList<>();
    ArrayList<Comment> commentsAll = new ArrayList<>();
    // [TERMINA]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // AO USAR CommentManager OU AuthManager, ADICIONE
        // SERVE PARA INJEÇÃO DE DEPENDENCIA! 
        // [COMEÇA]
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        // [TERMINA]

        // Instancia os managers
        authManager =  new AuthManager(firebaseDatabase);
        commentManager = new CommentManager(firebaseDatabase);

        testeAuth(); // EXAMPLO DE LOGIN

        // SE PRECISAR LER COMMENTS DO FIREBASE, ADICIONE ESSE CODIGO
		// [COMEÇA]
		DatabaseReference commentsNode = firebaseDatabase.getReference().child("comments");
        subscribeQueryOnValueEventListener(commentsNode);
        // [TERMINA]
    }

    private void testeAuth() {
    	// EXAMPLO DE LOGIN
        authManager.login("exodo.melo@estudante.ifms.edu.br", "123456789", new CallbackAuth() {
            @Override
            public void onSucess() {
                testeComment(); // // EXAMPLO DE COMENTARIO
            };

            @Override
            public void onFailure(){};
        });
    }

    private void testeComment(){
        commentManager.newComment("teste 123");
        commentManager.newComment("teste 1234");
        commentManager.newComment("teste 123456");

        commentManager.editCommentText(0, "teste 12345");
        commentManager.deleteComment(1);
    }

    // SE PRECISAR LER COMMENTS DO FIREBASE, ADICIONE ESSE CODIGO
	// [COMEÇA]
	private void subscribeQueryOnValueEventListener(DatabaseReference commentsNode) {
		Query queryAllComments = commentsNode;

		queryAllComments.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		    	commentsUser.clear();
		    	commentsAll.clear();

		        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
		            Comment comment = dataSnapshot1.getValue(Comment.class);
		            commentsAll.add(comment);

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
	// [TERMINA]
}