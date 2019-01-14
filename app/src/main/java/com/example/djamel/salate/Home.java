package com.example.djamel.salate;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
 import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import  android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
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

   //my Socket
    private Socket mSocket;

    // AlarmManager public
       AlarmManager mAlarmManager;

       // Intent pour notification d'alarm
      Intent mNotificationReceiverIntent;

      // PendingIntent pour notification d'alarm
 PendingIntent mNotificationReceiverPendingIntent ;
  // ListView la liste de  5 aw9at de salawat  [ sobh dohr 3asr marrab icha ]
    public static ListView ls;

    // pour chaque items de la list ls
    ArrayList<ListItem> Items=new ArrayList<ListItem>();

     // public static les Noms de 5 salawat  de h{1..5} qui represont le tomps  wa9t kol salat " kol h{1..5}"
public static String h1=null,h2=null,h3=null,h4=null,h5=null;
// onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ls=(ListView) findViewById(R.id.listView);

        // Get the AlarmManager Service
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Create PendingIntent to start the AlarmNotificationReceiver
        mNotificationReceiverIntent = new Intent(Home.this,
                AlarmNotificationReceiver.class);
      /*  try {
            // 169.254.135.165
            // 192.168.1.102
            mSocket = IO.socket("http://10.0.2.2:3000");
            Log.e("CONNECTED", "SUCCESS");
        } catch (URISyntaxException e) {
        }*/
        mSocket= ConnectionNodeJs.connectionNodeJs();
        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        if (mSocket != null)
            mSocket.connect();
    }

    // input: "Action"  la Selaction de ladate de JOUR ...  popActivity
    public void getTime(View view) {

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        PopActivity popActivity=new PopActivity();
        popActivity.show(transaction,null);
    }
//

    //
    // Get time of 5 salawat:
    //Apris la conection avice le server va domonder l' Action mSocket.emit("timesalat", s);
    //   String s represonter la date " le jour  selectioner ..."

    public  void getDatesalat(final String s){
        Emitter.Listener list5Salawat = getListenerListTileSalatSrerverNodeJS(s);
  // get "on" :r espons of server NODE JS
        mSocket.on("Respanse_iffiest", list5Salawat);
        // set "emit" : requet  of server NODE JS
        mSocket.emit("timesalat", s);
        System.out.println("######");

    }

    @NonNull
    private Emitter.Listener getListenerListTileSalatSrerverNodeJS(final String s) {
        // list5Salawat Action respons for server node js after emit executer actio on
//         mSocket.on("Respanse_iffiest", list5Salawat);
        return new Emitter.Listener() {
                 @Override
                 public void call(final Object... args) {

                     runOnUiThread(new Runnable() {

                         @Override
                         public void run() {
     // la table qui  qui reterner data on format JSON
                             JSONArray jArray = null;
                             try {
                                 //qui reterner data on format  JSON  et casting to String "text"...
                                 // args item of Object in method call
                                 jArray = new JSONArray(args[0].toString());
      // condition if exict oen result in this data with JSON
                                 if (jArray.length() > 0) {
                                     String id = null, date = null ;
                                     // Siprremer data  aven charger
                                     Items.clear();
                                     // for loop  to my data
                                     for (int i = 0; i < jArray.length(); i++) {
                                         JSONObject json_data = jArray.getJSONObject(i);
                                         id = json_data.getString("id").toString();
                                         date = json_data.getString("jour").toString();
                                         h1 = json_data.getString("h1").toString();
                                         h2 = json_data.getString("h2").toString();
                                         h3 = json_data.getString("h3").toString();
                                         h4= json_data.getString("h4").toString();
                                         h5 = json_data.getString("h5").toString();
                                         Toast.makeText(Home.this, "اليوم اللذي تم إختياره" + s, Toast.LENGTH_SHORT).show();
                                     }
     //                                Items.add(new ListItem("الفجر",h1,"",PendingIntent.getBroadcast(
     //                                        Home.this, 0, mNotificationReceiverIntent, 0)));
     //
                                     mNotificationReceiverIntent.putExtra("name","الفجر");
                                     Items.add(new ListItem("الفجر",h1,"",PendingIntent.getBroadcast(
                                             Home.this, 1, mNotificationReceiverIntent, 0)));
                                     mNotificationReceiverIntent.putExtra("name","الظهر");
                                     Items.add(new ListItem("الظهر",h2,"",PendingIntent.getBroadcast(
                                             Home.this, 2, mNotificationReceiverIntent, 0)));
                                     mNotificationReceiverIntent.putExtra("name","العصر");
                                     Items.add(new ListItem("العصر",h3,"",PendingIntent.getBroadcast(
                                             Home.this, 3, mNotificationReceiverIntent, 0)));
                                     mNotificationReceiverIntent.putExtra("name","المغرب");
                                     Items.add(new ListItem("المغرب",h4,"",PendingIntent.getBroadcast(
                                             Home.this, 4, mNotificationReceiverIntent, 0)));
                                     mNotificationReceiverIntent.putExtra("name","العشاء");
                                     Items.add(new ListItem("العشاء",h5,"",PendingIntent.getBroadcast(
                                             Home.this, 5, mNotificationReceiverIntent, 0)));

                                     // Remplir  la liste de Items la ListView with Adapter
                                     final MyCustomAdapter myadpter= new MyCustomAdapter(Items);
                                     ls.setAdapter(myadpter);
       } else {

                                     Toast.makeText(Home.this, "اليوم "+s+" اللذي تم إختياره غير موجود حاليا !", Toast.LENGTH_SHORT).show();

                                 }


                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }

                     });

                 }

             };
    }

    // Mtethodof Update text Date
    void updatedate(String date){
     EditText editText=  (EditText) findViewById(R.id.editText);
     editText.setText(date);


    }

    // To change text time "HH:MM:SS" To tabl of int [HH,MM,SS]
    public   int[] getTimeOfCalander(String h){
        if (h!=null){
            String[] tokens = h.split(":");       // Single blank is the separator.
            int[] index = new int[tokens.length];
            int i=0;
            for (String val : tokens) {
                index[i] =Integer.parseInt(val);
                 i++;
   }
            return index;
        }else{
            Toast.makeText(this,"la reqet et vide ",Toast.LENGTH_LONG).show();
            return null;
        }
    }




// Class Adepter for ListItems Aw9at AL_Salat
    class MyCustomAdapter extends BaseAdapter
    {
        ArrayList<ListItem> Items=new ArrayList<ListItem>();
        MyCustomAdapter(ArrayList<ListItem> Items ) {
            this.Items=Items;

        }

        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position).date;

        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(final int in, View view, ViewGroup viewGroup) {

            LayoutInflater linflater =getLayoutInflater();
            View view1=linflater.inflate(R.layout.row_view, null);
            final AlarmManager[] alarmManager = new AlarmManager[1];
            final PendingIntent[] pendingIntentSALAT = new PendingIntent[1];
            final TextView txtname =(TextView) view1.findViewById(R.id.nom);
            final  TextView txthur =(TextView) view1.findViewById(R.id.h);
            final ImageView imag =(ImageView) view1.findViewById(R.id.imageView);
            final PendingIntent pendingIntent ;
            final Switch Switchservice =(Switch) view1.findViewById(R.id.simpleSwitch);

             txthur.setText( Items.get(in).h);
            txtname.setText(Items.get(in).nom);
            pendingIntent=Items.get(in).mNotificationReceiverPendingIntent;


            // Action Listener Switch of Switchservice Off and On
            Switchservice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()  {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  //  Bundle noBundle = new Bundle();
                   // noBundle.putString("name", Items.get(in).nom);//This is the value I want to pass
                  //  noReceive.putExtras(noBundle);
// Condition IF  Switchservice Checked ===> ON
                    if (isChecked) {
                        //To c"hange the text near to switch

                        Switchservice.setText("On");
                        imag.setImageResource(R.drawable.perm_group_device_alarms);

                        Calendar calendar = Calendar.getInstance();
                        //  calendar.setTimeInMillis(System.currentTimeMillis());
                        int[] tab = getTimeOfCalander(Items.get(in).h);
                        calendar.set(Calendar.HOUR_OF_DAY, tab[0]);
                        calendar.set(Calendar.MINUTE, tab[1]);
                        calendar.set(Calendar.SECOND, tab[2]);
// Lanch  Servic ALARM ...

                        // WITH RTC_WAKEUP
                      //  mNotificationReceiverIntent.putExtra("name","magreb");
                         mAlarmManager.set(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(),
                                pendingIntent);


                        Toast.makeText(getApplicationContext(), "تفعيل وقت صلاة "+Items.get(in).nom+"\n على الساعة "+Items.get(in).h,
                                Toast.LENGTH_LONG).show();
                    }
// Condition ELSE __ IF  Switchservice NO__Checked ===> OFF
                    else {
                        //To change the text near to switch
                        Switchservice.setText("Off");
                        imag.setImageResource(R.drawable.perm_group_system_clock);
// Stoped  Servic ALARM ...
                        // WITH RTC_WAKEUP
                        mAlarmManager.cancel( pendingIntent);

                        Toast.makeText(getApplicationContext(),
                                "إزالة تفعيل وقت صلاة "+Items.get(in).nom+"\n على الساعة "+Items.get(in).h, Toast.LENGTH_LONG).show();


                    }
                }
            });

            return view1;

        }

    }



}
