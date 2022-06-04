package com.example.piuprojeto;

/*
 Pegar todos os comentarios criados;
 Pegar os comentarios do usuario logado;
 Deletar e editar comentarios;
*/

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentManager {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference commentsNode;

    ArrayList<Comment> commentsUser = new ArrayList<>();
    ArrayList<Comment> commentsAll = new ArrayList<>();

    User user;
    Boolean userExists = false;

    public CommentManager() {
        this.user = AuthManager.getUser();

        // Remover gambiarra e fazer um erro
        if(user != null){
            this.userExists = true;
        }
        
        initFirebase();
        subscribeQueryOnValueEventListener();
    }

    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        commentsNode = firebaseDatabase.getReference().child("comments");
    }

    private void subscribeQueryOnValueEventListener() {
        Query queryAllComments = commentsNode;

        queryAllComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Comment comment = dataSnapshot1.getValue(Comment.class);
                    commentsAll.add(comment);

                    if (comment.getUid().equals(user.getUid())) {
                    	commentsUser.add(comment);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // CREATE, UPDATE, DELETE
    public void newComment(String text){
        DatabaseReference currentRefComment = commentsNode.push();
        String key = currentRefComment.getKey();

        Comment comment = new Comment(user.getUid(), key, user.getUSername(), text);
        currentRefComment.setValue(comment);
    }

    public void deleteComment(int index){
        Comment comment = commentsUser.get(index);
        commentsUser.clear();

        commentsNode.child(comment.getKey()).removeValue();
    }

    public void editCommentText(int index, String text){
        Comment comment = commentsUser.get(index);
        commentsUser.clear();

        comment.setText(text);
        commentsNode.child(comment.getKey()).setValue(comment);
    }

    // READ
    public ArrayList<Comment> getCommentsUser() {
        return commentsUser;
    }

    public List<Comment> getAllComments(){
        return commentsAll;
    }
}
