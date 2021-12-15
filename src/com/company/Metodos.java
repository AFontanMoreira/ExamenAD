package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Metodos {
    String driver;
    String host;
    String porto;
    String sid;
    String usuario;
    String password;
    String url;
    Connection conn;

    String codigoa;
    int acidez;
    String tipoDeUva;
    int cantidade;
    String dni;

    public Metodos(String driver, String host, String porto, String sid, String usuario, String password, String url, Connection conn) {
        this.driver = driver;
        this.host = host;
        this.porto = porto;
        this.sid = sid;
        this.usuario = usuario;
        this.password = password;
        this.url = url;
        this.conn = conn;
    }
    public void leer() throws IOException, SQLException {
        FileReader re = new FileReader("/home/oracle/IdeaProjects/ExamenAD/src/com/company/analisis.txt");
        BufferedReader br = new BufferedReader(re);
        String read = br.readLine();

        while (read != null){

            codigoa = read.split(",")[0];
            acidez = Integer.parseInt(read.split(",")[1]);
            tipoDeUva = read.split(",")[4];
            cantidade = Integer.parseInt(read.split(",")[5]);
            dni = read.split(",")[6];
            accesoBaseDatos();
            modificarClientes();
            read = br.readLine();
        }
        br.close();
    }
    public void accesoBaseDatos() throws SQLException {
        PreparedStatement pst = conn.prepareStatement("SELECT acidezmin, acidezmax, nomeu from uvas WHERE tipo= '" + tipoDeUva + "'");
        ResultSet rs = pst.executeQuery();

        String tratamientoAcidez;
        int acidezmax = 0;
        int acidezmin = 0;
        String nome = null;
        while (rs.next()){
            acidezmin = rs.getInt("acidezmin");
            acidezmax = rs.getInt("acidezmax");
            nome = rs.getString("nomeu");

        }
        if(acidez<acidezmin){
            tratamientoAcidez = "subir acidez";
            String query = "INSERT INTO xerado VALUES(?,?,?,?)";
            PreparedStatement pst1 = conn.prepareStatement(query);

            pst1.setString(1,codigoa);
            pst1.setString(2,nome);
            pst1.setString(3,tratamientoAcidez);
            pst1.setInt(4,cantidade*15);
            pst1.executeUpdate();
        }
        if(acidez>acidezmax){
            tratamientoAcidez = "bajar acidez";
            String query = "INSERT INTO xerado VALUES(?,?,?,?)";
            PreparedStatement pst1 = conn.prepareStatement(query);

            pst1.setString(1,codigoa);
            pst1.setString(2,nome);
            pst1.setString(3,tratamientoAcidez);
            pst1.setInt(4,cantidade*15);
            pst1.executeUpdate();
        }
        if(acidez>=acidezmin && acidez<=acidezmax){
            tratamientoAcidez = "equilibrado";
            String query = "INSERT INTO xerado VALUES(?,?,?,?)";
            PreparedStatement pst1 = conn.prepareStatement(query);

            pst1.setString(1,codigoa);
            pst1.setString(2,nome);
            pst1.setString(3,tratamientoAcidez);
            pst1.setInt(4,cantidade*15);
            pst1.executeUpdate();
        }

    }
    public void modificarClientes() throws SQLException {
        PreparedStatement pst3 = conn.prepareStatement("UPDATE clientes SET numerodeanalisis = numerodeanalisis + '" + 1 + "' WHERE dni ='" + dni + "'");
        pst3.executeUpdate();
    }
}
