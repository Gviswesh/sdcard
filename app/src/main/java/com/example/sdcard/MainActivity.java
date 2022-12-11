package com.example.sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File f = new File("/sdcard/new.txt");

        try {
            f.createNewFile();
        } catch (IOException e) {e.printStackTrace();}

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){

                Intent i = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri u = Uri.fromParts("package", getPackageName(), null);
                i.setData(u);
                startActivity(i);

            }

        }
        else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);


        }

        EditText et1= findViewById(R.id.et1);
        findViewById(R.id.re).setOnClickListener(v->{

            try{
                FileReader r = new FileReader("/sdcard/m.txt");
                BufferedReader br = new BufferedReader(r);
                String x;
                while((x = br.readLine()) != null)
                    et1.append(x);
                br.close();
                r.close();

            }catch(FileNotFoundException e) {
                Toast.makeText(this,"File not found",Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.wr).setOnClickListener(v->{
            try{
                FileWriter r = new FileWriter("/sdcard/m.txt");
                r.write(et1.getText().toString());
                r.close();

            }catch(FileNotFoundException e){
                Toast.makeText(this,"File not found", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this,"Can't Write the file", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.clr).setOnClickListener(v->et1.setText(""));
        //findViewById(R.id.button4).setOnClickListener(v->{
            //Intent i = new Intent(this,MainActivity2.class);
           // startActivity(i);
        //});
    }


}