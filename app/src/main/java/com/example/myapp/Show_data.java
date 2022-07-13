package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.sql.*;

public class Show_data extends AppCompatActivity {
    ProgressDialog p;
Button btnshow;
ListView listv;
TextView tvinfo;
    ArrayList<String> arrname=new ArrayList<String>();
ArrayList<String> arrid=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        btnshow = findViewById(R.id.btnshow);
        listv = findViewById(R.id.lv);
        getSupportActionBar().setTitle("Database Dashboard");
        tvinfo = findViewById(R.id.txtview);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getData().execute("");
            }
        });
    }
    private class getData extends AsyncTask<String,String,String>{
        ArrayAdapter<String> ad;
        String msg = "";
        @Override
        protected void onPreExecute() {

            //tvinfo.setText("ກຳລັງເຊື່ອຕໍ່....");
            super.onPreExecute();
            p = new ProgressDialog(Show_data.this);
            p.setMessage("ກະລຸນາລໍຖ້າ...ມັນກຳລັງດາວໂຫຼດ");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnect();
                if(c!=null){
                    msg ="ເຊື່ອມຕໍ່ຖ້າຂໍ້ມູນແລ້ວ";
                    ModelCW cw = new ModelCW(c);
                    ResultSet rs = cw.SelectData();
                    arrid.clear();
                    arrname.clear();
                    while (rs.next()){
                        arrid.add(rs.getString("id"));
                        arrname.add(rs.getString("name"));
                    }
                    rs.close();

                }else if(c==null){
                    msg="ບໍ່ສາມມາດເຊື່ອມຖານຂໍ້ມູນໄດ້";

                }

            }catch (Exception ex){
                msg ="ບໍ່ສາມາດເຊື່ອຕໍ່ໄດ້";

            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,arrname);
            if(listv!=null){
                p.hide();
            }else {

                p.show();
            }
            listv.setAdapter(ad);
            listv.setEmptyView(findViewById(R.id.emptyView));
            tvinfo.setText("ຂໍ້ມູນທັງໝົດ:");
            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    String str = arrid.get(i).toString();
                    Intent intent = new Intent(Show_data.this,Manage_data.class);
                    intent.putExtra("id",arrid.get(i));
                    Show_data.this.finish();
                    startActivity(intent);

                }
            });

        }
    }
}