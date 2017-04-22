package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import static com.community.mnahm5.minihackathontest.R.id.etUsername;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signupClicked(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginClicked(View view){
        EditText etusername = (EditText) findViewById(R.id.etUsername);
        EditText etpassword = (EditText) findViewById(R.id.etPassword);
        final String username = etusername.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Username/Password can't be empty", Toast.LENGTH_LONG).show();
        }
        else{
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null){
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), StudentHomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Username/Password is wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
