package com.example.mragil.nodemcu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.pusher.pushnotifications.PushNotifications;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button kamarOn;
    Button kamarOff;
    Button tidurOn;
    Button tidurOff;
    TextView kondisiKamar;
    TextView kondisiTidur;
    TextView temperature;
    TextView jaraknya;


    DatabaseReference led;
    DatabaseReference led2;
    DatabaseReference temp;
    DatabaseReference jarak;


    int notifID = 22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushNotifications.start(getApplicationContext(), "4913ecf3-6ce9-4edb-95a3-079d99eea633");
        PushNotifications.subscribe("hello");


        kamarOn = (Button) findViewById(R.id.btn_on);
        kamarOff = (Button) findViewById(R.id.btn_off);
        tidurOn = (Button) findViewById(R.id.btn2_on);
        tidurOff = (Button) findViewById(R.id.btn2_off);
        kondisiKamar = (TextView) findViewById(R.id.kondisi1);
        kondisiTidur = (TextView) findViewById(R.id.kondisi2);
        temperature = (TextView) findViewById(R.id.suhu);
        jaraknya = (TextView) findViewById(R.id.jarakBarang);


        led = FirebaseDatabase.getInstance().getReference().child("led");
        led2 = FirebaseDatabase.getInstance().getReference().child("led2");
        temp = FirebaseDatabase.getInstance().getReference().child("Temp");
        jarak = FirebaseDatabase.getInstance().getReference().child("Jarak");

    }


    public void writeDB() {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("led");

        myRef.setValue("1");
    }


    @Override
    protected void onStart() {
        super.onStart();

        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kondisi = dataSnapshot.getValue(String.class);
                    temperature.setText(String.valueOf(kondisi));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kondisi = dataSnapshot.getValue(String.class);
                if(kondisi=="1"){
                    kondisiKamar.setText(String.valueOf("Lampu Kamar HIDUP"));

                }else{
                    kondisiKamar.setText(String.valueOf("Lampu Kamar MATI"));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        led2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kondisi = dataSnapshot.getValue(String.class);
                if(kondisi=="1"){
                    kondisiTidur.setText(String.valueOf("Lampu Tidur HIDUP"));
                }else{
                    kondisiTidur.setText(String.valueOf("Lampu Tidur MATI"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jarak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kondisi = dataSnapshot.getValue(String.class);
                int jarak = Integer.parseInt(kondisi);
                if(jarak > 30){
                    jaraknya.setText(String.valueOf("ILANG!"));
                }else{
                    jaraknya.setText(String.valueOf("Masih Aman!"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        kamarOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led.setValue("1");
            }
        });

        kamarOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led.setValue("0");
            }
        });

        tidurOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led2.setValue("1");
            }
        });

        tidurOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led2.setValue("0");
            }
        });

    }

}
