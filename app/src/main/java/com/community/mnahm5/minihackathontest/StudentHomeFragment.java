package com.community.mnahm5.minihackathontest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentHomeFragment extends Fragment {


    public StudentHomeFragment() {
        // Required empty public constructor
    }

    public static StudentHomeFragment newInstance() {
        StudentHomeFragment fragment = new StudentHomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        TextView tv = (TextView) view.findViewById(R.id.tvWelcome);
        String message = ("Welcome, " + ParseUser.getCurrentUser().getString("fullName") + "!");
        tv.setText(message);

        return view;
    }


}
