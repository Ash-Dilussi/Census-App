package com.example.mc2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private static final String PASSWORD_PREF_KEY = "password";
    private static final String KEY_USER= "user";

    private EditText passwordEditText,userEdit;
    private TextView username;
    private Button loginButton,logoutButton;
    private SharedPreferences sharedPreferences;

    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        passwordEditText = findViewById(R.id.txtpassword);
        userEdit = findViewById(R.id.txtuserid);
        loginButton = findViewById(R.id.btnlogin);
        username = findViewById(R.id.usertext);
        logoutButton = findViewById(R.id.btnlogout);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.contains(PASSWORD_PREF_KEY)) {
            // Ask the user to set a new password
            passwordEditText.setHint("Enter a new password");
            username.setText("Welcome !");
            userEdit.setHint("Enter your Name");
           logoutButton.setVisibility(View.GONE);
            loginButton.setText("Register");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = passwordEditText.getText().toString();
                    String userid = userEdit.getText().toString();

                    sharedPreferences.edit().putString(PASSWORD_PREF_KEY, password).apply();
                    sharedPreferences.edit().putString(KEY_USER, userid).apply();
                    startHomeScreen();
                }
            });
        } else {
            // Ask the user to enter the password to login
            passwordEditText.setHint("Enter your password");
            userEdit.setVisibility(View.INVISIBLE);
            String usernamev = sharedPreferences.getString("user","");

            username.setText("Welcome  "+ usernamev);
            loginButton.setText("Login");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = passwordEditText.getText().toString();
                    String savedPassword = sharedPreferences.getString(PASSWORD_PREF_KEY, "");
                    if (password.equals(savedPassword)) {
                        count =0;
                        passwordEditText.setText("");
                        startHomeScreen();


                    } else {
                        count++;
                        Toast.makeText(LoginActivity.this, "Incorrect password. Please try again. ( " + count+ " )", Toast.LENGTH_SHORT).show();
                        passwordEditText.setHint("Re-enter password");



                        if (count >= 3) {
                            Toast.makeText(LoginActivity.this,"something went wrong",Toast.LENGTH_LONG).show();
                            System.exit(1);
                        }
                    }
                }
            });
        }


        logoutButton.setText("Logout");
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().clear().commit();

                Toast.makeText(LoginActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();

                Intent intent =new  Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void startHomeScreen() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeScreen.class);
        startActivity(homeIntent);

    }
    }
