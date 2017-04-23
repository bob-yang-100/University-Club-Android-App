package com.community.mnahm5.minihackathontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class ClubHomeActivity extends AppCompatActivity {
    private ParseObject update_row;
    private List<String> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_home);

        Bundle bundle = getIntent().getExtras();
        String clubname = bundle.getString("CLUBNAME");
        displayClubDetails(clubname);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clubs");
        query.whereEqualTo("clubName", clubname);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    members = objects.get(0).getList("members");
                    Log.i("test", "members " + members.size() + " length " + objects.size());
                    members.add(ParseUser.getCurrentUser().getUsername());
                    Log.i("test", "members " + members.size());
                    update_row = objects.get(0);
                }
            }
        });

    }

    public void displayClubDetails(String clubname){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clubs");
        query.whereEqualTo("clubName", clubname);
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        TextView club_welcome = (TextView) findViewById(R.id.tvClubWelcome);
                        TextView club_about = (TextView) findViewById(R.id.tvClubAbout);
                        List<String> list_of_memmbers = objects.get(0).getList("members");
                        TextView club_join_price = (TextView) findViewById(R.id.tvPrice);
                        Button join = (Button) findViewById(R.id.btJoinClub);

                        String welcome_message = "Welcome to " + objects.get(0).getString("clubName");
                        club_welcome.setText(welcome_message);
                        club_about.setText(objects.get(0).getString("description"));

                        if(!list_of_memmbers.contains(ParseUser.getCurrentUser().getUsername())){
                            club_join_price.setText("$"+objects.get(0).getString("fees"));
                            club_join_price.setVisibility(View.VISIBLE);
                            join.setVisibility(View.VISIBLE);
                        }
                        else{
                            club_join_price.setVisibility(View.INVISIBLE);
                            join.setVisibility(View.INVISIBLE);
                        }

                    }
                }
            }
        });
    }

    public void joinClubClicked(View view){
        update_row.put("members", members);
        if(update_row != null) Log.i("test", "found object");
        update_row.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getApplicationContext(), "Joined Club", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.i("fail", "failed " + e.getMessage());
                }
            }
        });
    }
}
