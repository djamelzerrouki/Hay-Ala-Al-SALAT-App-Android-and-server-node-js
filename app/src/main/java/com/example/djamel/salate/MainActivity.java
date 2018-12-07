package com.example.djamel.salate;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
 import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    private Socket mSocket;
    public static String id_station;
public static  boolean bool=false ;
    Button test;
    final ArrayList<login> liststation = new ArrayList<login>();

    EditText txt1;
    EditText txt2;
    Calendar calender;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //test = (Button) findViewById(R.id.btn);
        txt1 = (EditText) findViewById(R.id.userEmail);
        txt2 = (EditText) findViewById(R.id.userPassword);
        //  GetEmployees();

        try {
            mSocket = IO.socket("http://192.168.1.107:3000");
            Log.e("CONNECTED", "SUCCESS");
        } catch (URISyntaxException e) {
        }
        //   Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        if (mSocket != null)
            mSocket.connect();
    }


    public void test(View view) {
        String s0 = txt1.getText().toString();
        String s1 = txt2.getText().toString();


        Emitter.Listener login = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        JSONArray jArray = null;
                        try {
                             jArray = new JSONArray(args[0].toString());
                            Toast.makeText(MainActivity.this, "length :" , Toast.LENGTH_SHORT).show();

                            if (jArray.length() > 0) {
                             String id = null, name = null, password = null ;
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    id = json_data.getString("id").toString();
                                    name = json_data.getString("name").toString();
                                    password = json_data.getString("password").toString();
                                     id_station=id;
                                   // login station =new  login(id,name,password);
                                    // liststation.add(station);
                                    bool=true;
                                    Toast.makeText(MainActivity.this, "Bienvenue :" + name, Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(MainActivity.this, "Bienvenue :" + password, Toast.LENGTH_SHORT).show();
                             stertActivty(id, name, password);


                            } else {
                                bool=false;
                                txt1.setText(null);
                                txt2.setText(null);
                                Toast.makeText(MainActivity.this, "S'il te plaît essayer une autre fois!", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });

            }

        };


        mSocket.on("Respanse_ifExist", login);
        mSocket.emit("isexist", s0, s1);
        System.out.println("######");
    }

    public void stertActivty(String id, String nom, String password) {
        Toast.makeText(this, "djamale jimmi sur NAFTAL...", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Home.class);
        Bundle b = new Bundle();
        b.putString("id", id);
        b.putString("usr", nom);


        intent.putExtras(b);
        startActivity(intent);


        Toast.makeText(this, "djamale jimmi sur NAFTAL...", Toast.LENGTH_LONG).show();
    }


}


