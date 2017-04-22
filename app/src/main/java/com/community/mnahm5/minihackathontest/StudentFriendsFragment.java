package com.community.mnahm5.minihackathontest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFriendsFragment extends Fragment implements View.OnClickListener {


    public StudentFriendsFragment() {
        // Required empty public constructor
    }


    public static StudentFriendsFragment newInstance() {
        return new StudentFriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_friends, container, false);

        Button b = (Button) view.findViewById(R.id.button2);
        b.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button2) {
            doSearch(v);
        }
    }

    public void doSearch(View view) {

        EditText editText = (EditText) getView().findViewById(R.id.editText2);
        String buddy = editText.getText().toString();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContains("fullName", buddy);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {

                    ListView listview = (ListView) getView().findViewById(R.id.lvClubView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    for (int i = 0; i < objects.size(); i++) {
                        adapter.add((String) objects.get(i).getUsername());
                    }
                    listview.setAdapter(adapter);

                }
            }
        });
    }
}
