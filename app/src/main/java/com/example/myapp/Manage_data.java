package com.example.myapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class Manage_data extends AppCompatActivity {
EditText txtid,txtname,txtsurname,txttel;
TextView showmsg;
Button btnsave,btndelete;
String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);
        txtid = findViewById(R.id.mid);
        txtname = findViewById(R.id.mname);
        txtsurname = findViewById(R.id.msurname);
        txttel = findViewById(R.id.mtel);
        getSupportActionBar().setTitle("Update Data");
        showmsg = findViewById(R.id.tmshow);
        btnsave = findViewById(R.id.msave);
        btndelete  = findViewById(R.id.mdelete);

        new showmanage().execute("");
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new updateData().execute("");
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete our course.
                AlertDialog.Builder builder = new AlertDialog.Builder(Manage_data.this);
                builder.setMessage("ຕ້ອງການລົບລາຍການນີ້ແທ້ບໍ່?");
                builder.setTitle("ລົບລາຍການ !");
                builder.setCancelable(false);
                builder.setPositiveButton("ຕົກລົງ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                        new deleteData().execute("");

                    }
                    });

                builder.setNegativeButton("ຍົກເລີກ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                    });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


//        btndelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new deleteData().execute("");
//            }
//        });



    }
    private class showmanage extends AsyncTask<String,String,String>{
        Intent b = getIntent();
        String id = b.getStringExtra("id");
        String msg="";
        @Override
        protected void onPreExecute() {

//            showmsg.setText("Pleas wait to connect");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Connection c = DBConnect.getConnect();
                if(c!=null){
                    msg = "ແກ້ໄຂຂໍ້ມູນ";
                    ModelCW cw = new ModelCW(c);
                    cw.setId(id);
                    ResultSet rs = cw.SelectDataById();
                    while (rs.next()){
                        txtid.setText(rs.getString("id"));
                        txtname.setText(rs.getString("name"));
                        txtsurname.setText(rs.getString("surname"));
                        txttel.setText(rs.getString("tel"));
                    }
                    rs.close();

                }else if(c==null){
                    msg="ມີຂໍ້ຜິດພາດ";

                }

            }catch(Exception ex){
                msg="ບໍ່ສາມາດເຊື່ອຕໍ່ໄດ້";
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            showmsg.setText(s);
        }
    }

    private class updateData extends AsyncTask<String,String,String>{
//        Intent b = getIntent();
//        String id = b.getStringExtra("id");
        String m ="";
        String id = txtid.getText().toString();
        String name = txtname.getText().toString();
        String surname = txtsurname.getText().toString();
        String tel = txttel.getText().toString();
        @Override
        protected void onPreExecute() {

            //showmsg.setText("Pleas wait to connect");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnect();
                ModelCW cw = new ModelCW(c);
                cw.setName(name);
                cw.setSurname(surname);
                cw.setTel(tel);
                cw.setId(id);
                int r = cw.UpdateData();
                if(r>0){
                    m="ອັບເດດຂໍ້ມູນສຳເລັດ";
                }else {
                    m="ບໍ່ສາມາດອັບເດດ";
                }


            }catch(Exception ex){
                    m="ບໍ່ສາມາດເຊື່ອຕໍ່ໄດ້";
            }
            return m;
        }

        @Override
        protected void onPostExecute(String s) {
//            showmsg.setText(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            Intent intent =new Intent(Manage_data.this,Show_data.class);
            Manage_data.this.finish();
            startActivity(intent);


        }
    }

    private  class deleteData extends  AsyncTask<String,String ,String>{
        String id = txtid.getText().toString();
        String msg="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnect();
                ModelCW cw = new ModelCW(c);
                cw.setId(id);
                int row = cw.DeleteData();
                if(row!=0){
                    msg="ລົບຂໍ້ມູນແລ້ວ";
                }else {
                    msg="ບໍ່າມາດລົບໄດ້";
                }

            }catch (Exception ex){
                     msg="ບໍ່ສາມາດເຊື່ອຕໍ່ໄດ້";
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
             Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
             Intent intent =new Intent(Manage_data.this,Show_data.class);
             Manage_data.this.finish();
             startActivity(intent);

        }
    }
}