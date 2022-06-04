package com.example.piuprojeto;

/*
*/

public class Comment {
    String key, text, uid, username;

    public Comment(){}

    public Comment(String uid, String key, String username, String text) {
        this.setUid(uid);
        this.setText(text);
        this.setKey(key);
        this.setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString(){
    	return this.username + ": " + this.text:
    }
}
