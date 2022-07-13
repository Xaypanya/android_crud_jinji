package com.example.myapp;
import java.sql.*;

public class DBConnect {
    public static Connection getConnect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String user ="ohmyjinji";
            String pass="qwerty1122";
            String url="jdbc:mysql://192.168.100.9/ohmydatabase?characterEncoding=utf8";
            Connection c = DriverManager.getConnection(url,user,pass);
            return c;

        }catch (Exception ex){
            return null;
        }
    }


}
