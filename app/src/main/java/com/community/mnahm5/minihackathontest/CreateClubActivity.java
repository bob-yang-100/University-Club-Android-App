package com.community.mnahm5.minihackathontest;

import android.app.ListActivity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        List<ParseObject> returned_object;
        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Clubs");
            query.orderByAscending("clubName");
            try {
                returned_object = query.find();
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ListView listview = (ListView) findViewById(R.id.lvClubView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateClubActivity.this, android.R.layout.simple_list_item_1);
            for (ParseObject club : returned_object) {
                adapter.add((String) club.get("clubName"));
            }
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                }
            });
        }
    }

    public void addclubClicked(View view){
        TextView clubname = (TextView) findViewById(R.id.etClubName);
        String current_user = ParseUser.getCurrentUser().getUsername();
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
