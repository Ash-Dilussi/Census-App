package com.example.mc2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextDirectionHeuristic;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mc2.databinding.ActivityMainBinding;

import yuku.ambilwarna.AmbilWarnaDialog;

public class HomeScreen extends AppCompatActivity {
    Button bgbutton,databutton;
    RelativeLayout relativeLayout;

    public int defaultcolor;


    ActivityMainBinding binding;
    TextView disnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        disnote.setText("  ");*/

        bgbutton = findViewById(R.id.btnpref);
        databutton = findViewById(R.id.btndata);


        relativeLayout= findViewById(R.id.homelayout);

        defaultcolor = ContextCompat.getColor(HomeScreen.this, com.google.android.material.R.color.design_default_color_primary);

        bgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });


        databutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeScreen.this, Add_Data.class);
                startActivity(i);
            }
        });

    }

    private void openColorPicker() {

        AmbilWarnaDialog ambilWarnaDialog= new AmbilWarnaDialog(this, defaultcolor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {


                defaultcolor =  color;
                relativeLayout.setBackgroundColor(defaultcolor);


            }
        });

        ambilWarnaDialog.show();
    }
}