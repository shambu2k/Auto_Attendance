package com.shambu.autoattendance.UI;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shambu.autoattendance.GeofencingServiceTogglerInterface;
import com.shambu.autoattendance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends Fragment {


    private GeofencingServiceTogglerInterface ginterface;
    private SharedPreferences pref;
    @BindView(R.id.toggleGeofencing)
    Button toggle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        ginterface = (GeofencingServiceTogglerInterface) getActivity();
        pref = getContext().getSharedPreferences("AutoAtt", 0);
        if(pref.getString("GeofencingService", "ON").equals("ON")){
            toggle.setText("STOP GEOFENCING SERVICE");
        } else if(pref.getString("GeofencingService", "ON").equals("OFF")){
            toggle.setText("START GEOFENCING SERVICE");
        }


        return view;
    }

    @OnClick(R.id.toggleGeofencing)
    void toggleService(){
        if(toggle.getText().toString().equals("START GEOFENCING SERVICE")){
            toggle.setText("STOP GEOFENCING SERVICE");
            ginterface.startGService();
        } else if(toggle.getText().toString().equals("STOP GEOFENCING SERVICE")){
            toggle.setText("START GEOFENCING SERVICE");
            ginterface.stopGService();
        }
    }

}
