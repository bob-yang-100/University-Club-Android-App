package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void Cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void SignUp(View view) {
        //Intent intent = new Intent(getApplicationContext(), StudentHomeActivity.class);
        //startActivity(intent);

        final EditText etStudentId = (EditText) findViewById(R.id.etStudentID);
        final EditText etFullName = (EditText) findViewById(R.id.etFullName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        ParseUser user = new ParseUser();
        user.setUsername(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.put("fullName", etFullName.getText().toString());
        user.put("studentID", etStudentId.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), StudentHomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.i("Sign up", e.getMessage());
                }
            }
        });
    }
}
