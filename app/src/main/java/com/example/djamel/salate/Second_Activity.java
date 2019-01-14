package com.example.djamel.salate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

public class Second_Activity extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        pdfView=(PDFView) findViewById(R.id.PDFView);
        pdfView.fromAsset("coran.pdf").load();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.simpel_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"لقد قمت باختيار المصحف الكريم",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
//                Intent intentMap = new Intent(this, Home.class);
//                this.startActivity(intentMap);
                super.onBackPressed();
                Toast.makeText(getApplicationContext(),"العوده الى الصفحه الرئيسيه",Toast.LENGTH_LONG).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
