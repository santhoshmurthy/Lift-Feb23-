package com.LAW.lift.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LAW.lift.R;
import com.LAW.lift.app.LiftApplication;

public class ContactusFragment extends Fragment {
    public static ContactusFragment newInstance() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contactus, container, false);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

    }


    }



