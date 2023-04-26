package com.example.mc2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListData extends AppCompatActivity {

    DBfile DB;
    RecyclerView recyclerView;
    ArrayList<String> name,age,gender;
    Dialog mDialog;

    Button picview,btncloud;
    RAdapter radapter;

    byte[] imagebit;
    ImageView propic;
    StorageReference storageReference;

    private static final String KEY_NAME = "Student Name";
    private static final String KEY_AGE = "Age";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_PHOTO = "";

    private FirebaseFirestore fierstore= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);


        btncloud = findViewById(R.id.tofirestore);
        //propic  = findViewById(R.id.pic);

        DB = new DBfile(this);
        name = new ArrayList<>();
        age = new ArrayList<>();
        gender = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        radapter = new RAdapter(this, name,age, gender);
        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        displaydata();

        btncloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushtocloud();
            }
        });


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

    private void pushtocloud() {

        Cursor c = DB.getdata();
        if(c.getCount()==0){
            Toast.makeText(ListData.this,"No data to Push",Toast.LENGTH_LONG).show();
            return;
        }else{

            final ProgressDialog pd= new ProgressDialog(this);
            pd.setMessage("Uploading");
            pd.show();


            Map<String,Object> listing = new HashMap<>();
            while(c.moveToNext()){
                String studentname = c.getString(0);
                storageReference = FirebaseStorage.getInstance().getReference().child("Profile_pics").child("image -"+ studentname+".jpg");
                listing.put(KEY_NAME,c.getString(0)+"\n");
                listing.put(KEY_AGE,c.getString(1)+"\n");
                listing.put(KEY_GENDER,c.getString(2)+"\n");

                imagebit = c.getBlob(3);

                storageReference.putBytes(imagebit);




                fierstore.collection("Census_App").document(studentname).set(listing)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                pd.dismiss();
                                Toast.makeText(ListData.this,"Save to Cloud",Toast.LENGTH_LONG).show();

                                DB.cleardata();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(ListData.this,"Error !! Not Saved to Cloud",Toast.LENGTH_LONG).show();

                                String Tag= "ListData";
                                Log.d( Tag, e.toString());

                            }
                        });

            }
        }


    }

/*
    private void uploadimage(){

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile_pics");

        Cursor c = DB.getdata();
        if(c.getCount()==0){
            Toast.makeText(ListData.this,"No data to Push",Toast.LENGTH_LONG).show();
            return;
        }else{
            while(c.moveToNext()){


            }


               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ListData.this, "Pic Sent", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ListData.this,"Success Upload",Toast.LENGTH_LONG).show();
                        Log.d("From Upload", e.toString());
                    }
                });
        }
    }*/


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

             /*   Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, 5);

                propic.setImageBitmap(bitmap);*/
            }
        }
    }
}