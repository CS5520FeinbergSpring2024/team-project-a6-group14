package com.example.unisphere.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {

    private static AuthService instance;
    private final FirebaseAuth firebaseAuth;

    private AuthService() {
        firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance());
        System.out.println("hello world");
    }

    /**
     * Get a static instance of the Auth Service.
     *
     * @return an instance of the auth service.
     */
    public static AuthService getInstance() {

        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public void signOut() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            this.firebaseAuth.signOut();
        }
    }

    public void loginWithEmailAndPassword(String email, String password, LoginCallback loginCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                //TODO: Check how to replace this with Tokens instead of email.
                if (firebaseUser != null) {
                    loginCallback.onLoginSuccess(email);
                } else {
                    //TODO: new Exception could just be string.


                }
            } else {
                loginCallback.onLoginFailure(new Exception("Authentication Failed"));
            }

        });


    }

}
