package com.example.djamel.salate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PopActivity extends  DialogFragment implements View.OnClickListener {
    View view;
    Button button;
    DatePicker datePicker;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.pop_activity,container,false);

        button=(Button) view.findViewById(R.id.btn1);
        datePicker=(DatePicker) view.findViewById(R.id.date);
button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
        String date=datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
 Home home=(Home) getActivity();
 home.getDatesalat(date);
 home.updatedate(date);



 }
}
