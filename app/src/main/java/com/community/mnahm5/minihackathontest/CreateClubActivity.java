package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CreateClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
    }

    public void addclubClicked(View view){
        TextView clubname = (TextView) findViewById(R.id.etClubName);
        String current_user = ParseUser.getCurrentUser().getUsername();
        Log.i("Test", "clubname is " + clubname.getText().toString() + " username is " + current_user);
        ParseObject clubs = new ParseObject("Clubs");
        clubs.put("clubName", clubname.getText().toString());
        List<String> admins = new ArrayList<>();
        admins.add(current_user);
        clubs.put("admins", admins);
        clubs.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Club Created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), StudentHomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.i("Error", e.getMessage());
                }
            }
        });
    }

}
