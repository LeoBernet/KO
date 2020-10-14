package com.example.ko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NapisatOtziv extends AppCompatActivity {

    private static final String TAG = "NapisatOtziv";

    private TextInputLayout textInputEmail;
    private TextInputLayout textInputName;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputConfirmPassword;

    private Button loginSozdatButton;
    private TextView loginVoitiTextView;

    private boolean isLoginActive;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napisat_otziv);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        loginSozdatButton = findViewById(R.id.loginSozdatButton);
        loginVoitiTextView = findViewById(R.id.loginVoitiTextView);

        auth = FirebaseAuth.getInstance();
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()){
            textInputEmail.setError("Пожалуйста введите Ваш e-mail");
            return false;
        }else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()){
            textInputName.setError("Пожалуйста введите Ваше имя");
            return false;
        }else if (nameInput.length() >15) {
            textInputName.setError("Длинна имени должна быть не более 15 букв");
            return false;
        }else {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText()
                .toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Пожалуйста введите пароль");
            return false;
        }else if (passwordInput.length() < 7) {
            textInputPassword.setError("Длинна пароля должна быть не менее 7 символов");
            return false;
        }else {
            textInputPassword.setError("");
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String passwordInput = textInputPassword.getEditText().getText()
                .toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText()
                .toString().trim();

        if (!passwordInput.equals(confirmPasswordInput)) {
            textInputPassword.setError("Пароли должны соответствовать");
            return false;
        }else {
            textInputPassword.setError("");
            return true;
        }
    }

    public void loginSozdatUser(View view) {

        if (!validateEmail() | !validateName() | !validatePassword()){
            return;
        }

        if (isLoginActive){
            auth.signInWithEmailAndPassword(
                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText()
                            .toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                startActivity(new Intent(
                                        NapisatOtziv.this,
                                        OstavitOtzivActivity.class
                                ));
                                FirebaseUser user = auth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(NapisatOtziv.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else {
            if (!validateEmail() | !validateName() | !validatePassword() |
                    !validateConfirmPassword()){
                return;
            }
            auth.createUserWithEmailAndPassword(
                    textInputEmail.getEditText().getText().toString().trim(),
                    textInputPassword.getEditText().getText()
                            .toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = auth.getCurrentUser();
                                startActivity(new Intent(
                                        NapisatOtziv.this,
                                        OstavitOtzivActivity.class
                                ));
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(NapisatOtziv.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }

    public void loginVoitiUser(View view) {

        if (isLoginActive){
            isLoginActive = false;
            loginSozdatButton.setText("Зарегистрироваться");
            loginVoitiTextView.setText("Или войти");
            textInputConfirmPassword.setVisibility(View.VISIBLE);
        } else{
            isLoginActive = true;
            loginSozdatButton.setText("Войти");
            loginVoitiTextView.setText("или зарегистрироваться");
            textInputConfirmPassword.setVisibility(View.GONE);
        }
    }
}