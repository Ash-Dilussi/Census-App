package com.example.mc2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Add_Data extends AppCompatActivity {

    EditText namev, agev;
    Button submit, view,btnimg,btnlistv;
    RadioGroup Rgroup;
    RadioButton RB;
    ImageButton btnphoto;

    ImageView frame;
    byte[] imagebit;
    DBfile DB;
    int picback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);



        namev = findViewById(R.id.name);
        agev = findViewById(R.id.age);
        btnlistv = findViewById(R.id.listv);
        frame = findViewById(R.id.btnphotopic);
        btnphoto = findViewById(R.id.btnphotopic);


        frame.setImageResource(R.drawable.picback);
        frame.setTag(R.drawable.picback);
        picback=(int) frame.getTag();

        Rgroup = findViewById(R.id.radioBtnGroup);


       Rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               RB = findViewById(checkedId);

              // Toast.makeText(Add_Data.this, RB.getText().toString(), Toast.LENGTH_LONG).show();
           }
       });


        submit = findViewById(R.id.btnsubmit);
        view = findViewById(R.id.btnview);

        DB = new DBfile(this);

        if(ContextCompat.checkSelfPermission(Add_Data.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA
            },100);
        }


        btnphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,100);
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametxt = namev.getText().toString();
                String agetxt = agev.getText().toString();
                int ageint= Integer.parseInt(agetxt);
                String gendertxt= RB.getText().toString();
               /* String gendertxt1= "male";*/


                Boolean checkinsertdata = DB.insertuserdata(nametxt,ageint,gendertxt,imagebit);

                if(checkinsertdata == true){
                    Toast.makeText(Add_Data.this,"New Submission Inserted", Toast.LENGTH_LONG).show();

                }
                else  {
                    Toast.makeText(Add_Data.this,"New Submission NOT Inserted", Toast.LENGTH_LONG).show();

                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor checkgetdata = DB.getdata();
                if(checkgetdata.getCount() == 0){
                    Toast.makeText(Add_Data.this, "No Entry Exist", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Add_Data.this,Add_Data.class);
                    startActivity(i);
                }
                StringBuffer buffer = new StringBuffer();
                while(checkgetdata.moveToNext()){
                    buffer.append("\nName : "+ checkgetdata.getString(0)+"\n");
                    buffer.append("Age : "+ checkgetdata.getString(1)+"\n");
                    buffer.append("Gender : "+ checkgetdata.getString(2)+"\n\n\t-----------------------------\t\n");

                }

                AlertDialog.Builder builder= new AlertDialog.Builder(Add_Data.this);
                builder.setCancelable(true);
                builder.setTitle("Student LIST");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });


        btnlistv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Add_Data.this, ListData.class);
                startActivity(i);
            }
        });


    }

  /*public  void checkgenderButton(View v){
        int radioId= Rgroup.getCheckedRadioButtonId();

        Rbutton = findViewById(radioId);

        Toast.makeText(this,"selected"+ Rbutton.getText(), Toast.LENGTH_LONG).show();
  }*/

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            Bitmap bitmap= (Bitmap)  data.getExtras().get("data");
            frame.setImageBitmap(bitmap);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
            imagebit = byteArray.toByteArray();
        }
    }



}


