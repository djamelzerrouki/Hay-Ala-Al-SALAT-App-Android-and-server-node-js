package com.example.djamel.salate;

import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by djamel on 04/01/2018.
 */

public class ConnectionNodeJs {

    private static Socket mSocket;


    ConnectionNodeJs() {

    }

    public static Socket connectionNodeJs() {
        try {
           // mSocket = IO.socket("http://192.168.1.108:3000");
//http://10.0.2.2:3000
            //http://192.168.1.109:3000
            mSocket = IO.socket("http://192.168.1.100:3000");
            Log.e("CONNECTED", "SUCCESS");
        } catch (URISyntaxException e) {
            Toast.makeText(null, "URISyntaxException", Toast.LENGTH_SHORT).show();

        }

        if (mSocket != null)
            mSocket.connect();
        return mSocket;
    }

}
