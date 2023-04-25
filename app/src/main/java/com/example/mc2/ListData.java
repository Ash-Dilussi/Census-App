package com.example.mc2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    DBfile DB;
    RecyclerView recyclerView;
    ArrayList<String> name,age,gender;
    Dialog mDialog;

    Button picview;
    RAdapter radapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);



        DB = new DBfile(this);
        name = new ArrayList<>();
        age = new ArrayList<>();
        gender = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        radapter = new RAdapter(this, name,age, gender);
        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        displaydata();


     /*   mDialog = new Dialog(this);

        picview = radapter.btnpic;

        picview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setContentView(R.layout.popup);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });*/



       /* DB = new DBfile(this);
        ListView lvContact = findViewById(R.id.lvstudents);

        final SimpleCursorAdapter simpleCursorAdapter = DB.populateListViewFromDB();
        lvContact.setAdapter(simpleCursorAdapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String name = cursor.getString(0);
                Toast.makeText(ListData.this, name, Toast.LENGTH_LONG).show();
            }
        });*/



    }


    private void displaydata() {

        Cursor cursor = DB.getdata();
        if(cursor.getCount()==0)
        {
            Toast.makeText(ListData.this, "No Entry Exist",Toast.LENGTH_LONG).show();
        }

        else{
            while(cursor.moveToNext()){
                name.add(cursor.getString(0));
                age.add(cursor.getString(1));
                gender.add(cursor.getString(2));
            }
        }
    }
}