package com.example.djamel.salate;

import android.app.PendingIntent;

/**
 * Created by djamel on 02/01/2018.
 */


    public class ListItem {
    public String nom;
    public String h;
    public String imag;
    PendingIntent mNotificationReceiverPendingIntent ;

    public String date;

    public ListItem(String nom, String h, String imag,PendingIntent mNotificationReceiverPendingIntent ) {
        this.nom = nom;
        this.h = h;
        this.imag = imag;
         this.mNotificationReceiverPendingIntent= mNotificationReceiverPendingIntent ;

    }
}

