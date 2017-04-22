package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseACL;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseSetup();

        setContentView(R.layout.activity_starter);
    }

    private void ParseSetup()
    {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("51757916800afeba27b718e6e9baeff132944e60")
                .clientKey("b84609db03fa6ad0ad1899b372de38829a3ba1c8")
                .server("http://ec2-54-252-158-169.ap-southeast-2.compute.amazonaws.com:80/parse/")
                .build()
        );

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
