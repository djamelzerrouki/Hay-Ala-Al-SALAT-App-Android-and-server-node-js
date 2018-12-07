package com.example.djamel.salate;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
 import android.content.Intent;
import  android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Home extends AppCompatActivity {
    private Socket mSocket;
    Switch aSwitch;
      AlarmManager mAlarmManager;
      Intent mNotificationReceiverIntent ;
      PendingIntent mNotificationReceiverPendingIntent ;
    private static final long INITIAL_ALARM_DELAY =  10 * 1000L;
public static String h1=null;

    public   int[] getTimeOfCalander(String h){
       if (h!=null){
        String[] tokens = h.split(":");       // Single blank is the separator.
        int[] index = new int[tokens.length];
        int i=0;
        for (String val : tokens) {
            index[i] =Integer.parseInt(val);
            //	System.out.println(index[i]);
            i++;

        }
           return index;
       }else{
           Toast.makeText(this,"la reqet et vide ",Toast.LENGTH_LONG).show();
           return null;
       }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get the AlarmManager Service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create PendingIntent to start the AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(Home.this,
                AlarmNotificationReceiver.class);
        mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
                Home.this, 0, mNotificationReceiverIntent, 0);


        aSwitch=(Switch)findViewById(R.id.simpleSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()  {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                   if (h1!=null){
                    aSwitch.setText("ON");  //To change the text near to switch
                    Calendar calendar = Calendar.getInstance();
                    //  calendar.setTimeInMillis(System.currentTimeMillis());
                    int[] tab=  getTimeOfCalander(h1);
                    calendar.set(Calendar.HOUR_OF_DAY, tab[0]);
                    calendar.set(Calendar.MINUTE,tab[1] );
                    calendar.set(Calendar.SECOND, tab[2]);

                    mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            mNotificationReceiverPendingIntent);
                    Toast.makeText(getApplicationContext(), "Single Alarm Set",
                            Toast.LENGTH_LONG).show();}
                            else {
                       aSwitch.setChecked(false);

                       aSwitch.setText("OFF");
                       Toast.makeText(getApplicationContext(),
                               "selact date svp !", Toast.LENGTH_LONG).show();
                   }
                 }
                else {


                    aSwitch.setText("OFF");  //To change the text near to switch

                    mAlarmManager.cancel(mNotificationReceiverPendingIntent);

                    Toast.makeText(getApplicationContext(),
                            "Repeating Alarms Cancelled", Toast.LENGTH_LONG).show();


                }
            }
        });





        try {
            mSocket = IO.socket("http://192.168.1.107:3000");
            Log.e("CONNECTED", "SUCCESS");
        } catch (URISyntaxException e) {
        }
        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        if (mSocket != null)
            mSocket.connect();
    }

    public void getTime(View view) {

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        PopActivity popActivity=new PopActivity();
        popActivity.show(transaction,null);
    }

    public  void getDatesalat(String s){



        Emitter.Listener login = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        JSONArray jArray = null;
                        try {
                            jArray = new JSONArray(args[0].toString());
                            Toast.makeText(Home.this, "length :" , Toast.LENGTH_SHORT).show();

                            if (jArray.length() > 0) {
                                String id = null, name = null ;
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    id = json_data.getString("id").toString();
                                    name = json_data.getString("jour").toString();
                                    h1 = json_data.getString("h1").toString();
                                    // login station =new  login(id,name,password);
                                    // liststation.add(station);
                                    Toast.makeText(Home.this, "Bienvenue :" + name, Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(Home.this, "Bienvenue :" + h1, Toast.LENGTH_SHORT).show();


                            } else {


                                Toast.makeText(Home.this, "S'il te plaît essayer une autre fois!", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

            }

        };


        mSocket.on("Respanse_iffiest", login);
        mSocket.emit("timesalat", s);
        System.out.println("######");



    }

    void updatedate(String date){
     EditText editText=  (EditText) findViewById(R.id.editText);
     editText.setText(date);
    }


}
