package com.example.piuprojeto;

/*
 Fazer o login
 Criar um novo usuario
 Recuperar senha
*/

import androidx.annotation.NonNull;


import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AuthManager {

	private FirebaseAuth auth;

	private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersNode;

	public static User user = new User();

	AuthManager(FirebaseDatabase firebaseDatabase){
		this.firebaseDatabase = firebaseDatabase;
		initFirebase();
	}

	private void initFirebase() {
		this.auth = FirebaseAuth.getInstance();
        // firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);

        this.usersNode = firebaseDatabase.getReference().child("users");
    }

	public void login(String email, String password, CallbackAuth callbackAuth){
		auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                	FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                	usersNode.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
			            @Override
			            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
			            	User userLogin = dataSnapshot.getValue(User.class); 
		                    user.setUsername(userLogin.getUsername());

		                    callbackAuth.onSucess();
			            }

			            @Override
			            public void onCancelled(@NonNull DatabaseError databaseError) {
			            }
			        });
                	
                	user.setUid(currentUser.getUid());

					// setUser(userLogin);
					
                }
                else {
                    callbackAuth.onFailure();
                }            
            }
        });

	}

	public void newUser(String name, String email, String password){
		auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                	FirebaseUser newUserFirebase = task.getResult().getUser();
			        saveNewUserOnDatabase(newUserFirebase.getUid(), name);
                }
            }
        });
	}

	public void newUser(String name, String email, String password, CallbackAuth callbackAuth){
		auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                	FirebaseUser newUserFirebase = task.getResult().getUser();
			        saveNewUserOnDatabase(newUserFirebase.getUid(), name);

			        callbackAuth.onSucess();
                }else{
                	callbackAuth.onFailure();
                }
            }
        });
	}

	public void resetPassword(String email, CallbackAuth callbackAuth){

		auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
			        callbackAuth.onSucess();                    
                }
                else{
                	callbackAuth.onFailure();
                }  
            }
        });
	}

	private void saveNewUserOnDatabase(String uid, String username){
		User newUser = new User(username);
		DatabaseReference currentRefUsr = usersNode.child(uid);
        currentRefUsr.setValue(newUser);
	}

	public static User getUser(){
		return user;
	}

	public static void setUser(User newUser){
		user = newUser;
	}
}