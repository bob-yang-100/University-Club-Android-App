package com.community.mnahm5.minihackathontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ClubSettingsActivity extends AppCompatActivity {

    private ParseObject club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_settings);

        final EditText etDescription = (EditText) findViewById(R.id.etDescription);
        final EditText etFees = (EditText) findViewById(R.id.etFees);
        final EditText etDiscount = (EditText) findViewById(R.id.etDiscount);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clubs");
        ArrayList<String> admins = new ArrayList<>();
        admins.add(ParseUser.getCurrentUser().getUsername());
        query.whereContainedIn("admins", admins);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    club = objects.get(0);
                    setTitle(club.getString("clubName") + " Settings");
                    if (club.getString("description") != null) {
                        etDescription.setText(club.getString("description"));
                    }
                    if (club.getString("fees") != null) {
                        etFees.setText(club.getString("fees"));
                    }
                    if (club.getString("discount") != null) {
                        etDiscount.setText(club.getString("discount"));
                    }
                }
            }
        });
    }

    public void AddUser(View view) {
        final EditText etAddAdmin = (EditText) findViewById(R.id.etAddAdmin);
        ParseQuery query = new ParseQuery("User");
        query.whereEqualTo("username", etAddAdmin.getText().toString());
        
    }
}
