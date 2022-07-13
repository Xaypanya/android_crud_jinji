package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Save_data extends AppCompatActivity {
    EditText txtname,txtsurname,txttel;
    TextView txtshowcon;
    Button btnok,btncancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);
        txtname = findViewById(R.id.txtname);
        txtsurname = findViewById(R.id.txtsurname);
        txttel = findViewById(R.id.txttel);
        txtshowcon = findViewById(R.id.txtcon);
        btnok = findViewById(R.id.ok);
        btncancle = findViewById(R.id.cancle);
        getSupportActionBar().setTitle("SAVE");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(txtname.getText().toString()) && TextUtils.isEmpty(txtsurname.getText().toString()) && TextUtils.isEmpty(txttel.getText().toString()) ){
                    Toast toast = Toast.makeText(getApplicationContext(), "ກະລຸນາໃສ່ຂໍ້ມູນ", Toast.LENGTH_LONG);
                    toast.show();

                }else {
                    new myData().execute("");
                }

            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtname.setText("");
                txtsurname.setText("");
                txttel.setText("");
                txtname.requestFocus();
            }
        });
    }
    private class myData extends AsyncTask<String,String,String>{
        String msg="";
        String name =txtname.getText().toString();
        String surname = txtsurname.getText().toString();
        String tel = txttel.getText().toString();


        @Override
        protected void onPreExecute() {

            //txtshowcon.setText("ກະລຸນາລໍຖ້າການເຊື່ອຕໍ່.....");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {

                Connection c = DBConnect.getConnect();
                ModelCW f = new ModelCW(c);
                f.setName(name);
                f.setSurname(surname);
                f.setTel(tel);
                int r = f.InsertData();
                if(r>0){
                    msg="ບັນທຶກສຳເລັດແລ້ວ!";
                } else {
                    msg = "ບໍ່ສາມາດບັນທຶກໄດ້";

                }


            }catch (Exception ex){
                msg = "ບໍ່ສາມາດເຊື່ອຕໍ່ໄດ້";

            }
            return msg;

        }

        @Override
        protected void onPostExecute(String s) {

//            txtshowcon.setText(s);
            txtname.setText("");
            txtsurname.setText("");
            txttel.setText("");
            txtname.requestFocus();
            new SweetAlertDialog(Save_data.this)
                    .setTitleText(s)
                    .show();




        }
    }

}