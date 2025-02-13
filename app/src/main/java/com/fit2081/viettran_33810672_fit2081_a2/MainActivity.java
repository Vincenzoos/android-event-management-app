package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2081.viettran_33810672_fit2081_a1.R;

public class MainActivity extends AppCompatActivity {

    // Declare global variables for Username and Password in the Register page
    EditText etUsername, etPassword, etConfPassword;

    public static final String USERNAME_KEY ="username_key";
    public static final String PASSWORD_KEY = "password_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set global variables to the relevant UI elements in the Register page
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfirmPassword);


    }

    public void onRegisterClick(View view){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sP = getPreferences(MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sP.edit();

        // Fetch Username value and Password value into its corresponding variables
        String Username =etUsername.getText().toString();
        String Password =etPassword.getText().toString();
        String ConfPassword = etConfPassword.getText().toString();


        // Display message for registration status
        if (!Password.equals(ConfPassword)){
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        }
        else if (Username.isEmpty() || Password.isEmpty()) {
            Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent login = new Intent(this, LoginPage.class);
            login.putExtra("username", Username);
            login.putExtra("password", Password);

            // put all data into SharedPreferences
            editor.putString(USERNAME_KEY,Username);
            editor.putString(PASSWORD_KEY,Password);
            editor.apply();

            // Clear all input fields
            etUsername.getText().clear();
            etPassword.getText().clear();
            etConfPassword.getText().clear();

            Toast.makeText(this, "Successful Registration!", Toast.LENGTH_SHORT).show();
            startActivity(login);
        }
    }

    public void onLoginClick(View view){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sP = getPreferences(MODE_PRIVATE);
        String Username = sP.getString(USERNAME_KEY,"");
        String Password = sP.getString(PASSWORD_KEY,"");
        Intent login = new Intent(this, LoginPage.class);
        login.putExtra("username", Username);
        login.putExtra("password", Password);
        Toast.makeText(this, "Go to Login page", Toast.LENGTH_SHORT).show();
        startActivity(login);
    }
}