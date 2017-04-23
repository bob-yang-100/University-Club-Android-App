package com.community.mnahm5.minihackathontest;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ClubSettingsActivity extends AppCompatActivity {

    private ParseObject club;
    private List<String> admins;
    private EditText etDescription;
    private EditText etFees;
    private EditText etDiscount;
    private Spinner spCurrentUsers;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_settings);

        etDescription = (EditText) findViewById(R.id.etDescription);
        etFees = (EditText) findViewById(R.id.etFees);
        etDiscount = (EditText) findViewById(R.id.etDiscount);
        spCurrentUsers = (Spinner) findViewById(R.id.spCurrentUsers);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clubs");
        final ArrayList<String> admins = new ArrayList<>();
        admins.add(ParseUser.getCurrentUser().getUsername());
        query.whereContainedIn("admins", admins);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    club = objects.get(0);
                    updateUI();
                }
            }
        });
    }

    public void AddUser(View view) {
        final EditText etAddAdmin = (EditText) findViewById(R.id.etAddAdmin);
        final String usernameOfNewAdmin = etAddAdmin.getText().toString();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", usernameOfNewAdmin);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() <= 0) {
                    Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show();
                }
                else if (admins.contains(usernameOfNewAdmin) || usernameOfNewAdmin.equals(ParseUser.getCurrentUser().getUsername())) {
                    Toast.makeText(getApplicationContext(), "User already an admin", Toast.LENGTH_LONG).show();
                }
                else {
                    admins.add(usernameOfNewAdmin);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "User added", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void RemoveAdmin(View view) {
        if (!spCurrentUsers.getSelectedItem().toString().equals("Current Admins")) {
            admins.remove(spCurrentUsers.getSelectedItem().toString());
            adapter.notifyDataSetChanged();
        }
    }

    public void SaveChanges(View view) {
        club.put("description", etDescription.getText().toString());
        club.put("fees", etFees.getText().toString());
        club.put("discount", etDiscount.getText().toString());
        admins.remove("Current Admins");
        admins.add(ParseUser.getCurrentUser().getUsername());
        club.put("admins", admins);
        club.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), StudentHomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void updateUI() {
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
        admins = club.getList("admins");
        admins.remove(ParseUser.getCurrentUser().getUsername());
        admins.add(0, "Current Admins");
        adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, admins);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrentUsers.setAdapter(adapter);
    }
}
