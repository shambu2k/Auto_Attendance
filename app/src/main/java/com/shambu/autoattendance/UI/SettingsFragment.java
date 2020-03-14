package com.shambu.autoattendance.UI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.shambu.autoattendance.Interfaces.GeofencingServiceTogglerInterface;
import com.shambu.autoattendance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class SettingsFragment extends Fragment {


    private GeofencingServiceTogglerInterface ginterface;
    private SharedPreferences pref;

    @BindView(R.id.toggleGeofencing)
    Switch toggle;

    @BindView(R.id.changeLoc_ll)
    LinearLayout changeloc_ll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        ginterface = (GeofencingServiceTogglerInterface) getActivity();
        pref = getContext().getSharedPreferences("AutoAtt", 0);
        if(pref.getString("GeofencingService", "ON").equals("ON")){
            toggle.setChecked(true);
        } else if(pref.getString("GeofencingService", "ON").equals("OFF")){
            toggle.setChecked(false);
        }


        return view;
    }

    @OnCheckedChanged(R.id.toggleGeofencing)
    void toggleService(){
        if(toggle.isChecked()){
            ginterface.startGService();
        } else {
            ginterface.stopGService();
        }
    }

    @OnClick(R.id.changeLoc_ll)
    void startSelectLocAct(){
        Intent intent = new Intent(getContext(), SelectClassLocation.class);
        startActivity(intent);
    }

}
