package com.fit2081.viettran_33810672_fit2081_a2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2081.viettran_33810672_fit2081_a1.R;

public class LoginActivity extends AppCompatActivity {

    // Declare global variables for Username and Password in the Login page
    EditText etLogUsername, etLogPassword;
    String Username, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Username = getIntent().getExtras().getString("username");
        Password = getIntent().getExtras().getString("password");
        etLogUsername = findViewById(R.id.etLogUsername);
        etLogPassword = findViewById(R.id.etLogPassword);
        etLogUsername.setText(Username);

    }


    public void onRegisterClick(View view){
        finish();
        Toast.makeText(this, "Back to register page", Toast.LENGTH_SHORT).show();
    }

    public void onLoginClick(View view){
        String LogUsernameEt = etLogUsername.getText().toString();
        String LogPasswordEt = etLogPassword.getText().toString();

        if (!LogUsernameEt.equals(Username) || !LogPasswordEt.equals(Password)){
            Toast.makeText(this,"Incorrect username or password!", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent DashBoard = new Intent(this, DashBoardActivity.class);
            Toast.makeText(this, "Successful Login!", Toast.LENGTH_SHORT).show();
            startActivity(DashBoard);
        }
        etLogPassword.getText().clear();
    }
}