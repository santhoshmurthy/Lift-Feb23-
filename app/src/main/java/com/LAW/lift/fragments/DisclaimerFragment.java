package com.LAW.lift.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LAW.lift.R;
import com.LAW.lift.app.LiftApplication;

public class DisclaimerFragment extends Fragment {



    public static DisclaimerFragment newInstance() {
       /* DisclaimerFragment fragmentStarter = new DisclaimerFragment();

        Bundle args = new Bundle();

        fragmentStarter.setArguments(args);*/
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        android.app.ActionBar actionbar = getActivity().getActionBar();
        assert actionbar != null;
        actionbar.setTitle(getResources().getString(R.string.Disclaimer));

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.aimdisclaimer, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LiftApplication.getInstance().trackScreenView("Disclaimer");
    }
}
