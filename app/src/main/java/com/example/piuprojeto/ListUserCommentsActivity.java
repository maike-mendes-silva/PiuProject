package com.example.piuprojeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;

public class ListUserCommentsActivity extends AppCompatActivity {

    ArrayList<Comment> commentsUser = new ArrayList<>();
    ArrayAdapter<Comment> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user_comments_activity);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        DatabaseReference commentsNode = firebaseDatabase.getReference().child("comments");
        subscribeQueryOnValueEventListener(commentsNode);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentsAll);
        setListAdapter(adapter);
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

    private void sendToCommentActivity(Comment comment){
        intent = new Intent(ListUserCommentsActivity.this, UserCommentActivity.class);
        intent.putExtra("comment", comment);
        startActivity(intent);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        sendToCommentActivity(commentsUser.get(position));
    }
}
