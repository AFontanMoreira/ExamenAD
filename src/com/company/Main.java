package com.company;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        String driver = "jdbc:postgresql:";
        String host = "//localhost:";
        String porto = "5432";
        String sid = "postgres";
        String usuario = "oracle";
        String password = "oracle";
        String url = driver + host+ porto + "/" + sid;
        Connection conn = DriverManager.getConnection(url,usuario,password);
        Metodos metodo = new Metodos(driver,host,porto,sid,usuario,password,url,conn);
        metodo.leer();
    }
}
