package com.community.mnahm5.minihackathontest;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClubListFragment extends Fragment implements View.OnClickListener {


    public ClubListFragment() {
        // Required empty public constructor
    }


    public static ClubListFragment newInstance() {
        return new ClubListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_list, container, false);

        Button b = (Button) view.findViewById(R.id.button);
        b.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            addclubClicked(v);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ClubListFragment.RemoteDataTask().execute();
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
            ListView listview = (ListView) getView().findViewById(R.id.lvClubView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
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
        TextView clubname = (TextView) getView().findViewById(R.id.etClubName);
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
                    Toast.makeText(getContext(), "Club Created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), StudentHomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.i("Error", e.getMessage());
                }
            }
        });
    }

}
