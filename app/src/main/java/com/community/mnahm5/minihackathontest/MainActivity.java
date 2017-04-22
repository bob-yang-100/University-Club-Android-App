package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

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
        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
    }
}
