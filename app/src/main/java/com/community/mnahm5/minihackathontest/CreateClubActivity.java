package com.community.mnahm5.minihackathontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

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
        clubs.put("admins", current_user);
    }

}
