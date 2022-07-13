package com.example.myapp;
import java.sql.*;
public class ModelCW implements ActionDB {
    private String id;
    private String name;
    private String surname;
    private String tel;
    Connection c;

    public ModelCW(Connection c) {
        this.c = c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public ResultSet SelectData() throws Exception {
        String sql="SELECT * FROM users";
        ResultSet rs = c.createStatement().executeQuery(sql);
        return rs;
    }


    public ResultSet SelectDataById() throws Exception{
        String sql="SELECT * FROM users WHERE id=?";
        PreparedStatement stm = c.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs=stm.executeQuery();
        return rs;
    }

    @Override
    public int InsertData() throws Exception {
        String sql="Insert into users(name,surname,tel) values(?,?,?)";
        PreparedStatement stm = c.prepareStatement(sql);
        stm.setString(1,name);
        stm.setString(2,surname); //tua pien private thanrg therngg
        stm.setString(3,tel);
        int r = stm.executeUpdate();

        return r;
    }

    @Override
    public int UpdateData() throws Exception {
        String sql="Update users set name=?,surname=?,tel=? where id=?";
        PreparedStatement stm = c.prepareStatement(sql);
        stm.setString(1,name);
        stm.setString(2,surname);
        stm.setString(3,tel);
        stm.setString(4,id);
        int r = stm.executeUpdate();

        return r;
    }

    @Override
    public int DeleteData() throws Exception {

        String sql="Delete from users where id=?";
        PreparedStatement stm = c.prepareStatement(sql);
        stm.setString(1,id);
        int r = stm.executeUpdate();

        return r;
    }
}
